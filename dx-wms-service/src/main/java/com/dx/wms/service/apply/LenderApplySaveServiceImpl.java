package com.dx.wms.service.apply;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.ccs.vo.OrgVo;
import com.dx.ccs.vo.RoleVo;
import com.dx.cmm.dto.PushData;
import com.dx.cmm.enums.MatchPushCode;
import com.dx.cmm.exception.PushException;
import com.dx.cmm.service.IMatchPushService;
import com.dx.common.service.utils.Assert;
import com.dx.fms.service.api.IFinanceBankInfoService;
import com.dx.fms.service.dto.BankInfoDTO;
import com.dx.fms.service.dto.BankParamDTO;
import com.dx.fms.service.dto.ProcessResultDTO;
import com.dx.framework.constant.service.IRegionNewService;
import com.dx.framework.dal.transaction.annotation.Transactional;
import com.dx.framework.exception.BaseException;
import com.dx.rule.api.dto.RuleParamDto;
import com.dx.rule.service.IRuleTriggerEngineService;
import com.dx.rule.service.exception.TriggerException;
import com.dx.wms.constant.RoleConstant;
import com.dx.wms.constant.WMSConstants;
import com.dx.wms.enums.CurrentStep;
import com.dx.wms.service.ICommonService;
import com.dx.wms.service.account.dao.ICustAccountDao;
import com.dx.wms.service.account.dto.CustAccountApplyDto;
import com.dx.wms.service.account.entity.CustAccount;
import com.dx.wms.service.apply.dao.ICustFinanceDao;
import com.dx.wms.service.apply.dao.ILenderApplyDao;
import com.dx.wms.service.apply.dao.ILenderConditionDao;
import com.dx.wms.service.apply.entity.CustFinance;
import com.dx.wms.service.apply.entity.LenderApply;
import com.dx.wms.service.apply.entity.LenderCondition;
import com.dx.wms.service.base.ICustViewService;
import com.dx.wms.service.changer.ChangeLog;
import com.dx.wms.service.changer.IChangeInfoSaveService;
import com.dx.wms.service.exception.SaveException;
import com.dx.wms.service.log.ILenderApplyLogDao;
import com.dx.wms.service.log.LenderApplyLog;
import com.dx.wms.service.save.HandlerDto;
import com.dx.wms.service.saver.ResultSaver;
import com.dx.wms.service.validators.CustFinanceValidator;
import com.dx.wms.service.validators.LenderApplyValidator;
import com.dx.wms.service.validators.LenderConditionValidator;
import com.dx.wms.service.validators.ValidatorUtils;
import com.google.gson.Gson;

@Service
public class LenderApplySaveServiceImpl implements ILenderApplySaveService {

    /**
     * ????????????
     */
    private static final Logger LOG = LoggerFactory.getLogger(LenderApplySaveServiceImpl.class);

    /**
     * ????????????????????????
     */
    @Autowired
    private ILenderConditionDao lenderConditionDao;
    

    /**
     * ??????????????????
     */
    @Autowired
    private ICommonService commonService;

    @Autowired(required = false)
    private IRuleTriggerEngineService ruleTriggerEngineService;

    /**
     * ????????????
     */
    @Autowired
    private IRegionNewService regionService;

    /**
     * ??????????????????Dao???
     */
    @Autowired
    private ICustAccountDao custAccountDao;

    @Autowired
    private ILenderApplyDao lenderApplyDao;

    /**
     * ????????????
     */
    @Autowired
    private ILenderApplyLogDao lenderApplyLogDao;

    /**
     * ???????????????Dao???
     */
    @Autowired
    private ICustFinanceDao custFinanceDao;

    @Autowired
    private IChangeInfoSaveService changeInfoSaveService;

    @Autowired
    private ICustLenderApplyService custLenderApplyService;

    @Autowired
    private IFinanceBankInfoService financeBankInfoService;

    @Autowired
    private IMatchPushService matchPushService;

