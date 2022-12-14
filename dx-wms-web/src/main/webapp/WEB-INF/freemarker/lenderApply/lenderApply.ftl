<style>
    .boder-right-scroll{
        width:100%;
        overflow-x:scroll;
    }
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
</style>
<!------------------------------------????????????  ???????????? ---------------------------------------->											
<div class="container-fluid" id="page1">
	<!-- BEGIN PAGE HEADER-->
	<div class="row-fluid">
		<div class="span12">
			<h3 class="page-title">
				????????????
			</h3>
			<ul class="breadcrumb">
				<li>
					<span>????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<span>??????????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<a href="${baseUrl}/index.htm">????????????</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- END PAGE HEADER-->
	<!-- BEGIN PAGE CONTENT-->
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box blue">
				<div class="portlet-title">
                	<div class="caption">
                		<i class="icon-reorder"></i>????????????
                	</div>
            	</div>
			
					<div class="portlet-body">
						<form action="#" role="form" class="form-horizontal" id="queryForm">
							<div class="row-fluid">
		                        <div class="span5 responsive">
		                            <label class="control-label" >???????????????</label>
		                            <div class="controls">
		                                <input type="text" class="span12 m-wrap" name="custName" id="custName">
		                            </div>
		                        </div>
		                        <div class="span5 responsive">
		                            <label class="control-label" >???????????????</label>
		                            <div class="controls">
		                                    <input type="text" class="span12 m-wrap" name="idCard" id="idCard">
		                            </div>
		                        </div>
		                    </div>
		                    <div class="row-fluid">
		                        <div class="span5 responsive">
		                            <label class="control-label" >&nbsp;</label>
		                            <div class="controls">
		                                &nbsp;
		                            </div>
		                        </div>
		                        <div class="span5 responsive" style="margin-left: 110px;">
		                            <input type="button" class="btn blue" value="??????" id="queryCust">
                            		<input type="button" class="btn blue" value="??????" id="resetCust">
		                        </div>
		                    </div>
						</form>
						<div class="form-section"></div>
                		<div style="overflow-x: hidden;">
                        	<table id="resultCustList" class="table table-striped table-bordered table-hover" >
                           		<thead>
                               		<th>????????????</th>
                               		<th>????????????</th>
                               		<th>????????????</th>
                               		<th>????????????</th>
                               		<th>??????</th>
                               		<th>??????</th>
                           		</thead>
                        	</table>
                		</div>
					</div>
			</div>
		</div>
	</div>
	<!-- END PAGE CONTENT-->         
</div>
<!------------------------------------???????????? ???????????????  ---------------------------------------->

