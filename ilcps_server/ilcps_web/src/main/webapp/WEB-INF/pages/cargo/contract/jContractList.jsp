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
   		{field:'contractNo',title:'合同号',width:100},
   		{field:'customName',title:'客户名称',width:100},    
   		{field:'xxx',title:'货物数/附件数 ',width:100,
        	formatter: function(value,row,index){
        		return row.productNum +"/" + row.extcNum;
        	}
        },
   		{field:'inputBy',title:'制单人',width:100},
   		{field:'checkBy',title:'审单人',width:100},
   		{field:'inspector',title:'验货员',width:100},
   		{field:'signingDate',title:'签单日期',width:100,
        	formatter: function(value,row,index){
        		return parseDate(value);
        	}
        },
   		{field:'deliveryPeriod',title:'交货期限 ',width:100,
        	formatter: function(value,row,index){
        		return parseDate(value);
        	}
        },
   		{field:'shipTime',title:'船期',width:100,
        	formatter: function(value,row,index){
        		return parseDate(value);
        	}
        },
   		{field:'tradeTerms',title:'贸易条款',width:100},
   		{field:'totalAmount',title:'总金额 ',width:100},
   		{field:'state',title:'状态',width:100,
   			formatter: function(value,row,index){
        		if (value == 0){
        			return "草稿";
        		}else if (value == 1) {
        			return "已上报待报运";
        		} else if (value == 2){
        			return "已报运";
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
			var url = "${ctx}/cargo/contractAction_toview";
			selectRowSubmit("dataList",url);
		}
	},
	{
		iconCls: 'icon-add',
		text : "新增",
		handler: function(){
			// 提交后台，通过后台转发到jDeptCreate.jsp
			location.href = "${ctx}/cargo/contractAction_tocreate";
		}
	},'-',{
		iconCls: 'icon-edit',
		text : "修改",
		handler: function(){
			var url = "${ctx}/cargo/contractAction_toupdate";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-remove',
		text : "删除",
		handler: function(){
			var url = "${ctx}/cargo/contractAction_delete";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-lock',
		text : "提交",
		handler: function(){
			var url = "${ctx}/cargo/contractAction_updateContractState";
			
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
			// 提交数据： 购销合同id
			var id = selRows[0].id;
			$("#id").val(id);
			
			// 提交数据： 购销合同状态
			$("#state").val("1");
			
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	},{
		iconCls: 'icon-cancel',
		text : "取消",
		handler: function(){
			var url = "${ctx}/cargo/contractAction_updateContractState";
			
			var selRows = $("#dataList").datagrid("getSelections");
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			// 判断状态
			if (selRows[0].state != 1) { // 只有“已上报待报运”，才可以点击取消。
				$.messager.alert("提示","只有“已上报待报运”，才可以点击取消！","warning");
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
	},{
		iconCls: 'icon-edit',
		text : "合同货物",
		handler: function(){
			
			// 判断是否选中
			var selRows = $("#dataList").datagrid("getSelections");
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			
			// 进入货物模块，传入购销合同id
			location.href="${ctx}/cargo/contractProductAction_contractProduct?contract.id=" + selRows[0].id;
		}
	}]

	// 初始化函数
	$(function(){
		$('#dataList').datagrid({   
		    url:'${ctx}/cargo/contractAction_listResult',    
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
    购销合同列表
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

