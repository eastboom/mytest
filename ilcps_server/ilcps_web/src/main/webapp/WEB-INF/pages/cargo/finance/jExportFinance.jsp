<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	
	<script type="text/javascript">
	$(function(){
		var v_columns = [[
	           		{field:'checkbox_',title:'',checkbox:true},    
	           		{field:'eid',title:'报运单号',width:300},  
	           		{field:'number',title:'产品/附件数',width:100},
	           		{field:'pid',title:'装箱单号',width:100},    
	           	]];
		// 工具栏
        var v_toolbar = [{
        		text : "出口商品报运单",
        		iconCls: 'icon-edit',
        		handler: function(){
        			var selRows = $("#dataList").datagrid("getSelections");
        			if (selRows == null  || selRows.length != 1) {
        				$.messager.alert("提示","先选中一行，再点击操作！","warning");
        				return;
        			}
        			var eid = selRows[0].eid;
        			var pid = selRows[0].pid;
        			// 设置id到隐藏域
        			$("#invoiceId").val(pid);
        			$("#eid").val(eid);
        			document.forms[0].action = "financeAction_printExport";
        			document.forms[0].submit();
        		}
        	}]
		
		$("#dataList").datagrid({
			url : "${ctx}/cargo/financeAction_exportFinance?id="+$("#id").val(), // 后台action地址
			columns : v_columns,
			toolbar:v_toolbar,
			fitColumns:true,
			singleSelect:true,
			pagination:true,
		});
	})
	
	</script>
</head>

<body>
<form name="icform" method="post">
      <input type="hidden" id="eid" name="eid" value=""/>
      <input type="hidden" id="id" name="id" value="${id}"/>
      <input type="hidden" id="invoiceId" name="invoiceId" value=""/>
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   <div class="eXtremeTable" >
		<table id="dataList" class="tableRegion" width="98%" >
		</table>
	</div>
</body>
</html>