<div class="container-fluid" id="page2">
    <!-- BEGIN PAGE HEADER-->
    <div class="row-fluid">
		<div class="span12">
			<h3 class="page-title">
				????????????
			</h3>
			<ul class="breadcrumb">
				<li>
					<span>????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<span>??????????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<a href="${baseUrl}/index.htm">????????????</a>
				</li>
			</ul>
		</div>
	</div>
    <!-- END PAGE HEADER-->
    <!-- BEGIN PAGE CONTENT-->
    <input type="hidden" name="custAccountId" id="custAccountId" value="">
    <input type="hidden" name="custCommId" id="custCommId" value="">
    <input type="hidden" name="custLinkmanId" id="custLinkmanId" value="">
    <input type="hidden" name="remitCustFinanceId" id="remitCustFinanceId" value="">
    <input type="hidden" name="refundCustFinanceId" id="refundCustFinanceId" value="">
    <input type="hidden" name="custProfessionId" id="custProfessionId" value="">
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box blue" id="form_wizard_1">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-reorder"></i> ????????????<input type="button" class="btn blue" value="+????????????" onclick="selectCust()" id="selectButton">
                    </div>
                </div>
                <div class="portlet-body form">
                
                    
                    <div class="form-wizard" >
      					
                        <div class="tab-content">
                            
                            <div class="tab-pane active" id="tab1">
                                <h4 class="form-section">????????????&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h4>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <input type="text" class="span10" name="custName" id="custName" value="">
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">??????(??????)???</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="custNameSpell" id="custNameSpell" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="sex" id="sex" value="">
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="nationality" id="nationality" value="??????">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <select class="span10 m-wrap" name="maritalStatus" id="maritalStatus" value="">
                                                <option value='-1'>?????????</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="commonLanguage" id="commonLanguage" value="??????">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive" >
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="idType" id="idType" value="">
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="idCard" id="idCard" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                			<input class="span10 m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" value="" name="birthDate" id="birthDate" >
                            			</div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="mobile" id="mobile" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????</label>
                                        <div class="controls">
											<input type="text" class="span10 m-wrap" name="profession" id="profession" value="">
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="industry" id="industry" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="companyName" id="companyName" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap"  name="post" id="post" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <select class="span10 m-wrap" name="education" id="education" value="">
                                                <option value='-1'>?????????</option>
                                            </select>
                                            <input type="text" class="span10 m-wrap"  name="post" id="post" value="">
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span12 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <div class="span7 responsive">
												<div class="span4 responsive" id="provinceRegionCodeDiv">	
													<select class="span10 m-wrap" name="provinceRegionCode" id="provinceRegionCode" value="" >
														<option value='-1'>????????????</option>
													</select>
												</div>
											    <div class="span3 responsive" id="cityRegionCodeDiv">	
												   <select class="span10 m-wrap" name="cityRegionCode" id="cityRegionCode" >
													  <option value='-1'>????????????</option>
												   </select>
											    </div>
											    <div class="span3 responsive" id="districtRegionCodeDiv">	
												   <select class="span10 m-wrap" name="districtRegionCode" id="districtRegionCode" >
													  <option value='-1'>????????????</option>
												   </select>
											    </div>	
										      </div>
                                             <div class="span6 responsive" style="margin-left: -80px;">
                                                <div class="span12 responsive"  id="streetInfoDiv">
                                                    <input type="text" class="m-wrap span10"  placeholder="????????????" name="streetInfo" id="streetInfo" value="" title=""/>
                                                </div>
                                            </div>   
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap"  name="zipCode" id="zipCode" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <div class="span4 responsive">
                                                <input type="text" class="span10 m-wrap"  name="areaCode" id="areaCode" value="">
                                            </div>
                                            <div class="span1 responsive" style="line-height: 30px;">
                                             - 
                                            </div>
                                            <div class="span5 responsive">
                                                <input type="text" class="span10 m-wrap"  name="telNum" id="telNum" value="">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="email" id="email" value="" >
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">????????????</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap"  name="wechat" id="wechat" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">?????????????????????   </h4>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap"   name="linkmanName" id="linkmanName" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">??????(??????)???</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap"  name="linkmanNameSpell" id="linkmanNameSpell" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap" name="linkmanSex" id="linkmanSex" value="">
                                                <option value='-1'>?????????</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="linkmanRelation" id="linkmanRelation" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <select class="span10 m-wrap" name="linkmanIdType" id="linkmanIdType" value="">
                                                <option value='-1'>?????????</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="linkmanIdCard" id="linkmanIdCard" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap"  name="linkmanMobile" id="linkmanMobile" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">???????????????</label>
                                        <div class="controls">
                                            <div class="span4 responsive">
                                                <input type="text" class="span10 m-wrap" name="linkmanAreaCode" id="linkmanAreaCode" value="" >
                                            </div>
                                            <div class="span1 responsive" style="line-height: 30px;">
                                             - 
                                            </div>
                                            <div class="span5 responsive">
                                                <input type="text" class="span10 m-wrap" name="linkmanTelNum" id="linkmanTelNum" value="" >
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">?????????????????? </h4>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                    <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap" name="remitBankCategory" id="remitBankCategory" value="">
                                                <option value='-1'>?????????</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="remitSubBank" id="remitSubBank" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="remitAccountName" id="remitAccountName" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="remitAccountNum" id="remitAccountNum" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <h4 class="form-section">?????????????????? </h4>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <select class="span10 m-wrap" name="refundBankCategory" id="refundBankCategory" value="">
                                                <option value='-1'>?????????</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="refundSubBank" id="refundSubBank" value="">
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="refundAccountName" id="refundAccountName" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">?????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="refundAccountNum" id="refundAccountNum" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>  
                                <h4 class="form-section">?????? </h4>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">?????????????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input readonly type="text" value="" name="msgWay" id="msgWay">
                                        </div>
                                    </div>
                                    <div class="span6 responsive"  style="margin-left: -50px;">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input readonly type="text" value="" name="openDate" id="openDate">
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input readonly type="text" value="" name="custSource" id="custSource">
                                        </div>
                                    </div>
                                    <div class="span6 responsive" id="custSourceOtherDiv" style="margin-left: -50px;">
                                        <label class="control-label">?????????</label>
                                        <div class="controls">
                                            <input type="text" class="span10 m-wrap" name="custSourceOther" id="custSourceOther" value="" >
                                            <span class="help-inline"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row-fluid">
                                    <div class="span6 responsive">
                                        <label class="control-label">???????????????<span class="required">*</span></label>
                                        <div class="controls">
                                            <input readonly type="text" value="" name="custCategory" id="custCategory">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                    </div>

                <div class="form-actions clearfix">
	                <a href="javascript:;" class="btn blue button-next"  id="save">
	                      	?????? <i class="m-icon-swapright m-icon-white"></i>
	                </a>
	                 <a href="javascript:;" class="btn blue button-next" id="nextPage">
	                                           ????????? 
	                </a>
	                 <a href="javascript:;" class="btn blue button-submit" onclick="doCreateUserAccount()">
	                    	???????????? <i class="m-icon-swapright m-icon-white"></i>
	                </a>
	               <a href="javascript:;" class="btn red" onclick="doCloseDiv()">
	                      	??????
	                </a>
	            </div>
            </div>
        </div>
    </div>
    </div>
    <!-- END PAGE CONTENT-->         
