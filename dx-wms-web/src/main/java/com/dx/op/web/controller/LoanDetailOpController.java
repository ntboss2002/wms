//package com.dx.op.web.controller;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.InvocationTargetException;
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.beanutils.PropertyUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.dx.ccs.dto.operation.OperationQueryDto;
//import com.dx.ccs.dto.operation.OperationResultDto;
//import com.dx.ccs.enums.PeriodInfo;
//import com.dx.ccs.enums.Purpose;
//import com.dx.ccs.enums.WorkSituation;
//import com.dx.ccs.service.IProductTypeService;
//import com.dx.ccs.service.intf.ICommonService;
//import com.dx.ccs.service.operation.IOperationService;
//import com.dx.ccs.util.DateUtil;
//import com.dx.ccs.vo.OrgVo;
//import com.dx.ccs.vo.UserVo;
//import com.dx.ccs.web.page.AjaxDataTableObj;
//import com.dx.ccs.web.page.DataTableObj;
//import com.dx.ccs.web.util.ExportExcelUtil;
//import com.dx.common.dto.CalculatorDto;
//import com.dx.common.service.utils.CalculatorUtils;
//import com.dx.common.service.utils.DateUtils;
//import com.dx.framework.dal.pagination.Pagination;
//import com.dx.framework.dal.pagination.PaginationResult;
//import com.dx.op.web.vo.LoanDetailExcelVo;
//import com.dx.op.web.vo.LoanDetailQueryVo;
//import com.dx.op.web.vo.LoanDetailResultVo;
//import com.google.gson.Gson;
//
//@Controller
//@RequestMapping("/loanDetailOp")
//public class LoanDetailOpController {
//
//    /**
//     * ????????????
//     */
//    private static final Logger LOG = LoggerFactory.getLogger(LoanDetailOpController.class);
//
//    /**
//     * ??????excel??????
//     */
//    private final String[] EXCEL_HEAD = { "?????????", "??????", "?????????", "????????????", "????????????", "??????????????????", "??????????????????", "??????", "????????????", "????????????",
//            "????????????", "????????????", "?????????????????????", "??????????????????", "????????????", "???????????????", "?????????????????????", "??????????????????", "????????????", "???????????????", "?????????????????????",
//            "?????????????????????", "?????????", "????????????", "????????????", "????????????", "????????????", "??????????????????", "????????????????????????", "????????????????????????", "??????????????????", "?????????????????????",
//            "??????????????????", "??????" };
//
//    @Autowired
//    private IOperationService operationService;
//
//    @Autowired
//    private IProductTypeService productTypeService;
//
//    @Autowired
//    private ICommonService commonService;
//
//    @RequestMapping(value = "/list.htm")
//    public String initPage(ModelMap model, HttpServletRequest request) {
//        // ?????????
//        model.addAttribute("orgs", commonService.queryAllSubCompanyOrgs());
//        // ??????????????????
//        model.addAttribute("productType", productTypeService.getProductView()); // ??????????????????
//        return "/operation/loan_detail/list";
//    }
//
//    @RequestMapping("list_do.json")
//    @ResponseBody
//    public AjaxDataTableObj<LoanDetailResultVo> listByPage(
//            @ModelAttribute("loanDetailQueryVo") LoanDetailQueryVo loanDetailQueryVo, HttpServletRequest request,
//            DataTableObj dTable) {
//        LOG.info("????????????????????????...");
//        Pagination pagination = new Pagination();
//        if (dTable.getCurrentPage() > 0) {
//            pagination.setCurrentPage(dTable.getCurrentPage());
//        }
//        pagination.setPagesize(dTable.getiDisplayLength());
//        LOG.info("listByPage() pagination{}", new Gson().toJson(pagination));
//        LOG.info("listByPage() loanDetailQueryVo{}", new Gson().toJson(loanDetailQueryVo));
//        PaginationResult<List<OperationResultDto>> result = operationService.queryByPageForLoanDetail(
//                convertQuery(loanDetailQueryVo), pagination);
//        List<LoanDetailResultVo> vos = convertResults(result.getR());
//        return new AjaxDataTableObj<LoanDetailResultVo>(dTable.getsEcho(),
//                new PaginationResult<List<LoanDetailResultVo>>(vos, pagination));
//    }
//
//    private OperationQueryDto convertQuery(LoanDetailQueryVo vo) {
//        OperationQueryDto dto = new OperationQueryDto();
//        dto.setSalesDepartment(vo.getSalesDepartment());
//        dto.setCustomerManagerId(vo.getCustomerManagerId());
//        dto.setProdType(vo.getProdType());
//        dto.setRepaymentDay(vo.getRepaymentDay());
//        dto.setCreateTimeBegin(vo.getCreateTimeBegin());
//        dto.setCreateTimeEnd(vo.getCreateTimeEnd());
//        dto.setIdCard(vo.getIdCard());
//        dto.setLoanPayTimeBegin(vo.getLoanPayTimeBegin());
//        dto.setLoanPayTimeEnd(vo.getLoanPayTimeEnd());
//        dto.setSignTimeBegin(vo.getSignTimeBegin());
//        dto.setSignTimeEnd(vo.getSignTimeEnd());
//        if (vo.getLoanPayTimeBegin() == null && vo.getLoanPayTimeEnd() == null && vo.getProdType() == null
//                && vo.getSalesDepartment() == null && StringUtils.isBlank(vo.getCustomerManagerId())
//                && vo.getRepaymentDay() == null && StringUtils.isBlank(vo.getIdCard())
//                && vo.getCreateTimeBegin() == null && vo.getCreateTimeEnd() == null && vo.getSignTimeBegin() == null
//                && vo.getSignTimeEnd() == null) {
//            dto.setLoanPayTimeBegin(new Date());
//            dto.setLoanPayTimeEnd(new Date());
//        }
//        return dto;
//    }
//
//    private List<LoanDetailResultVo> convertResults(List<OperationResultDto> dtos) {
//        List<LoanDetailResultVo> results = new ArrayList<LoanDetailResultVo>();
//        if (dtos == null || dtos.size() == 0) {
//            return results;
//        }
//        try {
//            Map<String, UserVo> userMap = new HashMap<>();
//            Map<Long, OrgVo> orgMap = new HashMap<>();
//            Map<Long, UserVo> leadMap = new HashMap<>();
//            Map<String, String> products = productTypeService.getProductView(true);
//            Map<String, BigDecimal> rates = productTypeService.getProductRate();
//            for (OperationResultDto dto : dtos) {
//                LoanDetailResultVo vo = new LoanDetailResultVo();
//                PropertyUtils.copyProperties(vo, dto);
//                // ????????? && ?????????
//                Long salesDepartmentId = dto.getSalesDepartment();
//                vo.setLoanDepartment(salesDepartmentId);
//                vo.setSalesDepartmentInfo(commonService.queryOrgCache(salesDepartmentId, orgMap).getName());
//                vo.setLoanDepartmentInfo(commonService.queryOrgCache(salesDepartmentId, orgMap).getLoanName());
//                vo.setAreaInfo(commonService.queryOrgCache(salesDepartmentId, orgMap).getAreaName());
//                // ???????????? && ????????????
//                vo.setCustomerManagerInfo(commonService.queryUserCache(dto.getCustomerManagerId(), userMap).getName());
//                vo.setCustomerManagerWorkerNo(commonService.queryUserCache(dto.getCustomerManagerId(), userMap)
//                        .getEmployeeId());
//                vo.setTeam(dto.getTeamId());
//                vo.setTeamInfo(commonService.queryOrgCache(dto.getTeamId(), orgMap).getName());
//                vo.setTeamManagerWorkerNo(commonService.queryLeaderCache(dto.getTeamId(), leadMap).getEmployeeId());
//                // ??????????????????
//                vo.setProfessionStatusInfo(dto.getProfessionStatus() != null ? WorkSituation.getInfo(dto
//                        .getProfessionStatus()) : "-");
//                // ????????????
//                Integer loanType = dto.getLoanType();
//                if (loanType != null) {
//                    if (loanType.equals(8)) {
//                        vo.setLoanTypeInfo(Purpose.getInfo(loanType) + "(" + dto.getLoanTypeOther() + ")");
//                    } else {
//                        vo.setLoanTypeInfo(Purpose.getInfo(loanType));
//                    }
//                }
//                // ???????????????
//                vo.setRefundFirstTime(DateUtils.formatForDay(dto.getRefundFirstTime(), "-"));
//                // ?????????????????????
//                Integer approveDeadline = PeriodInfo.getInfo(dto.getApproveDeadline());
//                BigDecimal amount = dto.getApproveAmount();
//                BigDecimal rate = productTypeService.getRate(dto.getLoanApplyId(), rates, "L");
//                // ??????????????????
//                vo.setApproveProdTypeInfo(products.get(dto.getApproveProdType().toString()));
//                vo.setSynthesizeRatio(new BigDecimal("100").multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP)
//                        .toString()
//                        + "%");
//                CalculatorDto calculatorDto = CalculatorUtils.calculate(amount, approveDeadline, rate);
//                vo.setApproveDeadlineInfo(approveDeadline != null ? approveDeadline.toString() : "-");
//                // ???????????????
//                if (dto.getRefundFirstTime() != null && approveDeadline != null) {
//                    vo.setRefundLastTime(CalculatorUtils.getEndBackDay(
//                            DateUtils.formatForDay(dto.getRefundFirstTime(), ""), approveDeadline));
//                }
//                // ??????????????????
//                vo.setApplyTime(DateUtils.formatForFull(dto.getApplyTime(), "-"));
//                // ????????????
//                vo.setCreateTime(DateUtils.formatForFull(dto.getCreateTime(), "-"));
//                // ????????????
//                vo.setApproveTime(DateUtils.formatForFull(dto.getApproveTime(), "-"));
//                // ????????????
//                vo.setSignTime(DateUtils.formatForFull(dto.getSignTime(), "-"));
//                // ????????????
//                vo.setLoanPayTime(DateUtils.formatForFull(dto.getLoanPayTime(), "-"));
//                // ?????????????????????
//                vo.setTotalFee(BigDecimal.ZERO.add(dto.getConsultFee() == null ? BigDecimal.ZERO : dto.getConsultFee())
//                        .add(dto.getServiceFee() == null ? BigDecimal.ZERO : dto.getServiceFee())
//                        .add(dto.getServiceFeeOther() == null ? BigDecimal.ZERO : dto.getServiceFeeOther())
//                        .add(dto.getAssessmentFee() == null ? BigDecimal.ZERO : dto.getAssessmentFee()));
//                // ??????????????????
//                vo.setRiskFee(StringUtils.isBlank(calculatorDto.getRiskMoney()) ? BigDecimal.ZERO : new BigDecimal(
//                        calculatorDto.getRiskMoney()));
//                // ??????
//                vo.setCustomer(commonService.queryUserCache(dto.getCreater(), userMap).getName());
//                results.add(vo);
//            }
//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            LOG.error("error:{}", e.getMessage());
//        }
//        return results;
//    }
//
//    private List<LoanDetailExcelVo> convertExcel(List<OperationResultDto> dtos) {
//        List<LoanDetailExcelVo> results = new ArrayList<LoanDetailExcelVo>();
//        if (dtos == null || dtos.size() == 0) {
//            return results;
//        }
//        try {
//            Map<String, UserVo> userMap = new HashMap<>();
//            Map<Long, OrgVo> orgMap = new HashMap<>();
//            Map<Long, UserVo> leadMap = new HashMap<>();
//            Map<String, String> products = productTypeService.getProductView(true);
//            Map<String, BigDecimal> rates = productTypeService.getProductRate();
//            for (OperationResultDto dto : dtos) {
//                LoanDetailExcelVo vo = new LoanDetailExcelVo();
//                PropertyUtils.copyProperties(vo, dto);
//                // ????????? && ?????????
//                vo.setSalesDepartmentInfo(commonService.queryOrgCache(dto.getSalesDepartment(), orgMap).getName());
//                vo.setLoanDepartmentInfo(commonService.queryOrgCache(dto.getSalesDepartment(), orgMap).getLoanName());
//                vo.setAreaInfo(commonService.queryOrgCache(dto.getSalesDepartment(), orgMap).getAreaName());
//                // ???????????? && ????????????
//                vo.setCustomerManagerInfo(commonService.queryUserCache(dto.getCustomerManagerId(), userMap).getName());
//                vo.setCustomerManagerWorkerNo(commonService.queryUserCache(dto.getCustomerManagerId(), userMap)
//                        .getEmployeeId());
//                vo.setTeamInfo(commonService.queryOrgCache(dto.getTeamId(), orgMap).getName());
//                vo.setTeamManagerWorkerNo(commonService.queryLeaderCache(dto.getTeamId(), leadMap).getEmployeeId());
//                // ??????????????????
//                Integer professionStatus = dto.getProfessionStatus();
//                vo.setProfessionStatusInfo(professionStatus != null ? WorkSituation.getInfo(professionStatus) : "-");
//                // ????????????
//                Integer loanType = dto.getLoanType();
//                if (loanType != null) {
//                    if (loanType.equals(8)) {
//                        vo.setLoanTypeInfo(Purpose.getInfo(loanType) + "(" + dto.getLoanTypeOther() + ")");
//                    } else {
//                        vo.setLoanTypeInfo(Purpose.getInfo(loanType));
//                    }
//                }
//                BigDecimal amount = dto.getApproveAmount();
//                Integer approveDeadline = PeriodInfo.getInfo(dto.getApproveDeadline());
//                BigDecimal rate = productTypeService.getRate(dto.getLoanApplyId(), rates, "L");
//                CalculatorDto calculatorDto = CalculatorUtils.calculate(amount, approveDeadline, rate);
//                // ??????????????????
//                vo.setApproveProdTypeInfo(products.get(dto.getApproveProdType().toString()));
//                vo.setSynthesizeRatio(new BigDecimal("100").multiply(rate).setScale(2, BigDecimal.ROUND_HALF_UP)
//                        .toString()
//                        + "%");
//                // ????????????
//                vo.setApproveAmount(amount);
//                // ???????????????
//                vo.setRefundFirstTime(DateUtils.formatForDay(dto.getRefundFirstTime(), ""));
//                // ?????????????????????
//                vo.setApproveDeadlineInfo(approveDeadline != null ? PeriodInfo.getInfo(approveDeadline).toString() : "");
//                // ???????????????
//                vo.setRefundLastTime(dto.getRefundFirstTime() != null && approveDeadline != null ? CalculatorUtils
//                        .getEndBackDay(DateUtils.formatForDay(dto.getRefundFirstTime(), ""), approveDeadline) : "");
//                // ????????????
//                vo.setCreateTime(DateUtils.formatForFull(dto.getCreateTime(), ""));
//                // ????????????
//                vo.setApproveTime(DateUtils.formatForFull(dto.getApproveTime(), ""));
//                // ????????????
//                vo.setSignTime(DateUtils.formatForFull(dto.getSignTime(), ""));
//                // ????????????
//                vo.setLoanPayTime(DateUtils.formatForFull(dto.getLoanPayTime(), ""));
//                // ?????????????????????
//                vo.setTotalFee(BigDecimal.ZERO.add(dto.getConsultFee() == null ? BigDecimal.ZERO : dto.getConsultFee())
//                        .add(dto.getServiceFee() == null ? BigDecimal.ZERO : dto.getServiceFee())
//                        .add(dto.getServiceFeeOther() == null ? BigDecimal.ZERO : dto.getServiceFeeOther())
//                        .add(dto.getAssessmentFee() == null ? BigDecimal.ZERO : dto.getAssessmentFee()));
//                // ??????????????????
//                vo.setRiskFee(StringUtils.isBlank(calculatorDto.getRiskMoney()) ? BigDecimal.ZERO : new BigDecimal(
//                        calculatorDto.getRiskMoney()));
//                // ??????
//                vo.setCustomer(commonService.queryUserCache(dto.getCreater(), userMap).getName());
//                results.add(vo);
//            }
//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            LOG.error("error:{}", e.getMessage());
//        }
//        return results;
//    }
//
//    /**
//     * 
//     * Excel??????
//     *
//     * @param taskId
//     * @param request
//     * @return
//     * @see [?????????/??????](??????)
//     * @since [??????/????????????](??????)
//     */
//    @RequestMapping("excelExoprt.json")
//    public void excelExoprt(HttpServletRequest request, HttpServletResponse response) {
//        OperationQueryDto operationQueryDto = new OperationQueryDto();
//        String salesDepartment = request.getParameter("salesDepartment");
//        String customerManagerId = request.getParameter("customerManagerId");
//        String prodType = request.getParameter("prodType");
//        String repaymentDay = request.getParameter("repaymentDay");
//        String createTimeBegin = request.getParameter("createTimeBegin");
//        String createTimeEnd = request.getParameter("createTimeEnd");
//        String idCard = request.getParameter("idCard");
//        String loanPayTimeBegin = request.getParameter("loanPayTimeBegin");
//        String loanPayTimeEnd = request.getParameter("loanPayTimeEnd");
//        String signTimeBegin = request.getParameter("signTimeBegin");
//        String signTimeEnd = request.getParameter("signTimeEnd");
//        if (StringUtils.isNotBlank(salesDepartment)) {
//            operationQueryDto.setSalesDepartment(Long.valueOf(salesDepartment));
//        }
//        if (StringUtils.isNotBlank(customerManagerId)) {
//            operationQueryDto.setCustomerManagerId(customerManagerId);
//        }
//        if (StringUtils.isNotBlank(prodType)) {
//            operationQueryDto.setProdType(Integer.valueOf(prodType));
//        }
//        if (StringUtils.isNotBlank(repaymentDay)) {
//            operationQueryDto.setRepaymentDay(Integer.valueOf(repaymentDay));
//        }
//        if (StringUtils.isNotBlank(createTimeBegin)) {
//            try {
//                operationQueryDto
//                        .setCreateTimeBeginStr(DateUtil.splitDate(URLDecoder.decode(createTimeBegin, "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//
//        }
//        if (StringUtils.isNotBlank(createTimeEnd)) {
//            try {
//                operationQueryDto.setCreateTimeEndStr(DateUtil.splitDate(URLDecoder.decode(createTimeEnd, "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        if (StringUtils.isNotBlank(idCard)) {
//            operationQueryDto.setIdCard(idCard);
//        }
//        if (StringUtils.isNotBlank(loanPayTimeBegin)) {
//            try {
//                operationQueryDto.setLoanPayTimeBeginStr(DateUtil.splitDate(URLDecoder
//                        .decode(loanPayTimeBegin, "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        if (StringUtils.isNotBlank(loanPayTimeEnd)) {
//            try {
//                operationQueryDto.setLoanPayTimeEndStr(DateUtil.splitDate(URLDecoder.decode(loanPayTimeEnd, "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        if (StringUtils.isNotBlank(signTimeBegin)) {
//            try {
//                operationQueryDto.setSignTimeBeginStr(DateUtil.splitDate(URLDecoder.decode(signTimeBegin, "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        if (StringUtils.isNotBlank(signTimeEnd)) {
//            try {
//                operationQueryDto.setSignTimeEndStr(DateUtil.splitDate(URLDecoder.decode(signTimeEnd, "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (StringUtils.isBlank(salesDepartment) && StringUtils.isBlank(customerManagerId)
//                && StringUtils.isBlank(prodType) && StringUtils.isBlank(repaymentDay)
//                && StringUtils.isBlank(createTimeBegin) && StringUtils.isBlank(createTimeEnd)
//                && StringUtils.isBlank(idCard) && StringUtils.isBlank(loanPayTimeBegin)
//                && StringUtils.isBlank(loanPayTimeEnd) && StringUtils.isBlank(signTimeBegin)
//                && StringUtils.isBlank(signTimeEnd)) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String startTime = sdf.format(new Date());
//            operationQueryDto.setLoanPayTimeBeginStr(startTime);
//            operationQueryDto.setLoanPayTimeEndStr(startTime);
//        }
//
//        List<OperationResultDto> results = operationService.createReportForLoanDetail(operationQueryDto);
//        ExportExcelUtil.excelExoprt(EXCEL_HEAD, null, convertExcel(results), null, "?????????????????? ", response, "yyyy-mm-dd");
//
//    }
//}
