<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script type="text/javascript" src="${ctx }/js/util.js"></script>
<script>
	var v_columns = [[    
		{field:'chk',title:'',checkbox:true},    
        {field:'id',title:'员工编号',width:100},    
        {field:'userName',title:'用户名',width:100},
        {field:'userinfo.name',title:'真实姓名',width:100,
        	formatter: function(value,row,index){
        		if (row.userinfo){
        			return row.userinfo.name;
        		}
        	}
        },
        {field:'userinfo.joinDate',title:'入职时间',width:100,
        	formatter: function(value,row,index){
        		if (row.userinfo){
        			if (row.userinfo.joinDate){
        				var d = new Date(row.userinfo.joinDate);
        				var y = d.getFullYear();
        				var m = d.getMonth() + 1;
        				var day = d.getDate();
        				return y + "-" + m + "-" + day;
        			}
        		}
        	}
        }
    ]]
	var v_toolbar = [{
		iconCls: 'icon-add',
		text : "新增",
		handler: function(){
			// 提交后台，通过后台转发到jDeptCreate.jsp
			location.href = "${ctx}/sysadmin/userAction_tocreate";
		}
	},'-',{
		iconCls: 'icon-edit',
		text : "修改",
		handler: function(){
			var url = "${ctx}/sysadmin/userAction_toupdate";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-remove',
		text : "删除",
		handler: function(){
			var url = "${ctx}/sysadmin/userAction_delete";
			selectRowSubmit("dataList",url);
		}
	},{
		iconCls: 'icon-ok',
		text : "用户角色",
		handler: function(){
			var url = "${ctx}/sysadmin/userAction_userRole";
			selectRowSubmit("dataList",url);
		}
	}]

	// 初始化函数
	$(function(){
		$('#dataList').datagrid({   
		    url:'${ctx}/sysadmin/userAction_listResult',    
		    columns:v_columns,
		    toolbar: v_toolbar,
		    pagination : true,
		    fitColumns : true,
		}); 
	})
</script>

</head>

<body>
	<form id="deptForm" name="deptForm" method="post">
	
		<input type="hidden" id="id" name="id" value="id">
		
		<div class="textbox" id="centerTextbox">
			<div class="textbox-header">
				<div class="textbox-inner-header">
					<div class="textbox-title">员工列表</div>
				</div>
			</div>
		<div>
		<div class="eXtremeTable">
			<table id="dataList" class="tableRegion" width="100%"></table>
		</div>
	</form>
</body>
</html>


