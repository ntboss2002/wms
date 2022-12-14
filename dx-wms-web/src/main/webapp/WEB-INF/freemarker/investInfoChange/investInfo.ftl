<style>
    .control-group{
        height: 40px;
    }
    .row-fluid{
        height: 60px;
    }
    .form-horizontal .control-label {
        float: left;
        width: 160px;
        padding-top: 5px;
        text-align: right;
    }
    .form-horizontal .controls {
        margin-left: 180px;
    }
    select.m-wrap.small {
        width: 90px !important;
    }
    .m-wrap.medium {
        width: 180px !important;
    }
    .m-wrap.small {
        width: 50px !important;
    }
    .responsive.success .validate-inline {
        color: #468847;
    }

    .responsive.error .validate-inline {
        color: #b94a48;
    }

    .responsive.error .control-label, .responsive.error .help-block,
    .responsive.error .help-inline {
        color: #b94a48
    }

    .responsive.error .checkbox, .responsive.error .radio,
    .responsive.error input, .responsive.error select, .responsive.error textarea{
        color: #b94a48
    }

    .responsive.error input, .responsive.error select, .responsive.error textarea{
        border-color: #b94a48;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
        -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075)
    }

    .responsive.error input:focus, .responsive.error select:focus,
    .responsive.error textarea:focus {
        border-color: #953b39;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px
            #d59392;
        -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #d59392;
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #d59392
    }

    .responsive.error .input-prepend .add-on, .responsive.error .input-append .add-on{
        color: #b94a48;
        background-color: #f2dede;
        border-color: #b94a48
    }

    .responsive.success .control-label, .responsive.success .help-block,
    .responsive.success .help-inline {
        color: #468847
    }

    .responsive.success .checkbox, .responsive.success .radio,
    .responsive.success input, .responsive.success select,
    .responsive.success textarea {
        color: #468847
    }

    .responsive.success input, .responsive.success select,
    .responsive.success textarea {
        border-color: #468847;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
        -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075)
    }

    .responsive.success input:focus, .responsive.success select:focus,
    .responsive.success textarea:focus {
        border-color: #356635;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px
            #7aba7b;
        -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #7aba7b;
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 6px #7aba7b
    }

    .responsive.success .input-prepend .add-on, .responsive.success .input-append .add-on{
        color: #468847;
        background-color: #dff0d8;
        border-color: #468847
    }
    .beyondRmb{
    	color:#CD6563;
    	position:relative;
    	left:180px;
    }
   
   .bankAccount{
   		color:#b94a48;
   		position:relative;
    	left:180px;
   }
   .refundAccount{
   		color:#b94a48;
   		position:relative;
    	left:180px;
   }
</style>

