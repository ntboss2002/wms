package com.dx.wms.service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.dx.ccs.constans.AMDubboConstant;
import com.dx.ccs.vo.RoleVo;
import com.dx.common.service.utils.Assert;
import com.dx.fms.service.dto.BankInfoDTO;
import com.dx.fms.service.dto.BankParamDTO;
import com.dx.framework.constant.service.IRegionNewService;
import com.dx.hr.enums.CensusType;
import com.dx.hr.enums.CertType;
import com.dx.hr.enums.CountryType;
import com.dx.hr.enums.EmployeeStatus;
import com.dx.hr.enums.EntrySourceType;
import com.dx.hr.enums.GraduateType;
import com.dx.hr.enums.InsuredCity;
import com.dx.hr.enums.JobCategory;
import com.dx.hr.enums.Marriage;
import com.dx.hr.enums.NationType;
import com.dx.hr.enums.PoliticalStatus;
import com.dx.hr.enums.RelationShip;
import com.dx.hr.enums.SexType;
import com.dx.hr.enums.WorkUnit;
import com.dx.hr.service.dto.EmployeeEntryDto;
import com.dx.wms.constant.RoleConstant;
import com.dx.wms.constant.WMSConstants;
import com.dx.wms.enums.AccountStatus;
import com.dx.wms.enums.BankCategery;
import com.dx.wms.enums.BizBillDay;
import com.dx.wms.enums.CustCategery;
import com.dx.wms.enums.CustSource;
import com.dx.wms.enums.Education;
import com.dx.wms.enums.IdType;
import com.dx.wms.enums.LenderAmountArea;
import com.dx.wms.enums.MaritalStatus;
import com.dx.wms.enums.MsgWay;
import com.dx.wms.enums.PayWay;
import com.dx.wms.enums.Profession;
import com.dx.wms.enums.RecoveryMode;
import com.dx.wms.enums.Sex;
import com.dx.wms.enums.StatusStep;
import com.dx.wms.service.ICommonService;
import com.dx.wms.service.account.dto.CustAccountApplyDto;
import com.dx.wms.service.apply.entity.CustFinance;
import com.dx.wms.service.apply.entity.LenderCondition;
import com.dx.wms.service.base.CustViewDto;
import com.dx.wms.service.base.ICustViewService;
import com.dx.wms.service.saver.ResultSaver;
import com.google.gson.Gson;

@Service
public class ModelGateway implements Model_ {

    /**
     * ????????????
     */
    private static final Logger LOG = LoggerFactory.getLogger(ModelGateway.class);

    /**
     * ????????????
     */
    @Autowired
    private IRegionNewService regionService;

    /**
     * ??????????????????
     */
    @Autowired
    private ICommonService commonService;

    /**
     * ??????????????????
     */
    @Autowired
    private ICustViewService custViewService;

