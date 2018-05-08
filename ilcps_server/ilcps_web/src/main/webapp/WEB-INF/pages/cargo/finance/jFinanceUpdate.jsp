<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
	<script type="text/javascript">
	$(function(){
		var v_columns = [[
	           		{field:'checkbox_',title:'',checkbox:true},    
	           		{field:'id',title:'发票号',width:300},    
	           		{field:'scNo',title:'合同号',width:100},    
	           		{field:'blNo',title:'外贸单证',width:100},
	           		{field:'tradeTerms',title:'贸易条款',width:100}
	           	]];
		$("#dataList").datagrid({
			url : "${ctx}/cargo/financeAction_ajaxInvoice", // 后台action地址
			columns : v_columns,
			fitColumns:true,
			singleSelect:true,
			pagination:true,
		});
	})
	
	function financeUpdate_fromSubmit(){
		var selRows = $("#dataList").datagrid("getSelections");
			if (selRows != null  && selRows.length >1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			if (selRows != null  && selRows.length == 1) {
				var id = selRows[0].id;
				$("#invoiceId").val(id);
			}
			document.forms[0].action = "financeAction_update";
			document.forms[0].submit();
	}
	</script>
</head>

<body>
<form name="icform" method="post">
     <input type="hidden" name="id" value="${id }">
     <input type="hidden" name="state" value="${state }">
     <input type="hidden" id="invoiceId" name="invoiceId" value="0">
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="financeUpdate_fromSubmit();this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
      更新财务报运单
  </div> 
  </div>
  </div>
  
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">制单人：</td>
	            <td class="tableContent"><input type="text" name="inputBy" value="${inputBy }"/></td>
	            <td class="columnTitle">制单日期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="inputDate"
	            	 value="<fmt:formatDate value='${inputDate}' pattern='yyyy-MM-dd' />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>	
	        <%-- <tr>
	            <td class="columnTitle">发票：</td>
	             <td class="tableContent">
	            	<input class="easyui-combobox" name="invoice.id"  style="width:300px" value="${invoice.id}"
    					data-options="valueField:'id',textField:'id',url:'${ctx}/cargo/financeAction_ajaxInvoice'" />  
	            </td>
	        </tr> --%>	
		</table>
	</div>
 	<div class="eXtremeTable" >
		<table id="dataList" class="tableRegion" width="98%" >
		</table>
	</div>
 
</form>
</body>
</html>

