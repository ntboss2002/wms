package com.dx.wms.web.process.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dx.cmm.service.credit.CreditMatchResult;
import com.dx.cmm.service.credit.ICreditService;
import com.dx.cms.dto.Condition;
import com.dx.cms.service.IFileService;
import com.dx.cms.web.vo.ConditionsVo;
import com.dx.common.service.utils.Assert;
import com.dx.common.service.utils.DateUtils;
import com.dx.framework.dal.pagination.Pagination;
import com.dx.framework.dal.pagination.PaginationResult;
import com.dx.framework.exception.BaseException;
import com.dx.wms.constant.WMSConstants;
import com.dx.wms.dto.DealDetailDto;
import com.dx.wms.dto.LenderPushDataDto;
import com.dx.wms.enums.PayWay;
import com.dx.wms.service.ICommonService;
import com.dx.wms.service.IPushDataService;
import com.dx.wms.service.IWorkFlowService;
import com.dx.wms.service.account.dto.CustAccountApplyDto;
import com.dx.wms.service.apply.IApplyService;
import com.dx.wms.service.apply.ILenderApplySaveService;
import com.dx.wms.service.apply.entity.LenderApply;
import com.dx.wms.service.detail.DetailObserver;
import com.dx.wms.service.detail.DetailType;
import com.dx.wms.service.detail.ParamDetail;
import com.dx.wms.service.process.ParamProcess;
import com.dx.wms.service.push.LenderPushException;
import com.dx.wms.web.account.vo.CustAccountApplyVo;
import com.dx.wms.web.page.AjaxDataTableObj;
import com.dx.wms.web.page.DataTableObj;
import com.dx.wms.web.process.vo.ProcessParamVo;
import com.dx.wms.web.vo.LenderPushDataVo;
import com.dx.wms.web.vo.ReMatchVo;
import com.google.gson.Gson;

@RequestMapping("/operate")
@Controller
public class OperateController {

    /**
     * ????????????
     */
    private static final Logger LOG = LoggerFactory.getLogger(OperateController.class);

    /**
     * ????????????
     */
    @Autowired
    private ICommonService commonService;

    /**
     * ??????????????????
     */
    @Autowired
    private IApplyService lenderApplyService;

    /**
     * ??????????????????????????????
     */
    @Autowired
    private ILenderApplySaveService lenderApplySaveService;

    /**
     * ???????????????
     */
    @Autowired
    private IWorkFlowService workFlowService;

    @Autowired
    private DetailObserver detail;

    @Autowired
    private IPushDataService pushDataService;

    @Autowired
    private ICreditService creditService;

    @Autowired
    IFileService fileService;

    // ????????????
    @RequestMapping(value = "save.json", method = RequestMethod.POST)
    @ResponseBody
    public CustAccountApplyDto save(@RequestBody CustAccountApplyVo custAccountApplyVo, HttpServletRequest request) {
        LOG.info("***save(1) custAccountApplyVo={}***", new Gson().toJson(custAccountApplyVo));
        LOG.info("*** save()??????????????????***");
        Long userId = commonService.getUserId(request);
        CustAccountApplyDto custAccountApplyDto = new CustAccountApplyDto();
        BeanUtils.copyProperties(custAccountApplyVo, custAccountApplyDto);
        custAccountApplyDto.setActionUserId(userId);
        return getCustAccountApplyDto(custAccountApplyVo, custAccountApplyDto);
    }

    // ??????????????????
    @RequestMapping(value = "submitToSalesService.json")
    @ResponseBody
    public boolean submitToSalesService(@RequestBody ConditionsVo conditions, HttpServletRequest request) {
        LOG.info("**start**ContentsManageController**submitToSalesService()");
        if (null == conditions) {
            return false;
        }
        Condition conditionsDto = new Condition();
        BeanUtils.copyProperties(conditions, conditionsDto);
        Long userId = commonService.getUserId(request);
        Long lenderApplyId = conditionsDto.getLenderApplyId();
        LOG.info("**end**CustApplyController**submitToSalesService()**true");
        return workFlowService.submitToSalesService(lenderApplyId, userId, conditionsDto);
    }

    /**
     * 
     *
     * ????????????VO???DTO
     * 
     * @param custAccountApplyQueryDtos
     * @return
     */
    private CustAccountApplyDto getCustAccountApplyDto(CustAccountApplyVo custAccountApplyVo,
            CustAccountApplyDto custAccountApplyDto) {
        if (Assert.checkParam(custAccountApplyVo.getLenderApply().getParentId())) {
            // ???lenderApplyId??????????????? ??????parentId???
            List<LenderApply> lenderApplys = lenderApplyService.getLenderByIdAndParentId(
                    custAccountApplyVo.getLenderApply().getLenderApplyId(),
                    custAccountApplyVo.getLenderApply().getParentId());
            if (lenderApplys.size() > 0) {
                throw new BaseException("?????????????????????????????????");
            }
        }
        if (custAccountApplyVo.getCustAccount().getCustAccountId() != null) {
            custAccountApplyDto = lenderApplySaveService.saveLenderApply(custAccountApplyDto);
        }
        return custAccountApplyDto;
    }

    // ?????????????????????
    @RequestMapping(value = "/submit.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkLenderApply(@RequestBody ProcessParamVo submit, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Long userId = commonService.getUserId(request);
        ParamProcess process = new ParamProcess();
        BeanUtils.copyProperties(submit, process);
        result.put("result", workFlowService.flow(process, userId));
        return result;
    }