    @Override
    public CustAccountApplyDto saveLenderApply(CustAccountApplyDto custAccountApplyDto) {
        Assert.notNull("**save() custAccountApplyDto can not be null**", custAccountApplyDto);
        Assert.notNull("**save() ???????????????????????? **", custAccountApplyDto.getActionUserId());
        LOG.info("**save() custAccountApplyDto={}**", new Gson().toJson(custAccountApplyDto));
        Long userId = custAccountApplyDto.getActionUserId();
        Long custAccountId = custAccountApplyDto.getCustAccount().getCustAccountId();
        Assert.notNull("**save() ????????????Id??????   **", custAccountId);
        // ?????????????????????
        LenderApply lenderApply = save(custAccountApplyDto.getLenderApply(), userId, custAccountId);
        Assert.notNull("**save() ????????????????????????   **", lenderApply.getLenderApplyId());
        // ?????????????????? ??????????????????????????????????????????
        List<CustFinance> custFinances = custAccountApplyDto.getCustFinances();
        saveSubBank(custFinances);
        Long lenderApplyId = lenderApply.getLenderApplyId();
        custAccountApplyDto.setLenderApply(lenderApply);
        custAccountApplyDto
                .setCustFinances(save(custAccountApplyDto.getCustFinances(), userId, lenderApplyId, custAccountId));
        deleteLenderConditions(custAccountApplyDto.getLenderConditions(), lenderApplyId);
        custAccountApplyDto.setLenderConditions(save(custAccountApplyDto.getLenderConditions(), userId, lenderApplyId));
        // ??????????????????
        if (Assert.checkParam(custAccountApplyDto.getLogs())) {
            for (ChangeLog log : custAccountApplyDto.getLogs()) {
                changeInfoSaveService.save(log, userId);
            }
        }
        return custAccountApplyDto;
    }

    private void deleteLenderConditions(List<LenderCondition> lenderConditions, Long lenderApplyId) {
        LOG.info("***deleteLenderConditions(),lenderConditions={},lenderApplyId={}",
                new Gson().toJson(lenderConditions), lenderApplyId);
        if (!Assert.checkParam(lenderConditions)) {
            // ???????????? ?????????ID???????????????????????????
            lenderConditionDao.del(lenderApplyId);
            return;
        }
        if (lenderConditions.size() == 1) {
            if (lenderConditions.get(0).getLenderConditionCategory() == 1) {
                lenderConditionDao.del(lenderApplyId, 2);
            } else {
                lenderConditionDao.del(lenderApplyId, 1);
            }
        }
    }

    private LenderApply save(LenderApply entity, Long userId, Long custAccountId) {
        Assert.notNull("????????????????????????", entity);
        OrgVo org = commonService.getOrgInfo(userId);
        Assert.notNull("???????????????????????????", org);
        Long teamId = org.getOrgId();
        Assert.notNull("?????????????????????ID??????", teamId);
        CustAccount custAccount = custAccountDao.queryById(CustAccount.class, custAccountId);
        String lenderCustCode = custAccount.getLenderCustCode();
        Assert.notNull("????????????????????????", lenderCustCode);
        Long id = entity.getLenderApplyId();
        if (Assert.checkParam(id)) {
            updateApply(entity, userId, id, lenderCustCode);
        } else {
            createApply(entity, userId, custAccountId, teamId, lenderCustCode);
        }
        return entity;
    }

    private void createApply(LenderApply entity, Long userId, Long custAccountId, Long teamId, String lenderCustCode) {
        Long orgId = commonService.getOrgIdByUserId(userId);
        Assert.notNull("??????????????????????????????ID??????", teamId, orgId);
        // ????????????
        // 1???????????????
        Long parentId = entity.getParentId();
        if (Assert.checkParam(parentId)) {
            LenderApply apply = lenderApplyDao.queryByParent(parentId);
            if (Assert.checkParam(apply)) {
                throw new BaseException("???????????????id = " + parentId + " ????????????");
            }
        }

        entity.setCustAccountId(custAccountId);
        entity.setLenderCustCode(lenderCustCode);
        entity.setOrgId(orgId);
        entity.setTeamId(teamId);
        entity.setCreateUser(userId);
        entity.setUpdateUser(userId);
        entity.setDataStatus(WMSConstants.DATE_STATUS_A);
        ValidatorUtils.validate(new LenderApplyValidator(), entity);
        Long applyId = lenderApplyDao.insert(entity);
        entity.setLenderApplyId(applyId);
        Assert.notNull("??????????????????ID??????", entity.getLenderApplyId());
        // ????????????????????????????????? ??? lenderApplyLog ??????????????????????????????
        Assert.notNull(save(entity.getLenderApplyId(), userId).getLenderApplyLogId());
        if (Assert.checkParam(parentId)) {
            dueApply(parentId, entity.getLenderApplyId());
        }
    }

