<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/util.js"></script>
	<script type="text/javascript">	    
	var v_columns = [[
   		{field:'checkbox_',title:'',checkbox:true},    
   		{field:'id',title:'编号',width:100},    
   		{field:'scNo',title:'包含的合同号',width:100},
   		{field:'blNo',title:'提单号',width:100},   
   		
   		{field:'tradeTerms',title:'贸易条款',width:100},
   		{field:'state',title:'发票状态',width:100,
   			
   			formatter: function(value,row,index){
        		if (value == 0){
        			return "草稿";
        		}else if (value == 1) {
        			return "可新增财务报运单";
        		} else if (value == 2){
        			return "已有财务报运单";
        		}
        	}	
   		}
   	]];
	var v_toolbar = [
	{
		iconCls: 'icon-add',
		text : "新增",
		handler: function(){
			// 提交后台，通过后台转发到jDeptCreate.jsp
			location.href = "${ctx}/cargo/invoiceAction_tocreate";
		}
	},
	{
		iconCls: 'icon-edit',
		text : "修改",
		handler: function(){
			var url = "${ctx}/cargo/invoiceAction_toupdate";
			selectRowSubmit("dataList",url);
		}
	},
	{
		iconCls: 'icon-remove',
		text : "删除",
		handler: function(){
			
			var selRows = $("#dataList").datagrid("getSelections");
			
			//2. 判断
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			var state = selRows[0].state;
			var delId = selRows[0].id;
			
			if(state != 0){
				$.messager.alert("提示","请选择草稿的发票进行操作","warning");
				return;
			}
			
			var url = "${ctx}/cargo/invoiceAction_delete?delId="+delId;
			
			document.forms[0].action = url;
			document.forms[0].submit();
		
			
		}
	},
	{
		iconCls: 'icon-lock',
		text : "提交",
		handler: function(){
			var selRows = $("#dataList").datagrid("getSelections");
			
			//2. 判断
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			var state = selRows[0].state;
			var delId = selRows[0].id;
			
			if(state != 0){
				$.messager.alert("提示","请选择草稿的发票进行操作","warning");
				return;
			}
			
			var url = "${ctx}/cargo/invoiceAction_updateInvoiceState?delId="+delId +"&state=1";
			
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	},
	{
		iconCls: 'icon-cancel',
		text : "取消",
		handler: function(){
			var selRows = $("#dataList").datagrid("getSelections");
			
			//2. 判断
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			var state = selRows[0].state;
			var delId = selRows[0].id;
			
			if(state != 1){
				$.messager.alert("提示","请选择可新增财务报运单的发票进行操作","warning");
				return;
			}
			
			var url = "${ctx}/cargo/invoiceAction_updateInvoiceState?delId="+delId+"&state=0";
			
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	},
	{
		iconCls: 'icon-print',
		text : "生成发票",
		handler: function(){
			var selRows = $("#dataList").datagrid("getSelections");
			//2. 判断
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			var delId = selRows[0].id;
			var state = selRows[0].state;
			
			if(state == 0){
				$.messager.alert("提示","不能对草稿状态的发票进行操作","warning");
				return;
			}
			
			var url = "${ctx}/cargo/invoiceAction_printInvoice?delId="+delId;
			
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	}
	
	]

	// 初始化函数
	$(function(){
		$('#dataList').datagrid({   
		    url:'${ctx}/cargo/invoiceAction_listResult',    
		    columns:v_columns,
		    toolbar: v_toolbar,
		    pagination : true,
		    fitColumns : true,
		}); 
	})
	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="id" id="id">
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
  <img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
 发票列表
  </div> 
  </div>
  </div>
<div>

<div class="eXtremeTable" >
 	<table id="dataList"></table>
</div>
</form>
</body>
</html>