    @Override
    public void put(ModelMap model, String... keys) {
        for (String key : keys) {
            if (Assert.equals(key, Model_.SEX)) {
                // ??????
                model.addAttribute(key, Sex.getMap());
            }
            if (Assert.equals(key, Model_.EDUCATION)) {
                // ??????
                model.addAttribute(key, Education.getMap());
            }
            if (Assert.equals(key, Model_.ID_TYPE)) {
                // ????????????
                model.addAttribute(key, IdType.getMap());
            }
            if (Assert.equals(key, Model_.MARITAL_STATUS)) {
                // ????????????
                model.addAttribute(key, MaritalStatus.getMap());
            }
            if (Assert.equals(key, Model_.PROFESSION)) {
                // ??????
                model.addAttribute(key, Profession.getMap());
            }
            if (Assert.equals(key, Model_.MSG_WAY)) {
                // ??????????????????
                model.addAttribute(key, MsgWay.getMap());
            }
            if (Assert.equals(key, Model_.CUST_SOURCE)) {
                // ????????????
                model.addAttribute(key, CustSource.getMap());
            }
            if (Assert.equals(key, Model_.CUST_CATEGORY)) {
                // ????????????
                model.addAttribute(key, CustCategery.getMap());
            }
            if (Assert.equals(key, Model_.AREAS)) {
                // ??????
                // ?????????Code
                model.addAttribute(key, regionService.getMapByParentCode(WMSConstants.ROOT));
            }
            if (Assert.equals(key, Model_.RECOVERY_MODE)) {
                // ????????????
                model.addAttribute(key, RecoveryMode.getMap());
            }
            if (Assert.equals(key, Model_.PAY_WAY)) {
                // ????????????
                model.addAttribute(key, PayWay.getMap());
            }
            if (Assert.equals(key, Model_.PRODUCT)) {
                // ????????????
                model.addAttribute(key, commonService.getProductByProductId());
            }
            if (Assert.equals(key, Model_.BANK_CATEGORY)) {
                // ??????
                model.addAttribute(key, BankCategery.getMap());
            }
            if (Assert.equals(key, Model_.AMOUNT_AREA)) {
                // ????????????
                model.addAttribute(key, LenderAmountArea.getMap());
            }
            if (Assert.equals(key, Model_.ORG)) {
                // ?????????
                model.addAttribute(key, commonService.getOrgVos(AMDubboConstant.ORG_TYPE_WMS));
            }
            if (Assert.equals(key, Model_.BILL_DAY)) {
                model.addAttribute(key, BizBillDay.getMap());
            }
            if (Assert.equals(key, Model_.CURRENT_STEP)) {
                model.addAttribute(key, commonService.queryStatus());
            }
            if (Assert.equals(key, Model_.CERT_TYPE)) {
                // ??????????????????????????????
                model.addAttribute(key, CertType.getMap());
            }
            if (Assert.equals(key, Model_.SEX_TYPE)) {
                // ????????????????????????
                model.addAttribute(key, SexType.getMap());
            }
            if (Assert.equals(key, Model_.EMPLOYEE_STATUS)) {
                // ????????????????????????
                model.addAttribute(key, EmployeeStatus.getMap());
            }
            if (Assert.equals(key, Model_.JOB_CATEGORY)) {
                // ??????????????????????????????
                model.addAttribute(key, JobCategory.getMap());
            }
            if (Assert.equals(key, Model_.MARRIAGE)) {
                // ????????????????????????
                model.addAttribute(key, Marriage.getMap());
            }
            if (Assert.equals(key, Model_.COUNTRY)) {
                // ????????????????????????
                model.addAttribute(key, CountryType.getMap());
            }
            if (Assert.equals(key, Model_.NATION)) {
                // ????????????????????????
                model.addAttribute(key, NationType.getMap());
            }
            if (Assert.equals(key, Model_.WORKUNIT)) {
                // ??????????????????????????????
                model.addAttribute(key, WorkUnit.getMap());
            }
            if (Assert.equals(key, Model_.POLITICAL_STATUS)) {
                // ??????????????????????????????
                model.addAttribute(key, PoliticalStatus.getMap());
            }
            if (Assert.equals(key, Model_.HR_EDUCTION)) {
                // ????????????????????????
                model.addAttribute(key, com.dx.hr.enums.Education.getMap());
            }
            if (Assert.equals(key, Model_.CENSUS)) {
                // ??????????????????????????????
                model.addAttribute(key, CensusType.getMap());
            }
            if (Assert.equals(key, Model_.ENTRY_SOURCE)) {
                // ??????????????????????????????
                model.addAttribute(key, EntrySourceType.getMap());
            }
            if (Assert.equals(key, Model_.RELATION_SHIP)) {
                // ????????????????????????
                model.addAttribute(key, RelationShip.getMap());
            }
            if (Assert.equals(key, Model_.GRADUATE_TYPE)) {
                // ??????????????????????????????
                model.addAttribute(key, GraduateType.getMap());
            }
            if (Assert.equals(key, Model_.INSURED_CITY)) {
                // ?????????????????????????????????
                model.addAttribute(key, InsuredCity.getMap());
            }
        }

    }

    @Override
    public void putCustAccount(CustAccountApplyDto apply, ModelMap model) {
        Assert.notNull("Apply must not be null", apply);
        LOG.info("Apply[{}] is[{}]]", apply.getClass(), new Gson().toJson(apply));
        put(model, Model_.SEX, Model_.EDUCATION, Model_.ID_TYPE, Model_.MARITAL_STATUS, Model_.PROFESSION,
                Model_.MSG_WAY, Model_.CUST_SOURCE, Model_.CUST_CATEGORY, Model_.AREAS, Model_.RECOVERY_MODE,
                Model_.PRODUCT);
        if (Assert.checkParam(apply.getCustComm().getProvinceRegionCode())) {
            model.put("cityRegionCode", regionService.getListByParentCode(apply.getCustComm().getProvinceRegionCode()));
        }
        if (Assert.checkParam(apply.getCustComm().getCityRegionCode())) {
            model.put("districtRegionCode", regionService.getListByParentCode(apply.getCustComm().getCityRegionCode()));
        }
        if (Assert.checkParam(apply.getCustAccount()) && Assert.checkParam(apply.getCustAccount().getCustCode())) {
            CustViewDto custViewDto = custViewService.queryByCustCode(apply.getCustAccount().getCustCode());
            model.addAttribute("custId", custViewDto.getCustBase().getCustId());
        }

    }

