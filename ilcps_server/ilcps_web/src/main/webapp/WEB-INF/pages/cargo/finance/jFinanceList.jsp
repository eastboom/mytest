<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx}/js/util.js"></script>
	
	<script type="text/javascript">
	

$(function(){
	
	var v_columns = [[
           		{field:'checkbox_',title:'',checkbox:true},    
           		{field:'id',title:'财务报运号',width:300},    
           		{field:'inputBy',title:'制单人',width:100},    
           		{field:'inputDate',title:'制单日期',width:100,formatter:function(value,row,index){
            			return parseDate(value);
            		}},
           		{field:'state',title:'状态',width:100,
            			formatter:function(value,row,index){
            				if (value == 0) return "草稿";
            				else if (value == 1) return "未收款"
            				else if (value == 2) return "已收款";
            			}
            		}
           	]];
	          var v_toolbar = [{
	          		text : "查看",
	          		iconCls: 'icon-search',
	          		handler: function(){
	          			selectRowSubmit("dataList","${ctx}/cargo/financeAction_toview");
	          		}
	          	},{
	          		text : "新增",
	          		iconCls: 'icon-add',
	          		handler: function(){
	          			location.href = "${ctx}/cargo/financeAction_tocreate";
	          		}
	          	},{
	          		text : "修改",
	          		iconCls: 'icon-edit',
	          		handler: function(){
	          			var selRows = $("#" + "dataList").datagrid("getSelections");
	          			if (selRows == null  || selRows.length != 1) {
	          				$.messager.alert("提示","先选中一行，再点击操作！","warning");
	          				return;
	          			}
	          			if (selRows[0].state !=0) {
	          				$.messager.alert("提示","请选中未收款的报运单,再点击操作！","warning");
	          				return;
	          			}
	          			var id = selRows[0].id;
	          			$("#id").val(id);
	          			document.forms[0].action ="${ctx}/cargo/financeAction_toupdate";
	          			document.forms[0].submit();
	          		}
	          	},{
	          		text : "删除",
	          		iconCls: 'icon-remove',
	          		handler: function(){
	          			selectRowSubmit("dataList","${ctx}/cargo/financeAction_delete");
	          		}
	          	},{
	          		text : "提交",
	          		iconCls: 'icon-lock',
	          		handler: function(){
	          			var url = "${ctx}/cargo/financeAction_commit";
	        			var selRows = $("#dataList").datagrid("getSelections");
	        			if (selRows == null  || selRows.length != 1) {
	        				$.messager.alert("提示","先选中一行，再点击操作！","warning");
	        				return;
	        			}
	        			if (selRows[0].state != 0) {
	        				$.messager.alert("提示","非草稿状态，不能提交！","warning");
	        				return;
	        			}
	        			var id = selRows[0].id;
	        			$("#id").val(id);
	        			$("#state").val("1");
	        			document.forms[0].action = url;
	        			document.forms[0].submit();
	          		}
	          	},{
	          		text : "取消",
	          		iconCls: 'icon-cancel',
	          		handler: function(){
	          			var url = "${ctx}/cargo/financeAction_concel";
	        			var selRows = $("#dataList").datagrid("getSelections");
	        			if (selRows == null  || selRows.length != 1) {
	        				$.messager.alert("提示","先选中一行，再点击操作！","warning");
	        				return;
	        			}
	        			if (selRows[0].state != 1) {
	        				$.messager.alert("提示","非未收款状态，不能取消！","warning");
	        				return;
	        			}
	        			var id = selRows[0].id;
	        			$("#id").val(id);
	        			$("#state").val("0");
	        			document.forms[0].action = url;
	        			document.forms[0].submit();
	          		}
	          	},{
	          		text : "报运单",
	          		iconCls: 'icon-edit',
	          		handler: function(){
	          			selectRowSubmit("dataList","${ctx}/cargo/financeAction_toExportFinance");
	          		}
	          	}];
	
	
	$("#dataList").datagrid({
		url : "${ctx}/cargo/financeAction_listResult", // 后台action地址
		columns : v_columns,
		fitColumns:true,
		toolbar:v_toolbar,
		singleSelect:true,
		pagination:true,
	});
})
</script>
	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" id="id">
	<input type="hidden"  name="state" id="state">
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    财务报运列表
  </div> 
  
<div>


<div class="eXtremeTable" >
<table id="dataList" class="tableRegion" width="98%" >
	
</table>
</div>
 
</div>
 
 
</form>
</body>
</html>