    // ?????????????????????????????????????????????
    @RequestMapping(value = "confirm_push.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> confirmPush(@RequestBody LenderPushDataVo lenderPushDataVo, HttpServletRequest request) {
        LOG.info("**confirmPush()**lenderPushDataVo={}**", new Gson().toJson(lenderPushDataVo));
        Map<String, Object> result = new HashMap<String, Object>();
        if (!Assert.checkParam(lenderPushDataVo)) {
            LOG.info("** end **confirmPush()**lenderPushDataVo is null**");
            result.put(WMSConstants.CODE, false);
            result.put(WMSConstants.MSG, "??????????????????");
            return result;
        }
        Long userId = commonService.getUserId(request);
        LenderPushDataDto lenderPushDataDto = new LenderPushDataDto();
        BeanUtils.copyProperties(lenderPushDataVo, lenderPushDataDto);
        if (Assert.checkParam(lenderPushDataVo.getTradeTime())) {
            lenderPushDataDto.setTradeTime(DateUtils.parseForDay(lenderPushDataVo.getTradeTime()));
        }
        try {
            workFlowService.confirmPush(lenderPushDataDto, userId);
        } catch (LenderPushException e) {
            result.put(WMSConstants.CODE, false);
            result.put(WMSConstants.MSG, e.getMessage());
        }
        result.put(WMSConstants.CODE, true);
        return result;
    }

    // ????????????
    @RequestMapping(value = "giveUp.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> giveUp(@RequestBody LenderPushDataVo lenderPushDataVo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        result.put(WMSConstants.CODE, true);
        try {
            Long userId = commonService.getUserId(request);
            workFlowService.giveUp(lenderPushDataVo.getLenderCode(), userId);
        } catch (BaseException e) {
            result.put(WMSConstants.CODE, false);
            result.put(WMSConstants.MSG, e.getMessage());
        }
        return result;
    }

    // ??????????????????????????????????????????????????????????????????????????????????????????
    @RequestMapping(value = "voucher.json")
    public String voucher(@ModelAttribute("lenderApplyId") Long lenderApplyId, String appCode, String resKey,
            String cmAction, ModelMap model, HttpServletRequest request) {
        if (!Assert.checkParam(lenderApplyId)) {
            LOG.error("**CustApplyController**voucher()**param**lenderApplyId is null");
            return "";
        }
        LOG.info("**CustApplyController**voucher()**lenderApplyId={}", lenderApplyId);
        LenderApply apply = detail.query(new ParamDetail(DetailType.APPLY, lenderApplyId)).getApply();
        Assert.notNull(" ***apply must not be null ***", apply);
        model.addAttribute("lenderApply", apply);
        if (Assert.checkParam(apply.getPayWayId())) {
            model.addAttribute("payWayName", PayWay.getInfo(apply.getPayWayId().intValue(), false));
        } else {
            model.addAttribute("payWayName", "");
        }
        return "custApply/voucher";
    }

    @RequestMapping(value = "reMatchView.json")
    public String returnMatch(@ModelAttribute("lenderApplyId") Long lenderApplyId, ModelMap model,
            @ModelAttribute("lenderCode") String lenderCode) {
        Assert.notNull(" lenderApplyId and lenderCode must be not null", lenderApplyId, lenderCode);
        LOG.info("**OperateController  returnMatch() lenderApplyId={},lenderCode={} **", lenderApplyId, lenderCode);
        LenderApply apply = lenderApplyService.queryByLenderCode(lenderCode);
        Assert.notNull(" ***apply must not be null ***", apply);
        model.addAttribute("lenderApply", apply);
        return "custApply/reMatchView";
    }

    // ????????????????????????
    /*
     * @RequestMapping(value="browser.json") public String browser(@ModelAttribute("lenderCode") String
     * lenderCode,ModelMap model){ model.addAttribute("lenderApply", new LenderApply()); return ""; }
     */

    /**
     * 
     * ????????????: ?????????????????? ????????????????????????
     *
     * @param lenderPushDataVo
     * @param request
     * @return
     * @see [?????????/??????](??????)
     * @since [??????/????????????](??????)
     */
    // list????????????
    @RequestMapping("creditor.json")
    @ResponseBody
    public AjaxDataTableObj<ReMatchVo> creditor(@ModelAttribute("lenderCode") String lenderCode,
            HttpServletRequest request, DataTableObj dTable) {
        Assert.notNull("**OperateController**creditor()**param**lenderCode is null", lenderCode);
        LOG.info("**lenderCode={}**", lenderCode);
        List<ReMatchVo> resultVos = new ArrayList<ReMatchVo>();
        List<CreditMatchResult> resultList = creditService.queryLenderFirst(lenderCode);
        for (CreditMatchResult credit : resultList) {
            resultVos.add(new ReMatchVo(credit));
        }
        Pagination page = new Pagination(resultList.size(), 1);
        page.setTotalRows(resultList.size());
        return new AjaxDataTableObj<ReMatchVo>(dTable, new PaginationResult<List<ReMatchVo>>(resultVos, page));
    }

    // ????????????
    @RequestMapping(value = "dealDetail.json", method = RequestMethod.POST)
    @ResponseBody
    public List<DealDetailDto> dealDetail(@RequestParam("lenderApplyId") Long lenderApplyId, ModelMap model,
            HttpServletRequest request) {
        LOG.info("**dealDetail()**lenderApplyId={}**", lenderApplyId);
        Long userId = commonService.getUserId(request);
        return pushDataService.getDealDetailDtoList(lenderApplyId, userId);
    }

    // ??????????????????
    @RequestMapping(value = "submitReMatch.json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitReMatch(@RequestBody LenderPushDataVo lenderVo, HttpServletRequest request) {
        LOG.info("**submitReMatch()**pushData={}**", lenderVo);
        LenderPushDataDto lenderDto = new LenderPushDataDto();
        BeanUtils.copyProperties(lenderVo, lenderDto);
        Long userId = commonService.getUserId(request);
        return workFlowService.reMatch(lenderDto, userId);
    }

}
