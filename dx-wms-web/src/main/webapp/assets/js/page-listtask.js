var TableManaged = function() {
	return {
		init : function() {
			if (!jQuery().dataTable) {
				return;
			}
			var modelsTable = $('#models-data')
					.dataTable(
							{
								"bFilter" : false,
								"bProcessing" : true,
								"sServerMethod" : "POST",
								"bServerSide" : true,
								"aLengthMenu" : [ 10, 20, 30, 50, 100],
								"iDisplayLength" : 10,
								"sPaginationType" : "bootstrap",
								"oLanguage" : {"sUrl" : "../media/js/jquery.dataTable.cn.js"},
								"bSort" : false,
								"sAjaxSource" : '../process/listTask.htm',
								"fnServerParams" : function(aoData) {
									var arr = $("#searchForm").serializeArray();
									for(var i=0;i<arr.length;i++){
										aoData.push(arr[i]);
									}
								},
								"aoColumns" : [{
									"mData" : "taskId",
									"bSortable" : false
								},{
									"mData" : "title",
									"bSortable" : false
								},  {
									"mData" : "requestUser",
									"bSortable" : false
								}, {
									"mData" : "requestDate",
									"bSortable" : false
								}, {
									"mData" : "processInstanceId",
									"bSortable" : false
								},{
									"mData" : "taskId",
									"bSortable" : false
								}],
								"aoColumnDefs" : [{
											sDefaultContent : '',
											'bSortable' : false,
											aTargets : [ '_all' ]
										},{
											'aTargets' : [ 0 ],
											"mData" : "",
											"mRender" : function(data, type, full) {
												return '<label style="width:100%;height:100%"><input type="checkbox" name="selectone" value="'+ data+ '"/></label>';
											}
										},{
											'aTargets' : [5],
											"mData" : "",
											"mRender" : function(data, type, full) {
												var str = '<button type="button" class="btn mini blue log" onclick=showHistory(' + data +')">';
													str = str + '<button type="button" class="btn mini red do" onclick=proTask(' + data +')">';

												return str;
											}
										}]
							});
			return modelsTable;
		}
	};
}();

function showHistory(taskId){
	window.location.href='${base}/process/prohistory.html';
}

function proTask(taskId){
	window.location.href='${base}/process/process.html';
}

$(document).ready(function(){
	App.init();
	var dataTable = TableManaged.init();

	$("#addBtn").click(function() {
		window.location.href=$("#smsBasePath").val()+'/smstemp/edit.html';
	});

	$("#editBtn").click(function() {
		var ids = getGridSelect("models-data");
		if(ids.length == 1){
			window.location.href=smsBasePath+'/smstemp/edit.html?id='+ids[0];
		}else{
			alert("?????????????????????");
		}
	});

	$('#previewBtn').click(function() {
		var ids = getGridSelect("models-data");
		if(ids.length == 1){
			window.location.href=smsBasePath+'/smstemp/preview.html?id='+ids[0];			
		}else{
			alert("?????????????????????");
		}
	});
	
	$("#cancelBatchBtn").click(function() {
		var ids = getGridSelect("models-data");
		if(ids.length==0){
			alert("???????????????");
		}else{
			for(var i=0; i< ids.length; i++){
				var status = $("#models-data"+" input[name='selectone'][value='"+ids[i]+"']").closest("tr").children().eq(4).text();
				if(status == '?????????'){
					alert("?????????????????????");
					return;
				}
			}
			if(confirm("???????????? ???????????? ???")){
				postAjax(
					smsBasePath+'/smstemp/cancel.json',
					{ids:ids.join(",")},
					function(data){
						dataTable.fnDraw();
					}
				);
			}
		}
	});

	$("#exportBatchBtn").click(function() {
		var ids = getGridSelect("models-data");
		if(ids.length==0){
			alert("???????????????");
		}else{
			window.location.href=smsBasePath+'/smstemp/export.html?ids=' + ids.join(",");
		}
	});
	
	$("#publishBatchBtn").click(function() {
		var ids = getGridSelect("models-data");
		if(ids.length==0){
			alert("???????????????");
		}else{
			for(var i=0; i< ids.length; i++){
				var status = $("#models-data"+" input[name='selectone'][value='"+ids[i]+"']").closest("tr").children().eq(3).text();
				if(status != '????????????'){
					alert("???????????? '????????????' ????????????");
					return;
				}
			}
			if(confirm("???????????? ???????????? ???")){
				postAjax(
					smsBasePath+'/smstemp/publish.json',
					{ids:ids.join(",")},
					function(data){
						dataTable.fnDraw();
					}
				);
			}
		}
	});
	$("#pauseBatchBtn").click(function() {
		var ids = getGridSelect("models-data");
		if(ids.length==0){
			alert("???????????????");
		}else{
			for(var i=0; i< ids.length; i++){
				var status = $("#models-data"+" input[name='selectone'][value='"+ids[i]+"']").closest("tr").children().eq(4).text();
				if(status == '?????????'){
					alert("?????????????????????");
					return;
				} 
				if(status != '?????????'){
					alert("???????????? '?????????' ????????????");
					return;
				}
			}
			if(confirm("???????????? ???????????? ???")){
				postAjax(
					smsBasePath+'/smstemp/pause.json',
					{ids:ids.join(",")},
					function(data){
						dataTable.fnDraw();
					}
				);
			}
		}
	});
	
	$('#btn-inquiry').click(function() {
		dataTable.fnDraw();
	});
	
	$("#models-data input[name='selectall']").change(function(){
		if($(this).attr("checked")=="checked"){
			$("#models-data input[name='selectone']").attr("checked","checked");
		} else {
			$("#models-data input[name='selectone']").removeAttr("checked");
		}
	});
	
});