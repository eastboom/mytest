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
   		{field:'id',title:'编号',width:300},    
   		{field:'shipper',title:'货主',width:100},
   		{field:'orderType',title:'海运/空运',width:100},    
   		{field:'lcNo',title:'信用证 ',width:100},
   		{field:'portOfLoading',title:'装运港',width:100},
   		{field:'portOfTrans',title:'转运港',width:100},
   		{field:'portOfDischarge',title:'卸货港',width:100},
   		{field:'loadingDate',title:'装期',width:100,
        	formatter: function(value,row,index){
        		return parseDate(value);
        	}
        },
   		{field:'createTime',title:'制单期限 ',width:100,
        	formatter: function(value,row,index){
        		return parseDate(value);
        	}
        },
   		{field:'state',title:'状态',width:100,
   			formatter: function(value,row,index){
        		if (value == 0){
        			return "草稿";
        		}else if (value == 1) {
        			return "已提交，待处理";
        		}else if (value == 2) {
        			return "已处理";
        		} 
        	}	
   		}
   	]];
	var v_toolbar = [
	{
		iconCls: 'icon-search',
		text : "查看",
		handler: function(){
			// 提交后台，通过后台转发到jDeptCreate.jsp
			var url = "${ctx}/cargo/shippingOrderAction_toview";
			selectRowSubmit("dataList",url);
		}
	},
	{
		iconCls: 'icon-add',
		text : "新增",
		handler: function(){
			// 提交后台，通过后台转发到jShippingOrderCreate.jsp
			location.href = "${ctx}/cargo/shippingOrderAction_tocreate";
		}
	},'-',{
		iconCls: 'icon-edit',
		text : "修改",
		handler: function(){
			var url = "${ctx}/cargo/shippingOrderAction_toupdate";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-remove',
		text : "删除",
		handler: function(){
			var url = "${ctx}/cargo/shippingOrderAction_delete";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-lock',
		text : "提交",
		handler: function(){
			var url = "${ctx}/cargo/shippingOrderAction_updateContractState";
			
			var selRows = $("#dataList").datagrid("getSelections");
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			// 判断状态
			if (selRows[0].state != 0) {
				$.messager.alert("提示","非草稿状态，不能提交！","warning");
				return;
			}
			// 提交数据： 委托单id
			var id = selRows[0].id;
			$("#id").val(id);
			
			// 提交数据： 委托单状态
			$("#state").val("1");
			
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	},{
		iconCls: 'icon-cancel',
		text : "取消",
		handler: function(){
			var url = "${ctx}/cargo/shippingOrderAction_updateContractState";
			
			var selRows = $("#dataList").datagrid("getSelections");
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			// 判断状态
			if (selRows[0].state != 1) { // 只有“已上报待报运”，才可以点击取消。
				$.messager.alert("提示","只有“已提交”，才可以点击取消！","warning");
				return;
			}
			// 提交数据： 购销合同id
			var id = selRows[0].id;
			$("#id").val(id);
			
			// 提交数据： 购销合同状态
			$("#state").val("0");
			
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	}]

	// 初始化函数
	$(function(){
		$('#dataList').datagrid({   
		    url:'${ctx}/cargo/shippingOrderAction_listResult',    
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
	<input type="hidden" name="state" id="state">
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
  <img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    委托单列表
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

