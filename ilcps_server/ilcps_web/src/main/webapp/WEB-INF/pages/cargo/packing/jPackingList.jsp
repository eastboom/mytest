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
   		{field:'seller',title:'卖方',width:100},
   		{field:'buyer',title:'买方',width:100},    
   		{field:'invoiceNo',title:'发票号',width:100},
   		{field:'invoiceDate',title:'发票日期',width:100,formatter:function(value,row,index){
   			return parseDate(value);
   		}},
   		{field:'marks',title:'唛头',width:100},
   		{field:'descriptions',title:'描述',width:100},
   		{field:'createTime',title:'创建日期',width:100,formatter:function(value,row,index){
   			return parseDate(value);
   		}},
   		{field:'createBy',title:'创建人',width:100},
   		{field:'createDept',title:'创建部门 ',width:100},
   		{field:'state',title:'状态',width:100,formatter:function(value,row,index){
   			if(value!=null){
   				if(value==0){
   					
		   			return "草稿";
   				}else if(value==1){
		   			return "已装箱待委托";
   					
   				}else if(value==2){
		   			return "已委托";
   					
   				}
   			}
   		}}
   	]];
	
	// 工具栏
		var v_toolbar = [
	{
		iconCls: 'icon-search',
		text : "查看",
		handler: function(){
			// 提交后台，通过后台转发到jDeptCreate.jsp
			var url = "${ctx}/cargo/packingListAction_toview";
			selectRowSubmit("dataList",url);
		}
	},
	{
		iconCls: 'icon-add',
		text : "新增",
		handler: function(){
			// 提交后台，通过后台转发到jDeptCreate.jsp
			location.href = "${ctx}/cargo/packingListAction_tocreate";
		}
	},'-',{
		iconCls: 'icon-edit',
		text : "修改",
		handler: function(){
			var url = "${ctx}/cargo/packingListAction_toupdate";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-remove',
		text : "删除",
		handler: function(){
			var url = "${ctx}/cargo/packingListAction_delete";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-lock',
		text : "提交",
		handler: function(){
			var url = "${ctx}/cargo/packingListAction_updatePackingListState";
			
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
			var url = "${ctx}/cargo/packingListAction_updatePackingListState";
			
			var selRows = $("#dataList").datagrid("getSelections");
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			// 判断状态
			if (selRows[0].state != 1) { // 只有“已上报待报运”，才可以点击取消。
				$.messager.alert("提示","只有“已装箱待委托”，才可以点击取消！","warning");
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
	}];
	
	$(function(){
		$("#dataList").datagrid({
			url : "${ctx}/cargo/packingListAction_listResult", // 后台action地址
			columns : v_columns,
			fitColumns:true,
			toolbar:v_toolbar,
			pagination:true,
		});
	});
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
   装箱管理列表
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