</div>	

<!------------------------------------????????????  ???????????? ---------------------------------------->											
<div class="container-fluid" id="page3">
	<!-- BEGIN PAGE HEADER-->
	<div class="row-fluid">
		<div class="span12">
			<h3 class="page-title">
				????????????
			</h3>
			<ul class="breadcrumb">
				<li>
					<span>????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<span>??????????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<a href="${baseUrl}/index.htm">????????????</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- END PAGE HEADER-->
	<!-- BEGIN PAGE CONTENT-->
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box blue">
				<div class="portlet-title">
                	<div class="caption">
                		<i class="icon-reorder"></i>????????????
                	</div>
            	</div>
			
					<div class="portlet-body">
						<form action="#" role="form" class="form-horizontal" id="queryForm">
							<h4 class="form-section">???????????? </h4>
							<div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">???????????????<span class="required">*</span></label>
                                    <div class="controls">
                                    	<select class="span10 m-wrap" name="custSource" id="product" value="">
                                            <option value='-1'>?????????</option>
                                            <#if products?exists>
                                                <#list products?keys as key>
                                                    <option value=${key}>${products[key]}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">&nbsp;</label>
                                    <div class="controls">
                                        &nbsp;
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">??????????????????(???)???<span class="required">*</span></label>
                                    <div class="controls">
                                    	 <input class="span10 m-wrap m-ctrl-medium date-picker" readonly name="expectLenderDateBegin" id="expectLenderDateBegin" data-date-format="yyyy-mm-dd">
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">??????????????????(???)???<span class="required">*</span></label>
                                    <div class="controls">
                                    	<input class="span10 m-wrap m-ctrl-medium date-picker" readonly name="expectLenderDateEnd" id="expectLenderDateEnd" data-date-format="yyyy-mm-dd">
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                            </div>

							<div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">????????????(???)???<span class="required">*</span></label>
                                    <div class="controls">	
                                    	 <input name="lenderAmount" id="lenderAmount" />
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">???????????????</label>
                                    <div class="controls">
                                    	<input id="upperCaseLenderAmount" readonly name="upperCaseLenderAmount" />
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                            </div>

							<div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">???????????????<span class="required">*</span></label>
                                    <div class="controls">
                                    	<select class="span10 m-wrap" name="custSource" id="payWay" value="">
                                            <option value='-1'>?????????</option>
                                            <#if payWays?exists>
                                                <#list payWays?keys as key>
                                                    <option value=${key}>${payWays[key]}</option>
                                                </#list>
                                            </#if>
											<option value='0'>??????</option>
                                        </select>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">??????????????? </label>
                                    <div class="controls">
                                        <input class="span10 m-wrap m-ctrl-medium date-picker" readonly name="signDate" id="signDate" data-date-format="yyyy-mm-dd">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">???????????????<span class="required">*</span></label>
                                    <div class="controls">
                                    	<select class="span10 m-wrap" name="custSource" id="payWay" value="">
                                            <option value='-1'>?????????</option>
                                            <#if accounts?exists>
                                                <#list accounts?keys as key>
                                                    <option value=${key}>${accounts[key]}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">&nbsp;</label>
                                    <div class="controls">
                                        &nbsp;
                                    </div>
                                </div>
                            </div>

							<div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">???????????????</label>
                                    <div class="controls">
                                    	<select class="span10 m-wrap" name="remitBankCategory" id="remitBankCategory" value="">
                                            <option value='-1'>?????????</option>
                                            <#if banks?exists>
                                                <#list banks?keys as key>
                                                    <option value=${key}>${banks[key]}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">??????:</label>
                                    <div class="controls">
										<input type="text" class="span10 m-wrap" name="remitSubBank" id="remitSubBank" value="" >
                                    </div>
                                </div>
                            </div>
				
							<div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">?????????</label>
                                    <div class="controls">
                                        <input type="text" class="span10 m-wrap" name="remitAccountName" id="remitAccountName" value="" >
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">?????????</label>
                                    <div class="controls">
                                        <input type="text" class="span10 m-wrap" name="remitAccountNum" id="remitAccountNum" value="" >
                                        <span class="help-inline"></span>
                                    </div>
                                </div>
                            </div>
							
							<h4 class="form-section">?????????????????? </h4>

							<div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">???????????????<span class="required">*</span></label>
                                    <div class="controls">
                                    	<select class="span10 m-wrap" name="isSpecialProfit" id="isSpecialProfit" value="">
                                            <option value='-1'>?????????</option>
                                            <#if banks?exists>
                                                <#list banks?keys as key>
                                                    <option value=${key}>${banks[key]}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                        <span class="help-inline"></span>
                                    </div>
                                </div>                                                                
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">????????????:<span class="required">*</span></label>
                                    <div class="controls">
										<input type="text" class="span10 m-wrap" name="lenderIncomeRatio" id="lenderIncomeRatio" value="" >%
                                    </div>
                                </div>
                            </div>
                            
                            <div class="row-fluid">
                                <div class="span6 responsive">
                                    <label class="control-label">?????????</label>
                                    <div class="controls">
                                    	 <textarea rows="5" cols="40" name="remark" id="remark"></textarea>	
                                        <span class="help-inline"></span>
                                    </div>
                                </div>                                                                
                                <div class="span6 responsive"  style="margin-left: -50px;">
                                    <label class="control-label">&nbsp;</label>
                                    <div class="controls">
										&nbsp;
                                    </div>
                                </div>
                            </div>
						</form>
					</div>
			</div>
		</div>
	</div>
	<!-- END PAGE CONTENT-->         
