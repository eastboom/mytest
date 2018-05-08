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
  		{field:'id',title:'报运号',width:300},    
  		{field:'xxx',title:'商品数/附件数',width:100,
        	formatter: function(value,row,index){
        		return row.productNum +"/" + row.extcNum;
        	}
        },
  		{field:'lcno',title:'信用证号',width:100},    
  		{field:'consignee',title:'收货人及地址',width:100},    
  		{field:'shipmentPort',title:'装运港',width:100},    
  		{field:'destinationPort',title:' 目的港',width:100},    
  		{field:'transportMode',title:'运输方式',width:100},    
  		{field:'priceCondition',title:'价格条件',width:100},    
  		{field:'inputDate',title:'制单日期',width:100,formatter:function(value,row,index){
   			return parseDate(value);
   		}},
  		{field:'state',title:'状态',width:100,
   			formatter:function(value,row,index){
   			//0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务 6上报失败
   				if(value!=null){
   					
	   				if (value == 0) return "草稿";
	   				else if (value == 1) return "已上报待装箱";
	   				else if (value == 2) return "已装箱待委托";
	   				else if (value == 3) return "已委托待开发票";
	   				else if (value == 4) return "已开发票";
	   				else if (value == 5) return "已完成财务";
	   				else return "上报失败";
   				}
   			}
   		}
  	]];

// 工具栏
var v_toolbar = [{
		text : "查看",
		iconCls: 'icon-search',
		handler: function(){
			selectRowSubmit("dataList","${ctx}/cargo/exportAction_toview");
		}
	},{
		text : "修改",
		iconCls: 'icon-edit',
		handler: function(){
			selectRowSubmit("dataList","${ctx}/cargo/exportAction_toupdate");
		}
	},{
		text : "删除",
		iconCls: 'icon-remove',
		handler: function(){
			selectRowSubmit("dataList","${ctx}/cargo/exportAction_delete");
		}
	},{
		text : "电子报运",
		iconCls: 'icon-edit',
		handler: function(){
			var url = "${ctx}/cargo/exportAction_exportE";
			//1. 判断是否选中行
			var selRows = $("#dataList").datagrid("getSelections");
			if (selRows == null  || selRows.length != 1) {
				$.messager.alert("提示","先选中一行，再点击操作！","warning");
				return;
			}
			//2. 判断状态 (只有草稿、报运失败才可以点击电子报运)
			if (selRows[0].state != 0 && selRows[0].state != 3){
				$.messager.alert("提示","只有草稿状态、报运失败，才可以点击电子报运！","warning");
				return;
			}
			//3. 提交
			location.href = url + "?id=" + selRows[0].id;
		}
	}
];

$(function(){
	$("#dataList").datagrid({
		url : "${ctx}/cargo/exportAction_listResult", // 后台action地址
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
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    出口报运列表
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