    @Override
    public void putCustAccountApply(CustAccountApplyDto apply, ModelMap model) {
        Assert.notNull("Apply must not be null", apply);
        LOG.info("Apply[{}] is[{}]]", apply.getClass(), new Gson().toJson(apply));
        put(model, Model_.PAY_WAY, Model_.PRODUCT, Model_.BANK_CATEGORY, Model_.RECOVERY_MODE, Model_.AREAS);
        // ????????????
        model.put("payCustFinance", new CustFinance());
        // ????????????
        model.put("refundCustFinance", new CustFinance());
        // ????????????
        model.put("specialLimitInfo", new LenderCondition());
        // ????????????s
        model.put("specialBenefitInfo", new LenderCondition());
        model.put("subbranchBank", new BankInfoDTO());
        if (Assert.checkParam(apply.getCustFinances())) {
            for (CustFinance finance : apply.getCustFinances()) {
                if (Assert.equals(finance.getAccountCategory(), 1)) {
                    model.put("payCustFinance", finance);
                    // ?????????Code
                    if (Assert.checkParam(finance.getProvinceRegionCode())) {
                        model.put("paycityRegionCode",
                                regionService.getListByParentCode(finance.getProvinceRegionCode()));
                        List<BankInfoDTO> list = commonService.subBank(finance.getBankCategory(),
                                finance.getCityRegionCode(), finance.getProvinceRegionCode());
                        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
                        if (list != null && list.size() > 0) {
                            for (BankInfoDTO bankInfoDTO : list) {
                                Map<String, String> result = new HashMap<String, String>();
                                result.put("area_code_name", bankInfoDTO.getSubBankName());
                                result.put("area_code_id", bankInfoDTO.getSubBankName());
                                results.add(result);
                            }
                        }
                        model.put("SubBankName", results);
                    }
                }
                if (Assert.equals(finance.getAccountCategory(), 2)) {
                    model.put("refundCustFinance", finance);
                    // ?????????Code
                    if (Assert.checkParam(finance.getProvinceRegionCode())) {
                        model.put("refundcityRegionCode",
                                regionService.getListByParentCode(finance.getProvinceRegionCode()));
                        List<BankInfoDTO> list = commonService.subBank(finance.getBankCategory(),
                                finance.getCityRegionCode(), finance.getProvinceRegionCode());
                        List<Map<String, String>> results = new ArrayList<Map<String, String>>();
                        if (list != null && list.size() > 0) {
                            for (BankInfoDTO bankInfoDTO : list) {
                                Map<String, String> result = new HashMap<String, String>();
                                result.put("area_code_name", bankInfoDTO.getSubBankName());
                                result.put("area_code_id", bankInfoDTO.getSubBankName());
                                results.add(result);
                            }
                        }
                        model.put("refundSubBankName", results);
                    }
                }
            }
        }

        if (Assert.checkParam(apply.getLenderConditions())) {
            for (LenderCondition condition : apply.getLenderConditions()) {
                // 1:"??????",2:"??????"
                if (Assert.equals(condition.getLenderConditionCategory(), 1)) {
                    model.put("specialBenefitInfo", condition);
                }
                if (Assert.equals(condition.getLenderConditionCategory(), 2)) {
                    model.put("specialLimitInfo", condition);
                }
            }
        }

    }

    @Override
    public void putChange(ModelMap model) {
        put(model, Model_.AMOUNT_AREA, Model_.PRODUCT, Model_.ORG);
        // ???????????????
        model.addAttribute(Model_.CURRENT_STEP, StatusStep.getMap(""));
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????3????????????????????????????????????
        model.addAttribute(Model_.ACCOUNT_STATUS, AccountStatus.getMap("3"));
    }

