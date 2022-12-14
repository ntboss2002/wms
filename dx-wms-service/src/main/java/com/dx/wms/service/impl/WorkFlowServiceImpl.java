package com.dx.wms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dx.bpm.constants.ProcessConstans;
import com.dx.bpm.constants.ProcessWealthConstans;
import com.dx.bpm.service.IProcessService;
import com.dx.bpm.service.ITaskService;
import com.dx.bpm.vo.CompleteForm;
import com.dx.bpm.vo.LoanApplicationForm;
import com.dx.bpm.vo.ProcessInstanceVo;
import com.dx.bpm.vo.TaskVo;
import com.dx.ccs.vo.RoleVo;
import com.dx.cmm.dto.PushData;
import com.dx.cmm.enums.MatchPushCode;
import com.dx.cmm.service.IMatchPushService;
import com.dx.cms.dto.Condition;
import com.dx.cms.enums.ResKey;
import com.dx.cms.service.IFileService;
import com.dx.common.service.utils.Assert;
import com.dx.framework.dal.client.support.PaginationDalClient;
import com.dx.framework.dal.transaction.annotation.Transactional;
import com.dx.framework.dal.transaction.template.CallBackTemplate;
import com.dx.framework.exception.BaseException;
import com.dx.rule.api.dto.RuleParamDto;
import com.dx.rule.service.IRuleTriggerEngineService;
import com.dx.wms.constant.RoleConstant;
import com.dx.wms.constant.WMSConstants;
import com.dx.wms.dto.LenderPushDataDto;
import com.dx.wms.dto.PushDataDto;
import com.dx.wms.enums.CodeRuleType;
import com.dx.wms.enums.PushCode;
import com.dx.wms.service.ICommonService;
import com.dx.wms.service.ILenderLogService;
import com.dx.wms.service.IPushDataService;
import com.dx.wms.service.IWorkFlowService;
import com.dx.wms.service.apply.IApplyService;
import com.dx.wms.service.apply.ICustLenderApplyService;
import com.dx.wms.service.apply.dao.ILenderApplyDao;
import com.dx.wms.service.apply.entity.LenderApply;
import com.dx.wms.service.exception.LenderLogException;
import com.dx.wms.service.log.ILenderApplyLogService;
import com.dx.wms.service.process.ParamProcess;
import com.dx.wms.service.push.ILenderPushService;
import com.dx.wms.service.push.LenderPushException;
import com.google.gson.Gson;

@Service
public class WorkFlowServiceImpl implements IWorkFlowService {
	
    private static final String APPROVE = "approve";
	
    /**
     * ??????????????????
     */
    @Autowired
    private ICommonService commonService;

    /**
     * ??????????????????
     */
    @Autowired
    private IApplyService lenderApplyService;

    /**
     * ????????????
     */
    @Autowired
    private IFileService fileService;
    /**
     * ????????????????????????
     */
    @Autowired
    private ILenderApplyLogService lenderApplyLogService;

    /**
     * ????????????????????????
     */
    @Autowired
    private ICustLenderApplyService custLenderApplyService;

    /**
     * ????????????
     */
    @Autowired
    private IProcessService processService;

    /**
     * ????????????
     */
    @Autowired
    private ITaskService taskService;

    /**
     * ??????????????????
     */
    @Autowired
    private IPushDataService pushDataService;

    @Autowired
    IMatchPushService matchPushService;

    @Autowired
    private ILenderApplyDao applyDao;

    @Autowired
    private ILenderPushService lenderPushService;

    @Autowired
    private ILenderLogService lenderLogService;

    @Autowired
    private PaginationDalClient dalClient;
    
    @Autowired
    private IRuleTriggerEngineService ruleTriggerEngineService;
    
    /**
     * ????????????
     */
    private static final Logger LOG = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    @Override
    @Transactional
    public Boolean submitToSalesService(Long lenderApplyId, Long userId, Condition condition) {
        Assert.notNull("????????????ID????????????", lenderApplyId);
        Assert.notNull("?????????????????????", userId);
        Assert.notNull("conditionsDto????????????", condition);
        LOG.info("***submitToSalesService() lenderApplyId={},userId={},conditionsDto={}", lenderApplyId, userId,
                condition);
        String currentStepKey = setSalesService(condition, userId, lenderApplyId);
        LOG.info("***submitToSalesService() conditionsDt{}", new Gson().toJson(condition));
        if (!fileService.effectiveFiles(condition, userId)) {
            throw new BaseException("**end**createUserAccount()**false ?????????????????????");
        }
        salesServiceException(currentStepKey, lenderApplyId, userId);
        return true;
    }

