<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
<script type="text/javascript" src="${ctx }/js/util.js"></script>
<script>
	var v_columns = [[    
		{field:'chk',title:'',checkbox:true},    
        {field:'id',title:'角色编号',width:100},    
        {field:'name',title:'角色名称',width:100}
    ]]
	var v_toolbar = [{
		iconCls: 'icon-add',
		text : "新增",
		handler: function(){
			// 提交后台，通过后台转发到jDeptCreate.jsp
			location.href = "${ctx}/sysadmin/roleAction_tocreate";
		}
	},'-',{
		iconCls: 'icon-edit',
		text : "修改",
		handler: function(){
			var url = "${ctx}/sysadmin/roleAction_toupdate";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-remove',
		text : "删除",
		handler: function(){
			var url = "${ctx}/sysadmin/roleAction_delete";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-ok',
		text : "角色权限",
		handler: function(){
			var url = "${ctx}/sysadmin/roleAction_roleModule";
			selectRowSubmit("dataList",url);
		}
	}]

	// 初始化函数
	$(function(){
		$('#dataList').datagrid({   
		    url:'${ctx}/sysadmin/roleAction_listResult',    
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
    角色列表
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