</div>

<!------------------------------------????????????  ???????????? ---------------------------------------->											
<div class="container-fluid" id="page4">
	<!-- BEGIN PAGE HEADER-->
	<div class="row-fluid">
		<div class="span12">
			<h3 class="page-title">
				????????????
			</h3>
			<ul class="breadcrumb">
				<li>
					<span>????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<span>??????????????????</span>
					<span class="icon-angle-right"></span>
				</li>
				<li>
					<a href="${baseUrl}/index.htm">????????????</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- END PAGE HEADER-->
	<!-- BEGIN PAGE CONTENT-->
	<div class="row-fluid">
		<div class="span12">
			<div class="portlet box blue">
				<div class="portlet-title">
                	<div class="caption">
                		<i class="icon-reorder"></i>????????????
                	</div>
            	</div>
			
					<div class="portlet-body">
					
						<div id="uploadDiv">
		                <!-- ??????????????? -->	
							<h4 class="form-section">
								??????????????????????????? &nbsp;&nbsp;&nbsp;&nbsp;
							</h4>	
			        		<form id="fileUploadFormFrom">
								<!-- ???????????? wmsCustOpenApply wmsCustLenderApply  wmsLenderCheck-->
								<input type="hidden" id="resKey" name="resKey" value="wmsCustOpenApply" >
								<!-- ?????? ??????-->
								<input type="hidden" id="appCode" name="appCode" value="wms" >
								<!-- ??????????????????????????? -->
								<input type="hidden" id="currentFileDir" name="currentFileDir" value="" >
								<!-- ???????????? -->
								<input type="hidden" id="cmAction" name="cmAction" value="uploadFile" >
								<!-- ????????????  -->
								<input type="hidden" id="userUniqueId" name="userUniqueId" value="${custAccountId}" >
								<input type="hidden" id="lenderCustCode" name="lenderCustCode" value="${lenderCustCode}" >
								<!-- ????????????????????? -->
								<input type="hidden" id="lenderUniqueId" name="lenderUniqueId" value="${lenderApplyId}" >
								<input type="hidden" id="lenderCode" name="lenderCode" value="${lenderCode}" >
									
								<div class="modal-body">	
									<div class="controls" style="margin-left: 0px">
										<div class="fileupload fileupload-new" data-provides="fileupload">
											<div class="input-append">
												<div class="uneditable-input">
													<i class="icon-file fileupload-exists"></i>
													<span class="fileupload-preview"></span>
												</div>
												<span class="btn btn-file">
													<span class="fileupload-new" id="file_choose">??????</span>
													<span class="fileupload-exists" id="file_update">??????</span>
													<input type="file" class="default" id="file" name="file">
												</span>
												<a href="#" class="btn fileupload-exists" data-dismiss="fileupload" id="file_del">??????</a>
												<button class="btn blue" id="upload">??????</button>
												<div class="uneditable-input" id="upLoadImg" style="display:none;text-align:center;width:23px;height:23px;border:0px">
													<img src="${resRoot}/image/u37.png">
												</div>
											</div>
										</div>
									</div>
								</div>
							</form>	
			            </div>	
			            <h4 class="form-section">
							???????????? &nbsp;&nbsp;&nbsp;&nbsp;
						</h4>
						<div class="control-group" id="vFolder" style="text-align:center;height:auto;">
						    <!-- ????????????????????? -->				
						</div>
						<br ><h4 class="form-section"></h4>	
						<div class="control-group" id="vFile" style="text-align:center;height:auto;">
							<!-- ?????????????????? -->
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
<script src="${baseUrl}/assets/js/lenderApply/lenderApply.js"></script>
<script src="${baseUrl}/assets/js/app.js" type="text/javascript"></script>
<script src="${baseUrl}/assets/js/form-wizard.js"></script>
<script src="${baseUrl}/assets/js/form-components.js"></script>

<!-- END PAGE LEVEL SCRIPTS -->
<script>
    (function($) {
        $(function() {
            App.init();
            FormComponents.init();

			uploadFiles("upload");
        });
    })(jQuery);
    
</script>		