    private void dueApply(Long parentId, Long applyId) {
        // ????????????
        // 2 ????????????
        // ????????????????????????
    	LOG.info("*parentId[{}],applyId[{}]*"+parentId+applyId);
        RuleParamDto dto = new RuleParamDto();
        dto.setAppKey(WMSConstants.WMS);
        dto.setLinkKey(WMSConstants.DRAFT);
        dto.setActionKey("approve");
        dto.setBizId(applyId);
        dto.setCreateTime(new Date());
        LenderApply apply = lenderApplyDao.queryById(LenderApply.class, parentId);
        dto.setParam(apply.getDueDate());
        try {
            ruleTriggerEngineService.triggerRule(dto);
        } catch (TriggerException e) {
            throw new BaseException("?????????????????????????????????????????????????????????" + e.getMessage());
        }
        lenderApplyDao.updateDueApplyStatus(parentId, "1");
    }

    private void updateApply(LenderApply entity, Long userId, Long lenderApplyId, String lenderCustCode) {
        LenderApply lenderApply = lenderApplyDao.queryById(LenderApply.class, lenderApplyId);
        List<RoleVo> roleList = commonService.findRolesByUserId(userId);
        if (Assert.checkParam(lenderApply.getFormStatus())) {
            if (!Assert.checkParam(entity.getFormStatus())) {
                entity.setFormStatus(-1l);
            }
            if (!commonService.hasRoleExist(roleList, RoleConstant.ZWH)
                    && !Assert.equals(lenderApply.getFormStatus(), entity.getFormStatus())) {
                throw new SaveException("?????????????????????????????????????????????????????????????????????");
            }
        }
        String lenderCode = lenderApply.getLenderCode();
        if (Assert.checkParam(lenderCode)) {
            entity.setLenderCode(lenderCode);
        }
        entity.setLenderCustCode(lenderCustCode);
        entity.setCustAccountId(lenderApply.getCustAccountId());
        entity.setCreateUser(lenderApply.getCreateUser());
        entity.setCreateTime(lenderApply.getCreateTime());
        entity.setOrgId(lenderApply.getOrgId());
        Date settlementDate = lenderApply.getSettlementDate();
        Integer statementDate = lenderApply.getStatementDate();
        Date matchDate = lenderApply.getMatchDate();
        Long formStatus = lenderApply.getFormStatus();
        Long parentId = lenderApply.getParentId();
        Date dueDate = lenderApply.getDueDate();
        String procInsId = lenderApply.getProcInsId();
        entity.setSettlementDate(settlementDate);
        entity.setProcInsId(procInsId);
        entity.setDueDate(dueDate);
        entity.setParentId(parentId);
        entity.setFormStatus(formStatus);
        entity.setMatchDate(matchDate);
        entity.setStatementDate(statementDate);
        entity.setTeamId(lenderApply.getTeamId());
        entity.setUpdateUser(userId);
        if(Assert.checkParam(lenderApply.getDueStatus())){
            entity.setDueStatus(lenderApply.getDueStatus());
        }
        entity.setDataStatus(WMSConstants.DATE_STATUS_A);
        ValidatorUtils.validate(new LenderApplyValidator(), entity);
        LOG.info("***End save() else***");
        lenderApplyDao.update(entity);
    }
    
    // ????????????????????????
    private LenderApplyLog save(Long lenderApplyId, Long userId) {
        LenderApplyLog lenderApplyLog = new LenderApplyLog();
        lenderApplyLog.setLenderApplyId(lenderApplyId);
        lenderApplyLog.setFromUser(userId);// ????????????
        lenderApplyLog.setCurrentStep(CurrentStep.DRAFT.getInfo());
        lenderApplyLog.setCurrentStepKey(WMSConstants.DRAFT);
        lenderApplyLog.setIsCurrent(WMSConstants.IS_CURRENT_APPROVED);// ????????????
        lenderApplyLog.setCreateUser(userId);
        lenderApplyLog.setUpdateUser(userId);
        lenderApplyLog.setDataStatus(WMSConstants.DATE_STATUS_A);
        lenderApplyLog.setLenderApplyLogId(lenderApplyLogDao.insert(lenderApplyLog));
        return lenderApplyLog;
    }

