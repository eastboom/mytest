<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/util.js"></script>
	<script type="text/javascript">
		//模块名 层数 权限标识 链接 类型 从属 状态
		var v_columns = [[
	   	    {field : "ck_",title:"",checkbox:true},
	   	    {field : "name",title:"模块名",width : "200"},
	   		{field : "layerNum",title : "层数",width : "100"},
	   		{field : "cpermission",title : "权限标识",width : "200"},
	   		{field : "curl",title : "链接",width : "200"},
	   		{field : "ctype",title : "类型 ",width : "100"},
	   		{field : "belong",title : "从属 ",width : "100"},
	   		{field : "state",title : "状态",width : "100"},
   		]];
		var v_toolbar = [{
			iconCls: 'icon-add',
			text : "新增",
			handler: function(){
				// 提交后台，通过后台转发到jDeptCreate.jsp
				location.href = "${ctx}/sysadmin/moduleAction_tocreate";
			}
		},'-',{
			iconCls: 'icon-edit',
			text : "修改",
			handler: function(){
				var url = "${ctx}/sysadmin/moduleAction_toupdate";
				selectRowSubmit("dataList",url);
			}
		},{
			iconCls: 'icon-remove',
			text : "删除",
			handler: function(){
				var url = "${ctx}/sysadmin/moduleAction_delete";
				selectRowSubmit("dataList",url);
			}
		}]

		// 初始化函数
		$(function(){
			$('#dataList').datagrid({   
			    url:'${ctx}/sysadmin/moduleAction_listResult',    
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
   <input type="hidden" id="id" name="id">
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
    模块列表
  </div> 
  </div>
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