    @Override
    public void put(ModelMap model, Map<String, Object> result) {
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            model.addAttribute(entry.getKey(), entry.getValue());
        }

    }

    @Override
    public void putEmployee(ModelMap model, EmployeeEntryDto entryDto) {
        if (Assert.checkParam(entryDto.getEmployeeDetailInfo().getCensusProvinceCode())) {
            model.addAttribute("censusCityCode",
                    regionService.getListByParentCode(entryDto.getEmployeeDetailInfo().getCensusProvinceCode()));
        }
        if (Assert.checkParam(entryDto.getEmployeeDetailInfo().getCensusCityCode())) {
            model.addAttribute("censusAreaCode",
                    regionService.getListByParentCode(entryDto.getEmployeeDetailInfo().getCensusCityCode()));
        }
        if (Assert.checkParam(entryDto.getEmployeeDetailInfo().getHomeProvinceCode())) {
            model.addAttribute("homeCityCode",
                    regionService.getListByParentCode(entryDto.getEmployeeDetailInfo().getHomeProvinceCode()));
        }
        if (Assert.checkParam(entryDto.getEmployeeDetailInfo().getHomeCityCode())) {
            model.addAttribute("homeAreaCode",
                    regionService.getListByParentCode(entryDto.getEmployeeDetailInfo().getHomeCityCode()));
        }
        if (Assert.checkParam(entryDto.getEmployeeDetailInfo().getBankProvinceCode())) {
            model.addAttribute("bankCityCode",
                    regionService.getListByParentCode(entryDto.getEmployeeDetailInfo().getBankProvinceCode()));
        }
        if (Assert.checkParam(entryDto.getSocialDto().getInsuredProvinceCode())) {
            model.addAttribute("insuredCityCode",
                    regionService.getListByParentCode(entryDto.getSocialDto().getInsuredProvinceCode()));
        }
        if (Assert.checkParam(entryDto.getEmployeeDetailInfo().getBankProvinceCode())) {
            BankParamDTO bankDto = new BankParamDTO();
            bankDto.setBankCode("CMB");
            bankDto.setProvinceCode(entryDto.getEmployeeDetailInfo().getBankProvinceCode());
            bankDto.setCityCode(entryDto.getEmployeeDetailInfo().getBankCityCode());
            model.addAttribute("openAddress", commonService.querySubBankNameByCode(bankDto));
        }

    }

    // list????????????model
    @Override
    public void putListModel(String biz, Long userId, List<RoleVo> roleVos, ModelMap model) {
        model.put("biz", biz);
        // ?????????????????????????????????list????????????????????????????????????????????????
        model.addAttribute("isShow", commonService.hasRoleExist(roleVos, RoleConstant.YYBJLZL));
        // ?????????????????????????????????
        model.addAttribute("departmentView", commonService.queryOrgListByUserId(userId));
        put(model, Model_.EMPLOYEE_STATUS, Model_.JOB_CATEGORY);
    }

    @Override
    public void putSaveModel(ResultSaver dto, ModelMap model) {
        if (Assert.checkParam(dto.getComm()) && Assert.checkParam(dto.getComm().getProvinceRegionCode())) {
            model.put("cityRegionCode", regionService.getListByParentCode(dto.getComm().getProvinceRegionCode()));
        }
        if (Assert.checkParam(dto.getComm()) && Assert.checkParam(dto.getComm().getCityRegionCode())) {
            model.put("districtRegionCode", regionService.getListByParentCode(dto.getComm().getCityRegionCode()));
        }
        if (Assert.checkParam(dto.getFinances())) {
            for (CustFinance finance : dto.getFinances()) {
                switch (finance.getAccountCategory()) {
                    case 2:
                        if (Assert.checkParam(finance.getProvinceRegionCode())) {
                            model.put("refundCityRegionCode",
                                    regionService.getListByParentCode(finance.getProvinceRegionCode()));
                            List<BankInfoDTO> list = commonService.subBank(finance.getBankCategory(),
                                    finance.getCityRegionCode(), finance.getProvinceRegionCode());
                            List<Map<String, String>> results = new ArrayList<Map<String, String>>();
                            if (list != null && list.size() > 0) {
                                for (BankInfoDTO bankInfoDTO : list) {
                                    Map<String, String> result = new HashMap<String, String>();
                                    result.put("area_code_name", bankInfoDTO.getSubBankName());
                                    result.put("area_code_id", bankInfoDTO.getSubBankName());
                                    results.add(result);
                                }
                            }
                            model.put("refundSubBankName", results);
                        }
                        break;
                    case 1:
                        if (Assert.checkParam(finance.getProvinceRegionCode())) {
                            model.put("payCityRegionCode",
                                    regionService.getListByParentCode(finance.getProvinceRegionCode()));
                            List<BankInfoDTO> list = commonService.subBank(finance.getBankCategory(),
                                    finance.getCityRegionCode(), finance.getProvinceRegionCode());
                            List<Map<String, String>> results = new ArrayList<Map<String, String>>();
                            if (list != null && list.size() > 0) {
                                for (BankInfoDTO bankInfoDTO : list) {
                                    Map<String, String> result = new HashMap<String, String>();
                                    result.put("area_code_name", bankInfoDTO.getSubBankName());
                                    result.put("area_code_id", bankInfoDTO.getSubBankName());
                                    results.add(result);
                                }
                            }
                            model.put("SubBankName", results);
                        }
                        break;
                }
            }

        }
    }
}