    private List<CustFinance> save(List<CustFinance> custFinances, Long userId, Long lenderApplyId,
            Long custAccountId) {
        List<CustFinance> saveCustfFinances = new ArrayList<CustFinance>();
        if (Assert.checkParam(custFinances)) {
            for (CustFinance entity : custFinances) {
                Assert.notNull("????????????????????????????????????", entity);
                Long id = entity.getCustFinanceId();
                if (id == null) {
                    entity.setLenderApplyId(lenderApplyId);
                    entity.setCustAccountId(custAccountId);
                    entity.setCreateUser(userId);
                    entity.setUpdateUser(userId);
                    entity.setDataStatus(WMSConstants.DATE_STATUS_A);
                    ValidatorUtils.validate(new CustFinanceValidator(), entity);
                    entity.setCustFinanceId(custFinanceDao.insert(entity));
                } else {
                    CustFinance custFinance = custFinanceDao.queryById(CustFinance.class, id);
                    entity.setLenderApplyId(custFinance.getLenderApplyId());
                    entity.setCustAccountId(custFinance.getCustAccountId());
                    entity.setCreateUser(custFinance.getCreateUser());
                    entity.setCreateTime(custFinance.getCreateTime());
                    entity.setAccountCategory(custFinance.getAccountCategory());
                    entity.setDataStatus(custFinance.getDataStatus());
                    entity.setUpdateUser(userId);
                    ValidatorUtils.validate(new CustFinanceValidator(), entity);
                    custFinanceDao.update(entity);
                }
                saveCustfFinances.add(entity);
            }
        }
        return saveCustfFinances;
    }

    private List<LenderCondition> save(List<LenderCondition> lenderConditions, Long userId, Long lenderApplyId) {
        List<LenderCondition> saveLenderConditions = new ArrayList<LenderCondition>();
        if (Assert.checkParam(lenderConditions)) {
            for (LenderCondition entity : lenderConditions) {
                Assert.notNull("????????????????????????????????????", entity);
                Long id = entity.getLenderConditionId();
                if (id == null) {
                    entity.setLenderApplyId(lenderApplyId);
                    entity.setCreateUser(userId);
                    entity.setUpdateUser(userId);
                    entity.setDataStatus(WMSConstants.DATE_STATUS_A);
                    ValidatorUtils.validate(new LenderConditionValidator(), entity);
                    entity.setLenderConditionId(lenderConditionDao.insert(entity));
                } else {
                    LenderCondition lenderCondition = lenderConditionDao.queryById(LenderCondition.class, id);
                    entity.setLenderApplyId(lenderCondition.getLenderApplyId());
                    entity.setCreateTime(lenderCondition.getCreateTime());
                    entity.setCreateUser(lenderCondition.getCreateUser());
                    entity.setUpdateUser(userId);
                    entity.setDataStatus(WMSConstants.DATE_STATUS_A);
                    ValidatorUtils.validate(new LenderConditionValidator(), entity);
                    lenderConditionDao.update(entity);
                }
                saveLenderConditions.add(entity);
            }
        }
        return saveLenderConditions;
    }