    private void salesServiceException(String currentStepKey, Long lenderApplyId, Long userId) {
        LenderApply apply = applyDao.queryById(LenderApply.class, lenderApplyId);
        try {
            lenderLogService.add(currentStepKey, userId, lenderApplyId, "", 1);
        } catch (LenderLogException e) {
            throw new BaseException("??????????????????,??????:" + e.getMessage());
        }
        lenderApplyService.updateStatus(lenderApplyId, 10L);
        custLenderApplyService.update(apply);
        Long nextUserId = lenderApplyLogService.getToUserId(userId, RoleConstant.XSKF);
        start(userId, lenderApplyId, nextUserId);
    }

    private String setSalesService(Condition condition, Long userId, Long lenderApplyId) {
        LenderApply lenderApply = applyDao.queryById(LenderApply.class, lenderApplyId);
        condition.setRes(ResKey.WMS_APPLY);
        if (Assert.checkParam(lenderApply.getLenderCode())) {
            condition.setLenderCode(lenderApply.getLenderCode());
            return WMSConstants.RESUBMIT;
        }
        Long orgId = commonService.getOrgIdByUserId(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lenderCustCode", lenderApply.getLenderCustCode());
        String code = commonService.getCode(CodeRuleType.LENDER_CODE.getInfo(), orgId, map);
        lenderApplyService.updateForCode(lenderApplyId, code);
        condition.setLenderCode(code);
        return WMSConstants.DRAFT;
    }

    private void start(Long userId, Long lenderApplyId, Long nextUserId) {
        Assert.notNull("***startWorkFlow()***userId is null", userId);
        Assert.notNull("***startWorkFlow()***lenderApplyId is null", lenderApplyId);
        Assert.notNull("***startWorkFlow()***nextUserId is null", nextUserId);
        LOG.info("***startWorkFlow()***userId={},lenderApplyId={},nextUserId={}", userId, lenderApplyId, nextUserId);
        LenderApply lenderApply = applyDao.queryById(LenderApply.class, lenderApplyId);
        String procInsId = lenderApply.getProcInsId();
        if (Assert.checkParam(procInsId)) {
            if (Assert.checkParam(procInsId) && Assert.checkParam(nextUserId)) {
                if (!completeWorkFlow(lenderApplyId, userId, "", ProcessConstans.CHECK_APPROVE, procInsId,
                        nextUserId)) {
                    throw new BaseException("**????????? ??????");
                }
            }
        } else {
            LoanApplicationForm loanApplicationForm = new LoanApplicationForm();
            loanApplicationForm.setLoanApplyId(lenderApplyId);
            loanApplicationForm.setBusinessType(ProcessWealthConstans.PROCESS_DEFINITION_WEALTHAPPLICATION);
            loanApplicationForm.setUserId(userId.toString());
            loanApplicationForm.setNextUserId(nextUserId.toString());
            ProcessInstanceVo pi = processService.startLoanApplication(loanApplicationForm);
            LOG.info("***submitToSalesService() pi={}", pi);
            procInsId = pi.getProcessInstanceId();
            if (!Assert.checkParam(procInsId)) {
                throw new BaseException(
                        "**end**CustApplyController**submitToSalesService() pi.getProcessInstanceId() ??????");
            }
        }
        lenderApplyService.updateForProcess(lenderApplyId, procInsId);
    }

    private Boolean execute(ParamProcess submit, Long userId, LenderApply apply) {
        // ????????????????????????
        List<RoleVo> roles = commonService.findRolesByUserId(userId);
        if (!Assert.checkParam(roles)) {
            LOG.error("User[{}] has no roles", userId);
            return false;
        }
        if (!commonService.hasRoleExist(roles, RoleConstant.XSKF)
                && !commonService.hasRoleExist(roles, RoleConstant.TZSH)) {
            return true;
        }
        try {
            Long nextId = -1l;
            lenderLogService.add(
                    commonService.hasRoleExist(roles, RoleConstant.XSKF) ? WMSConstants.QUALITY_CHECK
                            : WMSConstants.INVESTMENT_CHECK,
                    userId, apply.getLenderApplyId(), submit.getApproveComment(), submit.getCurrentAction());

            // ??????????????????
            if (commonService.hasRoleExist(roles, RoleConstant.XSKF)) {
                operation(userId, apply, submit.getCurrentAction(), nextId);
            }
            // ???????????????
            if (commonService.hasRoleExist(roles, RoleConstant.TZSH)) {
                operation(userId, apply, submit.getCurrentAction());
            }
            // ?????????
            if (Assert.checkParam(apply.getProcInsId())) {
                return completeWorkFlow(apply.getLenderApplyId(), userId, submit.getApproveComment(),
                        submit.getCurrentAction(), apply.getProcInsId(), nextId);
            }
        } catch (BaseException e) {
            return false;
        }
        return true;

    }

    @Override
    @Transactional
    public Boolean flow(ParamProcess submit, Long userId) {
        if (!Assert.checkParam(submit) || !Assert.checkParam(userId)) {
            LOG.error("Submit or user id must not be null");
            return false;
        }
        Long applyId = submit.getLenderApplyId();
        if (!Assert.checkParam(applyId)) {
            LOG.error("Apply id must not be null");
            return false;
        }
        LOG.info("User[{}], apply id[{}]", userId, applyId);
        LenderApply apply = applyDao.queryById(LenderApply.class, applyId);
        if (!Assert.checkParam(apply)) {
            LOG.error("Apply[{}] not found", applyId);
            return false;
        }
        return execute(submit, userId, apply);
    }

    private void operation(Long userId, LenderApply apply, Integer getPass, Long nextId) {
        if (Assert.checkParam(getPass, ProcessConstans.CHECK_APPROVE)) {
            nextId = lenderApplyLogService.getToUserId(userId, RoleConstant.TZSH);
        }
        if (!Assert.checkParam(apply.getFormStatus()) || !Assert.equals(apply.getFormStatus(), 10L)) {
            throw new BaseException();
        }
        lenderApplyService.updateStatus(apply.getLenderApplyId(),
                Assert.equals(getPass, ProcessConstans.CHECK_APPROVE) ? 12L : 11L);
        custLenderApplyService.update(apply);
    }

    private void operation(Long userId, LenderApply apply, Integer getPass) {
        lenderApplyService.updateStatus(apply.getLenderApplyId(),
                Assert.equals(getPass, ProcessConstans.CHECK_APPROVE) ? 14L : 13L);
        custLenderApplyService.update(apply);
        if (Assert.equals(getPass, ProcessConstans.CHECK_APPROVE)) {
            // ????????????????????????????????????????????????????????????
            if (Assert.checkParam(apply.getPayWayId()) && Assert.equals(apply.getPayWayId(), 2L)) {
                if (!pushDataService.pushDealDataByLenderApplyId(apply.getLenderApplyId(), userId)) {
                    throw new BaseException();
                }
            }
         // ??????????????????????????????15?????????
            if (Assert.checkParam(apply.getParentId())) {
                trigger(apply, WMSConstants.INVESTMENT_CHECK, APPROVE);
            }
        }
    }
    
    	private void trigger(LenderApply apply, String link, String action) {
	        RuleParamDto param = new RuleParamDto();
	        param.setAppKey(WMSConstants.WMS);
	        param.setLinkKey(link);
	        param.setActionKey(action);
	        param.setBizId(apply.getLenderApplyId());
	        param.setParam(apply.getDueDate());
	        param.setCreateTime(new Date());
	        LOG.info("Execute rule[{}]", new Gson().toJson(param));
	        ruleTriggerEngineService.triggerRule(param);
	    }
    
    private Boolean completeWorkFlow(Long lenderApplyId, Long userId, String content, Integer getPass, String procInsId,
            Long nextUserId) {
        CompleteForm form = new CompleteForm();
        TaskVo task = taskService.queryTaskByProcessInstance(procInsId);
        if (!Assert.checkParam(task)) {
            return false;
        }
        form.setProInsId(procInsId);
        form.setTaskId(task.getId());
        form.setLoanApplyId(lenderApplyId);
        form.setUserId(userId.toString());
        if (Assert.checkParam(nextUserId)) {
            form.setNextUserId(nextUserId.toString());
        }
        form.setContent(content);
        form.setPass(getPass);
        form.setAppKey("wms");
        processService.complete(form);
        LOG.info("Flow form[{}]", new Gson().toJson(form));
        return true;
    }

    @Override
    public List<String> getProIns(Long userId, String taskKey) {
        Assert.notNull("**getProIns *** userId ????????????", userId);
        Assert.notNull("**getProIns *** taskKey ????????????", taskKey);
        LOG.info("**getProIns *** userId={}, taskKey={}", userId, taskKey);
        // ?????????????????????
        List<TaskVo> tasks = new ArrayList<TaskVo>();
        // ????????????/???????????? ?????????
        tasks = taskService.listProsByAssigneeTask(userId, taskKey,
                ProcessWealthConstans.PROCESS_DEFINITION_WEALTHAPPLICATION);
        Map<String, String> proTask = new HashMap<String, String>();
        // ?????????????????????ID?????????????????????
        List<String> proIns = queryProIns(tasks, proTask);
        LOG.info("??????ID??????[{}]", new Gson().toJson(proIns));
        return proIns;
    }

    private List<String> queryProIns(List<TaskVo> tasks, Map<String, String> proTask) {
        List<String> pros = new ArrayList<String>();
        for (TaskVo t : tasks) {
            pros.add(t.getProcessInstanceId());
            proTask.put(t.getProcessInstanceId(), t.getId());
        }
        return pros;
    }

    @Override
    public void confirmPush(final LenderPushDataDto push, final Long userId) throws LenderPushException {
        dalClient.getTransactionTemplate().execute(new CallBackTemplate<Boolean>() {
            @Override
            public Boolean invoke() {
                // ????????????
                lenderApplyService.updateStatus(push.getLenderApplyId(), 17L);
                if (!Assert.checkParam(push.getPayWay())) {
                    effectImage(push, userId, ResKey.WMS_VOUCHERS);
                }
                // ????????????
                pushDataService.pushFinance(userId, push);
                // ??????????????? ??? ????????????
                lenderLogService.add(WMSConstants.CREDITOR_CONFIRM, userId, push.getLenderApplyId(), "??????????????????",
                        WMSConstants.APPROVED);
                return true;
            }
        });

    }

    // ???????????????
    private void effectImage(LenderPushDataDto lenderPushDataDto, Long userId, ResKey key) {
        Condition condition = new Condition();
        BeanUtils.copyProperties(lenderPushDataDto, condition);
        condition.setRes(key);
        LOG.info(" *** effectImage() conditio{} ***", new Gson().toJson(condition));
        if (!fileService.effectiveFiles(condition, userId)) {
            throw new BaseException("**end**createUserAccount()**false ?????????????????????");
        }
    }

    @Override
    @Transactional
    public Map<String, Object> reMatch(LenderPushDataDto lenderDto, Long userId) {
        Assert.notNull("**reMatch()** lenderDto = {},userId = {} ***", lenderDto, userId);
        LOG.info("**reMatch()**lenderApplyId={},userId={}**", new Gson().toJson(lenderDto), userId);
        effectImage(lenderDto, userId, ResKey.WMS_REFUSE);
        Long lenderApplyId = lenderDto.getLenderApplyId();
        Map<String, Object> result = new HashMap<String, Object>();
        Assert.notNull(" poolIds must not be null", lenderDto.getPoolIds());
        matchPushService.push(MatchPushCode.MATCH_REPEAT,
                new PushData(lenderDto.getLenderCode(), lenderDto.getPoolIds()));
        lenderLogService.add("creditorConfirm", userId, lenderApplyId, "??????????????????", WMSConstants.REFUSE);
        // ????????????
        lenderApplyService.updateStatus(lenderApplyId, 14L);
        result.put(WMSConstants.CODE, true);
        return result;
    }

    @Override
    @Transactional
    public void giveUp(String lenderCode, Long userId) {
        Assert.notNull("Lender code or user id must not be null", lenderCode, userId);
        LOG.info("Lender code[{}], user id[{}]", lenderCode, userId);
        PushDataDto dto = new PushDataDto();
        dto.giveUp(userId, lenderCode);
        lenderPushService.push(PushCode.GIVE_UP, dto);
        LenderApply apply = lenderApplyService.queryByLenderCode(lenderCode);
        Assert.notNull("Apply must not be null", apply);
        String procInsId = apply.getProcInsId();
        if (Assert.checkParam(procInsId)) {
            if (!completeWorkFlow(apply.getLenderApplyId(), userId, "", ProcessConstans.CHECK_QUIT, procInsId, null)) {
                throw new BaseException("???????????????");
            }
        }
    }

}
