<style>
	.find-modal{
    	left: 31%;
		width: 80%;
		height:auto;
		top: 1% !important;
    }
</style>
<#include "/macro/base.ftl">
<div class="container-fluid">
    <@head title="重新匹配" home="匹配管理" item={"重新匹配":"/match/repeat/list.htm"} />
    <@portlet title="重新匹配" formId="queryForm">
    	<div class="row-fluid h50">
	    	<@div title="待匹配金额-起（万）">
	        	<@text id="matchAmountFrom" name="matchAmountFrom"/>
	        </@div>
	        <@div title="待匹配金额-止（万）">
	        	<@text id="matchAmountTo" name="matchAmountTo"/>
	        </@div>
	    </div>
	    <div class="row-fluid h50">
	    	 <@div title="出借方式">
	        	<@select source=product name="productId" id="productId"/>
	        </@div>
	        <@div title="出借编号">
	        	<@text id="lenderCode" name="lenderCode"/>
	        </@div>
	    </div>
	    <div class="row-fluid h50">
	        <div class="span5 responsive" style="margin-left: 635px;">
	            <@query/><@reset/>
	        </div>
	    </div>
	    <div class="row-fluid" id="statBody"></div>
	    <@line/>
	    <@datatable id="result" items=["#","客户姓名","身份证","出借编号","出借金额","待匹配金额","已匹配金额","出借方式","退回债权数","账单日","首期账单日期","首次匹配日期","操作"] />
	</@portlet>
</div>
<@modal id="findModal" name="find-modal"/>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="${baseUrl}/assets/js/app.js" type="text/javascript"></script>
<script src="${baseUrl}/assets/js/form-components.js" type="text/javascript"></script>
<script src="${baseUrl}/assets/js/checkDate/checkDate.js" type="text/javascript"></script>
<script src="${baseUrl}/assets/js/reset/reset.js" type="text/javascript"></script>
<script src="${baseUrl}/assets/js/credit/current/list.js" type="text/javascript"></script>  
<script src="${baseUrl}/assets/js/match/repeat/list.js" type="text/javascript"></script>  
<!-- END PAGE LEVEL SCRIPTS -->
<script>
	var base = "${baseUrl}";
	var resBase = "${resRoot}";
    (function($) {
        $(function() {
            App.init();
            FormComponents.init();
        });
    })(jQuery);    
</script>
 