    @Override
    @Transactional
    public Map<String, Object> save(LenderApply apply, CustAccountApplyDto dto, Long userId) {
        Map<String, Object> result = new HashMap<String, Object>() {
            /**
             */
            private static final long serialVersionUID = 5218886602845035177L;

            {
                put(WMSConstants.CODE, true);
                put(WMSConstants.MSG, "????????????");
            }
        };

        Assert.notNull("getLenderApply must be not null", dto.getLenderApply());

        // ???????????????????????????????????????????????????????????????
        if (!Assert.checkParam(dto) || !Assert.checkParam(dto.getLenderApply().getLenderApplyId())) {
            result.put(WMSConstants.CODE, false);
            result.put(WMSConstants.MSG, "??????????????????????????????");
            return result;
        }
        //???????????????????????????????????????????????????????????????????????????????????????????????????
        LenderApplyLog applyLog = lenderApplyLogDao.queryByParam(dto.getLenderApply().getLenderApplyId());
        if (!(Assert.equals(apply.getFormStatus(), 20L) && (Assert.equals(applyLog.getLastStepKey(), "resubmit")
                || Assert.equals(applyLog.getLastStepKey(), "investmentCheck")
                || Assert.equals(applyLog.getLastStepKey(), "qualityCheck")))) {
            try {
                PushData data = custLenderApplyService.getPushDataDto(apply, dto.getLenderApply().getContractCode());
                if (Assert.checkParam(data)) {
                    matchPushService.push(MatchPushCode.INVEST_APPLY_UPDATE, data);
                }
            } catch (PushException e) {
                LOG.error("Push match error[{}]", e.getMessage());
                result.put(WMSConstants.CODE, false);
                result.put(WMSConstants.MSG, "??????????????????,?????????" + e.getMessage());
                return result;

            }
        }

        if (Assert.checkParam(dto.getCustAccount().getCustAccountId())) {
            dto.setActionUserId(userId);
            dto = saveLenderApply(dto);
        }
        return result;
    }

    @Override
    public void saveAppy(LenderApply entity, HandlerDto dto, ResultSaver result) {
        LenderApply apply = save(entity, dto.getUserId(), entity.getCustAccountId());
        result.setApply(apply);
    }

    @Override
    public void saveFinances(List<CustFinance> entity, HandlerDto dto, ResultSaver result) {
        // ??????????????????
        saveSubBank(entity);
        List<CustFinance> finances = save(entity, dto.getUserId(), result.getApply().getLenderApplyId(),
                result.getApply().getCustAccountId());
        result.setFinances(finances);
    }

    @Override
    public void saveConditions(List<LenderCondition> entity, HandlerDto dto, ResultSaver result) {
        deleteLenderConditions(entity, result.getApply().getLenderApplyId());
        List<LenderCondition> conditions = save(entity, dto.getUserId(), result.getApply().getLenderApplyId());
        result.setConditions(conditions);
    }

    // ??????????????????
    @Override
    public void saveSubBank(List<CustFinance> list) {
        List<BankInfoDTO> bankInfoDTOList = new ArrayList<BankInfoDTO>();
        for (CustFinance custFinance : list) {
            if (null != custFinance.getBankCategory()) {
                BankParamDTO bankParamDTO = new BankParamDTO();
                bankParamDTO.setBankCode(commonService.getbankCode(Integer.parseInt(custFinance.getBankCategory())));
                bankParamDTO.setProvinceCode(custFinance.getProvinceRegionCode());
                bankParamDTO.setCityCode(custFinance.getCityRegionCode());
                // ????????????????????????
                List<BankInfoDTO> bankList = commonService.querySubBankNameByCode(bankParamDTO);
                boolean flag = true;
                for (BankInfoDTO bankInfoDTO : bankList) {
                    if (bankInfoDTO.getSubBankName().equals(custFinance.getSubBank())) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    BankInfoDTO bankInfoDTO = new BankInfoDTO();
                    String bankCode = commonService.getbankCode(Integer.parseInt(custFinance.getBankCategory()));
                    bankInfoDTO = financeBankInfoService.queryBankNameByCode(bankCode);
                    bankInfoDTO.setBankCode(bankCode);
                    bankInfoDTO.setBankName(bankInfoDTO.getBankName());
                    bankInfoDTO.setProvinceCode(custFinance.getProvinceRegionCode());
                    bankInfoDTO.setProvinceName(regionService.getRegionNameByCode(custFinance.getProvinceRegionCode()));
                    bankInfoDTO.setCityName(regionService.getRegionNameByCode(custFinance.getCityRegionCode()));
                    bankInfoDTO.setCityCode(custFinance.getCityRegionCode());
                    bankInfoDTO.setSubBankName(custFinance.getSubBank());
                    bankInfoDTOList.add(bankInfoDTO);
                    Map<String, ProcessResultDTO> map = commonService.saveBankInfo(bankInfoDTOList);
                    Assert.notNull("**save() ????????????????????????   **", map.get(custFinance.getSubBank()).getIsSuccess());
                    bankInfoDTOList.clear();
                }
            }

        }
    }
}