<!-- BEGIN PAGE CONTAINER-->
<div class="container-fluid">
    <!-- BEGIN PAGE HEADER-->
    <div class="top">
        
    </div>
    <!-- END PAGE HEADER-->
    <!-- BEGIN PAGE CONTENT-->
    <input type="hidden" name="custAccountId" id="custAccountId" value="${custAccountApplyVo.custAccount.custAccountId}">
    <input type="hidden" name="lenderApplyId" id="lenderApplyId" value="${custAccountApplyVo.lenderApply.lenderApplyId}">
    <input type="hidden" name="payCustFinanceId" id="payCustFinanceId" value="${payCustFinance.custFinanceId}">
    <input type="hidden" name="refundCustFinanceId" id="refundCustFinanceId" value="${refundCustFinance.custFinanceId}">
	<input type="hidden" name="specialLimitId" id="specialLimitId" value="${specialLimitInfo.lenderConditionId}">
    <input type="hidden" name="specialBenefitId" id="specialBenefitId" value="${specialBenefitInfo.lenderConditionId}">
    <input type="hidden" name="specialData" value="specialCredits" <#if specialLimitInfo.lenderConditionVal?exists> checked="checked" </#if> />
    <input type="hidden" name="specialData" value="specialProfits" <#if specialBenefitInfo.lenderConditionVal?exists> checked="checked" </#if> />
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box blue" id="form_wizard_1">
                <div class="portlet-title">
                   <div class="caption"><i class="icon-reorder"></i>??????????????????</div>
                </div>
                <div class="portlet-body form">
                    <form action="#" class="form-horizontal" id="submit_form">
                    <div class="form-wizard" >
                        <div class="tab-content">
                            <div class="alert alert-error hide">
                                <button class="close" data-dismiss="alert"></button>
                                            ????????????????????????????????????????????????
                            </div>
                            <div class="alert alert-success hide">
                                <button class="close" data-dismiss="alert"></button>
                                    ????????????????????????!
                            </div>
                            <div class="tab-pane active" id="tab2">
                                <h4 class="form-section">????????????</h4>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" maxlength="20" name="contractCode" id="contractCode" source_val="${custAccountApplyVo.lenderApply.contractCode}" value="${custAccountApplyVo.lenderApply.contractCode}">
                                        </div>
                                    </div>
                                    <div class="span6 responsive" style="margin-left: -50px;">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap" name="productId" id="productId"  value="${custAccountApplyVo.lenderApply.productId}">
                                                <option value='-1'>?????????</option>
                                                <#if product?exists>
                                                    <#list product?keys as key>
                                                        <#if key==custAccountApplyVo.lenderApply.productId>
                                                            <option value=${key} selected="selected">${product[key]}</option>
                                                        <#else>    
                                                            <option value=${key}>${product[key]}</option> 
                                                        </#if> 
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                    
                                </div>
                                <div class="control-group" >
                                    
                                    <div class="span6 responsive"   name="expectLenderDate" id="expectLenderDate"  >
                                        <label class="control-label">????????????????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input class="span10 m-wrap m-ctrl-medium date-picker" readonly size="12" type="text" source_val="<#if custAccountApplyVo.lenderApply.expectLenderDateBegin?exists>${custAccountApplyVo.lenderApply.expectLenderDateBegin?string("yyyy-MM-dd")}</#if>"  value="${custAccountApplyVo.lenderApply.expectLenderDateBegin}" name="expectLenderDateBegin" id="expectLenderDateBegin" data-date-format="yyyy-mm-dd"  >
                                  		</div>
                                  	 </div> 
 										<div class="span6 responsive"  style="margin-left: -50px;" name="expectLenderDate" id="expectLenderDate"  >                                  		
                                        <label class="control-label">????????????????????????<span class="required">*</span></label>
                                        <div class="controls">        
                                            <input class="span10 m-wrap m-ctrl-medium date-picker" readonly size="12" type="text" source_val="<#if custAccountApplyVo.lenderApply.expectLenderDateEnd?exists>${custAccountApplyVo.lenderApply.expectLenderDateEnd?string("yyyy-MM-dd")}</#if>"  value="${custAccountApplyVo.lenderApply.expectLenderDateEnd}" name="expectLenderDateEnd" id="expectLenderDateEnd" data-date-format="yyyy-mm-dd" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
							 
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" maxlength="13" class="span10 m-wrap" name="lenderAmount" id="lenderAmount"  value="${custAccountApplyVo.lenderApply.lenderAmount}">
                                           <span class="rmb2"  style="line-height: 40px;">???</span>
                                        </div>
                                        <span class="beyondRmb" id="beyondRmb"></span>
                                    </div>
                                    <div class="span6 responsive" style="margin-left: -50px;">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="bigLenderAmount" id="bigLenderAmount" source_val="${custAccountApplyVo.biglenderAmount}" value="${custAccountApplyVo.biglenderAmount}">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="payWayId" id="payWayId"  source_val="${custAccountApplyVo.lenderApply.payWayId}">
                                                <option value='-1'>?????????</option>
                                                <#if payWay?exists>
                                                    <#list payWay?keys as key>
                                                        <#if key==custAccountApplyVo.lenderApply.payWayId>
                                                            <option value=${key} selected="selected">${payWay[key]}</option>
                                                        <#else>    
                                                            <option value=${key}>${payWay[key]}</option> 
                                                        </#if> 
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive" id="signMobileDiv">
                                        <label class="control-label" style="width: 19%;">???????????????<span class="required">*</span></label>
                                        <div class="controls" style="position: relative;right: 16%;">
                                            <input type="text" class="span10 m-wrap" maxlength="11" name="signMobile" id="signMobile"  source_val="${custAccountApplyVo.lenderApply.signMobile}" value="${custAccountApplyVo.lenderApply.signMobile}">
                                        </div>
                                    </div>
                                    
                                </div>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="recovery" id="recovery"  source_val="${custAccountApplyVo.lenderApply.recovery}">
                                                <option value='-1'>?????????</option>
                                                <#if recoveryMode?exists>
                                                    <#list recoveryMode?keys as key>
                                                        <#if key==custAccountApplyVo.lenderApply.recovery>
                                                            <option value="${key}" selected="selected">${recoveryMode[key]}</option>
                                                        <#else>    
                                                            <option value="${key}">${recoveryMode[key]}</option> 
                                                        </#if> 
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive" style="margin-left: -50px;">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input class="span10 m-wrap m-ctrl-medium date-picker" readonly size="12" type="text" source_val="<#if custAccountApplyVo.lenderApply.signDate?exists>${custAccountApplyVo.lenderApply.signDate?string("yyyy-MM-dd")}</#if>"  value="${custAccountApplyVo.lenderApply.signDate}" name="signDate" id="signDate" data-date-format="yyyy-mm-dd" >
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">?????????????????? </h4>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="payprovinceRegionCode" id="payprovinceRegionCode" source_val="${payCustFinance.provinceRegionCode}"  >
                                                <option value='-1'>?????????</option>
                                                <#if areas?exists>
                                                    <#list areas?keys as key>
                                                        <#if key==payCustFinance.provinceRegionCode>
                                                           <option value=${key} selected="selected">${areas[key]}</option>
                                                        <#else>
                                                            <option value=${key}>${areas[key]}</option>
                                                        </#if>
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive" style="position: relative;right: 75px;">
                                        <label class="control-label">????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="paycityRegionCode" id="paycityRegionCode" source_val="${payCustFinance.cityRegionCode}" >
                                                <option value='-1'>?????????</option>
                                                <#if paycityRegionCode?exists>
												     <#list paycityRegionCode as area>
													    <#if area.area_code_id==payCustFinance.cityRegionCode>   
														   <option value=${area.area_code_id} selected="selected">${area.area_code_name}</option>
													    <#else>
														   <option value=${area.area_code_id}>${area.area_code_name}</option>
													    </#if>
                                                    </#list>    
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="payBankCategory" id="payBankCategory" source_val="${payCustFinance.bankCategory}">
                                                <option value='-1'>?????????</option>
                                                <#if bankCategory?exists>
                                                    <#list bankCategory?keys as key>
                                                        <#if key==payCustFinance.bankCategory>
                                                            <option value=${key} selected="selected">${bankCategory[key]}</option>
                                                        <#else>
                                                            <option value=${key}>${bankCategory[key]}</option>
                                                        </#if> 
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="paySubBank" id="paySubBank" source_val="${payCustFinance.subBank}">
                                                <option value='-1'>?????????</option>
                                                <#if SubBankName?exists>
                                                    <#list SubBankName as area>
                                                        <#if area.area_code_id==payCustFinance.subBank>   
														   	<option value=${area.area_code_id} selected="selected">${area.area_code_name}</option>
													    <#else>
													  	 	<option value=${area.area_code_id}>${area.area_code_name}</option>
													    </#if>
                                                    </#list>
                                                </#if>
                                                <option value=-2>??????</option>
                                        	</select>
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group">
                					 <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" maxlength="40" class="span10 m-wrap" name="payOtherAddress" id="payOtherAddress" value="${payCustFinance.subBank}">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                				</div>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="payAccountName" id="payAccountName" value="${custAccountApplyVo.custAccount.custName}" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" maxlength="30" class="span10 m-wrap" name="payAccountNum" id="payAccountNum" value="${payCustFinance.accountNum}"  source_val="${payCustFinance.accountNum}">
                                        </div>
                                        <span class="bankAccount" id="bankAccount"></span>
                                    </div>
                                </div>
                
                                <h4 class="form-section">?????????????????? </h4>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="refundprovinceRegionCode" id="refundprovinceRegionCode" source_val="${refundCustFinance.provinceRegionCode}" >
                                                <option value='-1'>?????????</option>
                                                <#if areas?exists>
                                                    <#list areas?keys as key>
                                                        <#if key==refundCustFinance.provinceRegionCode>
                                                           <option value=${key} selected="selected">${areas[key]}</option>
                                                        <#else>
                                                            <option value=${key}>${areas[key]}</option>
                                                        </#if> 
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive" style="position: relative;right: 75px;">
                                        <label class="control-label">????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="refundcityRegionCode" id="refundcityRegionCode" source_val="${refundCustFinance.cityRegionCode}" >
                                                <option value='-1'>?????????</option>
                                                <#if refundcityRegionCode?exists>
														     <#list refundcityRegionCode as area>
															    <#if area.area_code_id=refundCustFinance.cityRegionCode>
																   <option value=${area.area_code_id} selected="selected">${area.area_code_name}</option>
															    <#else>
																   <option value=${area.area_code_id}>${area.area_code_name}</option>
															    </#if>
														 </#list>
													  </#if>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap bank" name="refundBankCategory" id="refundBankCategory" source_val="${refundCustFinance.bankCategory}">
                                                <option value='-1'>?????????</option>
                                                <#if bankCategory?exists>
                                                    <#list bankCategory?keys as key>
                                                        <#if key==refundCustFinance.bankCategory>
                                                           <option value=${key} selected="selected">${bankCategory[key]}</option>
                                                        <#else>
                                                            <option value=${key}>${bankCategory[key]}</option>
                                                        </#if> 
                                                    </#list>
                                                </#if>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                             <select class="span10 m-wrap bank" name="refundSubBank" id="refundSubBank" source_val="${refundCustFinance.subBank}">
                                                <option value='-1'>?????????</option>
                                                <#if refundSubBankName?exists>
                                                    <#list refundSubBankName as area>
                                    				    <#if area.area_code_id==refundCustFinance.subBank>   
														   	<option value=${area.area_code_id} selected="selected">${area.area_code_name}</option>
													    <#else>
													  	 	<option value=${area.area_code_id}>${area.area_code_name}</option>
													    </#if>
                                                    </#list>
                                                </#if>
                                                <option value=-2>??????</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                				<div class="control-group">
                					 <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" maxlength="40" class="span10 m-wrap" name="refundOtherAddress" id="refundOtherAddress" value="${refundCustFinance.subBank}">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                				</div>
                                <div class="control-group">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="refundAccountName" id="refundAccountName" value="${custAccountApplyVo.custAccount.custName}">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" maxlength="30" class="span10 m-wrap" name="refundAccountNum" id="refundAccountNum" value="${refundCustFinance.accountNum}" source_val="${refundCustFinance.accountNum}" >
                                        </div>
                                        <span class="refundAccount" id="refundAccount"></span>
                                    </div>
                                </div>  
                                <h4 class="form-section">
	                                
	                                      	?????????????????????&nbsp;&nbsp;
	                                    
                                </h4>
                                <br/>
                                <div class="control-group" id ="specialCredits" style="height:auto" >
                                    <div class="span6 responsive" >
                                        <label class="control-label" >???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" maxlength="13" class="span10 m-wrap" id ="specialLimit" disabled="disabled"  name="specialLimit" value="${specialLimitInfo.lenderConditionVal}" source_val="${specialLimitInfo.lenderConditionVal}">
                                       		<span class="rmb3" style="line-height: 40px;">???</span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive" style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <textarea rows="3" maxlength="200" disabled="disabled" class="span10 m-wrap" type="text" id = "specialLimitRemark" name="specialLimitRemark" source_val="${specialLimitInfo.lenderConditionRemark}">${specialLimitInfo.lenderConditionRemark}</textarea>
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div> 
                                <br/>
                                <div class="control-group" id ="specialProfits" style="height:auto" >  
                                    <div class="span6 responsive" >
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" maxlength="13" class="span10 m-wrap" disabled="disabled" id = "specialBenefit" name="specialBenefit" value="${specialBenefitInfo.lenderConditionVal}" source_val="${specialBenefitInfo.lenderConditionVal}">
                                        	<span class="percent"  style="line-height: 40px;">%</span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive" style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <textarea rows="3" maxlength="200" class="span10 m-wrap" type="text" disabled="disabled" id = "specialBenefitRemark" name="specialBenefitRemark" source_val="${specialBenefitInfo.lenderConditionRemark}">${specialBenefitInfo.lenderConditionRemark}</textarea>
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                             </div>
                        </div>
                    </div>
                    </form>

                    <div class="form-actions clearfix">
                     	<a href="javascript:;" class="btn blue"  id="saveChange" onclick="doInvestSaveChange()" >
	                      	??????
	                	</a>
	               		<a href="javascript:;" class="btn red" onclick="doInvestCloseDiv()">
	                      	??????
	               		</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- END PAGE CONTENT-->         

</div>


<!-- END PAGE CONTAINER-->

<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script>
var base = "${baseUrl}"; 
var resBase = "${resRoot}";
</script>
<script src="${baseUrl}/assets/js/custApply/create.js" type="text/javascript"></script> 
<script src="${baseUrl}/assets/js/form-wizard.js"></script>
<script src="${baseUrl}/assets/js/investInfoChange/save.js" type="text/javascript"></script> 
<script src="${baseUrl}/assets/js/checkDate/checkDate.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script>
    (function($) {
        $(function() {
        	//App.init();
            FormWizard.init();
            FormComponents.init();
			investInfo();	
			$("#specialLimitRemark").attr("disabled", "disabled");
			$("#specialBenefitRemark").attr("disabled", "disabled");
        });
    })(jQuery);
    
</script>
 