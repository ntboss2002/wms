//package com.dx.op.web.controller;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.reflect.InvocationTargetException;
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
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
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.dx.ccs.bean.ProductType;
//import com.dx.ccs.dto.ProductTypeQueryDto;
//import com.dx.ccs.dto.operation.OperationByDayQueryDto;
//import com.dx.ccs.dto.operation.OperationByDayResultDto;
//import com.dx.ccs.enums.PeriodInfo;
//import com.dx.ccs.enums.ProjectPeriod;
//import com.dx.ccs.enums.Purpose;
//import com.dx.ccs.enums.WorkSituation;
//import com.dx.ccs.service.IProductTypeService;
//import com.dx.ccs.service.intf.ICommonService;
//import com.dx.ccs.service.operation.IOperationService;
//import com.dx.ccs.service.util.RepayDeadlineCount;
//import com.dx.ccs.util.CalculatorUtil;
//import com.dx.ccs.util.DateUtil;
//import com.dx.ccs.web.page.AjaxDataTableObj;
//import com.dx.ccs.web.page.DataTableObj;
//import com.dx.ccs.web.util.ExportExcelUtil;
//import com.dx.common.dto.CalculatorDto;
//import com.dx.common.service.utils.AmountUtils;
//import com.dx.common.service.utils.CalculatorUtils;
//import com.dx.common.service.utils.DateUtils;
//import com.dx.framework.dal.pagination.Pagination;
//import com.dx.framework.dal.pagination.PaginationResult;
//import com.dx.op.web.vo.LoanDayExcelVo;
//import com.dx.op.web.vo.LoanDayQueryVo;
//import com.dx.op.web.vo.LoanDayResultVo;
//import com.google.gson.Gson;
//
///**
// * 
// * ???????????????????????????<br>
// * ????????????????????????
// *
// * @author tonys
// * @see [?????????/??????]????????????
// * @since [??????/????????????] ????????????
// */
//@Controller
//@RequestMapping("/loanDayOp")
//public class LoanDayOpController {
//    /**
//     * ????????????
//     */
//    private static final Logger LOG = LoggerFactory.getLogger(LoanDetailOpController.class);
//
//    /**
//     * ??????excel??????
//     */
//    private final String[] EXCEL_HEAD = { "?????????", "????????????", "??????????????????", "????????????????????????", "???????????????", "??????????????????", "????????????", "???????????????",
//            "?????????????????????", "??????????????????", "??????????????????????????????", "??????????????????", "????????????", "???????????????", "????????????", "?????????", "?????????", "????????????", "????????????" };
//
//    @Autowired
//    private ICommonService commonService;
//
//    @Autowired
//    private IOperationService operationService;
//
//    @Autowired
//    private IProductTypeService productTypeService;
//
//    @RequestMapping(value = "/list.htm")
//    public String initPage(ModelMap model, HttpServletRequest request) {
//        // ?????????
//        model.addAttribute("orgs", commonService.queryAllSubCompanyOrgs());
//        // ??????????????????
//        model.addAttribute("productType", productTypeService.getProductView()); // ??????????????????
//        return "/operation/loan_day/list";
//    }
//
//    @RequestMapping("list_do.json")
//    @ResponseBody
//    public AjaxDataTableObj<LoanDayResultVo> listByPage(
//            @ModelAttribute("loanDayQueryVo") LoanDayQueryVo loanDayQueryVo, HttpServletRequest request,
//            DataTableObj dTable) {
//        Pagination pagination = new Pagination();
//        if (dTable.getCurrentPage() > 0) {
//            pagination.setCurrentPage(dTable.getCurrentPage());
//        }
//        pagination.setPagesize(dTable.getiDisplayLength());
//        LOG.info("pagination{}", new Gson().toJson(pagination));
//        PaginationResult<List<OperationByDayResultDto>> results = operationService.queryByPageForLoanDay(
//                convertQuery(loanDayQueryVo), pagination);
//        List<LoanDayResultVo> loanDayResultVo = convertResults(results.getR());
//
//        return new AjaxDataTableObj<LoanDayResultVo>(dTable.getsEcho(), new PaginationResult<List<LoanDayResultVo>>(
//                loanDayResultVo, pagination));
//    }
//
//    private OperationByDayQueryDto convertQuery(LoanDayQueryVo queryVo) {
//        OperationByDayQueryDto queryDto = new OperationByDayQueryDto();
//        queryDto.setSalesDepartment(queryVo.getSalesDepartment());
//        queryDto.setCustomerManagerId(queryVo.getCustomerManagerId());
//        queryDto.setProdType(queryVo.getProdType());
//        queryDto.setRepaymentDay(queryVo.getRepaymentDay());
//        queryDto.setIdCard(queryVo.getIdCard());
//        queryDto.setLoanPayTimeBegin(queryVo.getLoanPayTimeBegin());
//        queryDto.setLoanPayTimeEnd(queryVo.getLoanPayTimeEnd());
//        queryDto.setName(queryVo.getName());
//        if (queryVo.getLoanPayTimeBegin() == null && queryVo.getLoanPayTimeEnd() == null
//                && queryVo.getProdType() == null && queryVo.getSalesDepartment() == null
//                && StringUtils.isBlank(queryVo.getCustomerManagerId()) && queryVo.getRepaymentDay() == null
//                && StringUtils.isBlank(queryVo.getIdCard()) && StringUtils.isBlank(queryVo.getName())) {
//            queryDto.setLoanPayTimeBegin(new Date());
//            queryDto.setLoanPayTimeEnd(new Date());
//        }
//        return queryDto;
//    }
//
//    private CalculatorDto getCalculatorDto(OperationByDayResultDto dto, Map<String, BigDecimal> rates) {
//        CalculatorDto calculatorDto = new CalculatorDto();
//        if (dto.getApproveAmount() != null && dto.getApproveDeadline() != null) {
//            BigDecimal amount = dto.getApproveAmount();
//            Integer approveDeadline = PeriodInfo.getInfo(dto.getApproveDeadline());
//            BigDecimal rate = productTypeService.getRate(dto.getLoanApplyId(), rates, "L");
//            LOG.info("getCalculatorDto() loanApplyId:{} , amount:{} , approveDeadline:{} , rate:{}",
//                    dto.getLoanApplyId(), amount, approveDeadline, rate);
//            calculatorDto = CalculatorUtils.calculate(amount, approveDeadline, rate);
//        }
//        return calculatorDto;
//    }
//
//    private List<LoanDayResultVo> convertResults(List<OperationByDayResultDto> dtos) {
//        List<LoanDayResultVo> results = new ArrayList<LoanDayResultVo>();
//        if (dtos != null && dtos.size() > 0) {
//            Map<String, String> products = productTypeService.getProductView(true);
//            Map<String, BigDecimal> rates = productTypeService.getProductRate();
//            try {
//                for (OperationByDayResultDto dto : dtos) {
//                    LoanDayResultVo vo = new LoanDayResultVo();
//                    CalculatorDto calculatorDto = getCalculatorDto(dto, rates);
//                    PropertyUtils.copyProperties(vo, dto);
//                    vo.setProfessionStatusInfo(dto.getProfessionStatus() != null ? WorkSituation.getInfo(dto
//                            .getProfessionStatus()) : "-");
//                    // ????????????
//                    vo.setLoanTypeInfo(Purpose.getInfoAd(dto.getLoanType()));
//                    // ???????????????
//                    vo.setRefundFirstTimeInfo(DateUtils.formatForDay(dto.getRefundFirstTime(), "-"));
//                    // ?????????????????????
//                    vo.setApproveDeadlineInfo(dto.getApproveDeadline() != null
//                            && Integer.compare(dto.getApproveDeadline(), 0) >= 0 ? PeriodInfo.getInfo(
//                            dto.getApproveDeadline()).toString() : "-");
//                    Integer overplusDeadline = PeriodInfo.getInfo(dto.getApproveDeadline())
//                            - RepayDeadlineCount.getRepayDeadlineCount(DateUtils.formatForDay(dto.getRefundFirstTime(),
//                                    ""));
//                    vo.setOverplusDeadline(overplusDeadline.toString());
//                    vo.setContractAmountInfo(AmountUtils.format(dto.getContractAmount(), "-"));
//                    vo.setPayAmountInfo(AmountUtils.format(dto.getContractAmount(), "-"));
//                    vo.setCreditorValueInfo(AmountUtils.format(dto.getContractAmount(), "-"));
//                    // ????????????????????????
//                    double a1 = 1;
//                    double a2 = CalculatorUtil.add(a1, calculatorDto.getRealRate());
//                    double pow = CalculatorUtil.sub(CalculatorUtil.power(a2, 12), a1);
//                    vo.setPow(pow + "");
//                    // ?????????
//                    vo.setRealRate(calculatorDto.getRealRate() + "");
//                    Integer eliteItem = 0;
//                    ProductTypeQueryDto productTypeQueryDto = new ProductTypeQueryDto();
//                    productTypeQueryDto.setLoanApplyId(dto.getLoanApplyId());
//                    productTypeQueryDto.setOperateProcess("L");
//                    List<ProductType> productTypes = productTypeService.queryByParam(productTypeQueryDto);
//                    for (ProductType productTypeI : productTypes) {
//                        if (!productTypeI.getLastProductType().equals(Long.valueOf(0))) {
//                            eliteItem = Integer.valueOf(productTypeI.getProductType());
//                        }
//                    }
//
//                    // ??????????????????
//                    if (dto.getApproveProdType() != null) {
//                        if (dto.getApproveProdType().equals(1) && !eliteItem.equals(0)) {
//                            vo.setApproveProdTypeInfo(products.get(eliteItem.toString()));
//                        } else {
//                            vo.setApproveProdTypeInfo(products.get(dto.getApproveProdType().toString()));
//
//                        }
//                    }
//                    // ???????????????
//                    if (dto.getRefundFirstTime() != null && dto.getApproveDeadline() != null) {
//                        vo.setRefundLastTime(DateUtils.formatForDay(dto.getRefundFirstTime(), "")
//                                + "???"
//                                + CalculatorUtils.getEndBackDay(DateUtils.formatForDay(dto.getRefundFirstTime(), ""),
//                                        PeriodInfo.getInfo(dto.getApproveDeadline())));
//                    }
//                    // ????????????
//                    vo.setSignTimeInfo(DateUtils.formatForFull(dto.getSignTime(), "-"));
//                    // ?????????????????????
//                    vo.setBackMoneyPer(AmountUtils.format(new BigDecimal(calculatorDto.getBackMoneyPer()), "-"));
//                    vo.setLoanAmountInfo(AmountUtils.format(dto.getLoanAmount(), "-"));
//                    results.add(vo);
//                }
//            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                LOG.error("error:{}", e.getMessage());
//            }
//        }
//        return results;
//    }
//
//    /**
//     * 
//     * ??????????????????
//     *
//     * @param queryLoanApplyVo
//     * @param request
//     * @param dTable
//     * @return
//     * @see [?????????/??????](??????)
//     * @since [??????/????????????](??????)
//     */
//    @RequestMapping("total.json")
//    @ResponseBody
//    public Map<String, Object> total(@RequestBody OperationByDayQueryDto operatiionByDayQueryDto) {
//        return operationService.queryByPageForLoanDayTotal(operatiionByDayQueryDto);
//    }
//
//    private List<LoanDayExcelVo> convertExcel(List<OperationByDayResultDto> dtos) {
//        List<LoanDayExcelVo> results = new ArrayList<LoanDayExcelVo>();
//        if (dtos != null && dtos.size() > 0) {
//            Map<String, String> products = productTypeService.getProductView(true);
//            Map<String, BigDecimal> rates = productTypeService.getProductRate();
//            for (OperationByDayResultDto dto : dtos) {
//                CalculatorDto calculatorDto = getCalculatorDto(dto, rates);
//                LoanDayExcelVo vo = new LoanDayExcelVo();
//                // ??????
//                vo.setName(dto.getName());
//                // ????????????
//                vo.setIdCard(dto.getIdCard());
//                // ????????????????????????????????????
//                vo.setContractAmount(dto.getContractAmount());
//                // ????????????????????????
//                vo.setCreditorValue(dto.getContractAmount());
//                // ???????????????
//                vo.setPayAmount(dto.getContractAmount());
//                // ??????????????????
//                Integer professionStatus = dto.getProfessionStatus();
//                if (professionStatus != null) {
//                    vo.setProfessionStatusInfo(WorkSituation.getInfo(professionStatus));
//                }
//                // ????????????
//                Integer loanType = dto.getLoanType();
//                if (loanType != null) {
//                    if (loanType == 1) {
//                        vo.setLoanTypeInfo("??????");
//                    } else if (loanType == 2) {
//                        vo.setLoanTypeInfo("??????");
//                    } else if (loanType == 3) {
//                        vo.setLoanTypeInfo("??????");
//                    } else if (loanType == 4) {
//                        vo.setLoanTypeInfo("??????");
//                    } else if (loanType == 5) {
//                        vo.setLoanTypeInfo("??????");
//                    } else if (loanType == 6) {
//                        vo.setLoanTypeInfo("??????");
//                    } else if (loanType == 7) {
//                        vo.setLoanTypeInfo("??????");
//                    } else {
//                        vo.setLoanTypeInfo(Purpose.getInfo(loanType));
//                    }
//                }
//                // ???????????????
//                vo.setRefundFirstTime(DateUtils.formatForDay(dto.getRefundFirstTime(), ""));
//                // ?????????????????????
//                Integer approveDeadline = dto.getApproveDeadline();
//                if (approveDeadline != null) {
//                    vo.setApproveDeadlineInfo(ProjectPeriod.getInfo(approveDeadline).replace("???", ""));
//                }
//
//                int iApproveDeadline = 0;
//                if (vo.getApproveDeadlineInfo() != null) {
//                    iApproveDeadline = Integer.parseInt(vo.getApproveDeadlineInfo());
//                }
//                // ??????????????????
//                int repayDeadlineCount = 0;
//                repayDeadlineCount = RepayDeadlineCount.getRepayDeadlineCount(vo.getRefundFirstTime());
//                int overplusDeadline = iApproveDeadline - repayDeadlineCount;
//                vo.setOverplusDeadline(overplusDeadline + "");
//                // ????????????????????????
//                double a1 = 1;
//                double a2 = CalculatorUtil.add(a1, calculatorDto.getRealRate());
//                double pow = CalculatorUtil.sub(CalculatorUtil.power(a2, 12), a1);
//                vo.setPow(pow + "");
//                // ?????????
//                vo.setRealRate(calculatorDto.getRealRate() + "");
//                // ??????????????????
//                Integer eliteItem = 0;
//                ProductTypeQueryDto productTypeQueryDto = new ProductTypeQueryDto();
//                productTypeQueryDto.setLoanApplyId(dto.getLoanApplyId());
//                productTypeQueryDto.setOperateProcess("L");
//                List<ProductType> productTypes = productTypeService.queryByParam(productTypeQueryDto);
//                for (ProductType productTypeI : productTypes) {
//                    if (!productTypeI.getLastProductType().equals(Long.valueOf(0))) {
//                        eliteItem = Integer.valueOf(productTypeI.getProductType());
//                    }
//                }
//
//                // ??????????????????
//                if (dto.getApproveProdType() != null) {
//                    if (dto.getApproveProdType().equals(1) && !eliteItem.equals(0)) {
//                        vo.setApproveProdTypeInfo(products.get(eliteItem.toString()));
//                    } else {
//                        vo.setApproveProdTypeInfo(products.get(dto.getApproveProdType().toString()));
//                    }
//                }
//                // ???????????????
//                if (dto.getRefundFirstTime() != null && approveDeadline != null) {
//                    Integer months = PeriodInfo.getInfo(approveDeadline);
//                    vo.setRefundLastTime(DateUtils.formatForDay(dto.getRefundFirstTime(), "")
//                            + "???"
//                            + CalculatorUtils.getEndBackDay(DateUtils.formatForDay(dto.getRefundFirstTime(), ""),
//                                    months));
//                }
//                // ?????????????????????
//                vo.setLoanAmount(dto.getLoanAmount());
//                // ?????????
//                vo.setRepaymentDay(dto.getRepaymentDay());
//
//                // ????????????
//                vo.setSignTime(DateUtils.formatForFull(dto.getSignTime(), ""));
//                // ????????????
//                vo.setPosition(dto.getPosition());
//
//                // ?????????????????????
//                vo.setBackMoneyPer(calculatorDto.getBackMoneyPer());
//
//                results.add(vo);
//            }
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
//        OperationByDayQueryDto operationByDayQueryDto = new OperationByDayQueryDto();
//        String salesDepartment = request.getParameter("salesDepartment");
//        String customerManagerId = request.getParameter("customerManagerId");
//        String prodType = request.getParameter("prodType");
//        String repaymentDay = request.getParameter("repaymentDay");
//        String idCard = request.getParameter("idCard");
//        String name = request.getParameter("name");
//        String loanPayTimeBegin = request.getParameter("loanPayTimeBegin");
//        String loanPayTimeEnd = request.getParameter("loanPayTimeEnd");
//
//        if (StringUtils.isNotBlank(salesDepartment)) {
//            operationByDayQueryDto.setSalesDepartment(Long.valueOf(salesDepartment));
//        }
//        if (StringUtils.isNotBlank(customerManagerId)) {
//            operationByDayQueryDto.setCustomerManagerId(customerManagerId);
//        }
//        if (StringUtils.isNotBlank(prodType)) {
//            operationByDayQueryDto.setProdType(Integer.valueOf(prodType));
//        }
//        if (StringUtils.isNotBlank(repaymentDay)) {
//            operationByDayQueryDto.setRepaymentDay(Integer.valueOf(repaymentDay));
//        }
//
//        if (StringUtils.isNotBlank(idCard)) {
//            operationByDayQueryDto.setIdCard(idCard);
//        }
//        if (StringUtils.isNotBlank(loanPayTimeBegin)) {
//            try {
//                operationByDayQueryDto.setLoanPayTimeBeginStr(DateUtil.splitDate(URLDecoder.decode(loanPayTimeBegin,
//                        "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        if (StringUtils.isNotBlank(loanPayTimeEnd)) {
//            try {
//                operationByDayQueryDto.setLoanPayTimeEndStr(DateUtil.splitDate(URLDecoder.decode(loanPayTimeEnd,
//                        "UTF-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//        if (StringUtils.isNotBlank(name)) {
//            try {
//                operationByDayQueryDto.setName(URLDecoder.decode(name, "UTF-8"));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (StringUtils.isBlank(salesDepartment) && StringUtils.isBlank(customerManagerId)
//                && StringUtils.isBlank(prodType) && StringUtils.isBlank(repaymentDay) && StringUtils.isBlank(idCard)
//                && StringUtils.isBlank(loanPayTimeBegin) && StringUtils.isBlank(loanPayTimeEnd)
//                && StringUtils.isBlank(name)) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String startTime = sdf.format(new Date());
//            operationByDayQueryDto.setLoanPayTimeBeginStr(startTime);
//            operationByDayQueryDto.setLoanPayTimeEndStr(startTime);
//        }
//
//        List<OperationByDayResultDto> results = operationService.createReportForLoanDay(operationByDayQueryDto);
//        ExportExcelUtil.excelExoprt(EXCEL_HEAD, null, convertExcel(results), null, "????????????????????????", response, "yyyy-mm-dd");
//
//    }
//}
