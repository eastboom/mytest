<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<body>
<form name="icform" method="post">
      <input type="hidden" name="id" value="${id}"/>
      
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('deptAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改部门
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">上级部门：</td>
	            <td class="tableContent">
	            	<!-- 设置value默认选中当前部门的父部门 -->
	            	<input class="easyui-combobox" name="parent.id"  style="width:300px" value="${parent.id }"
    					data-options="valueField:'id',textField:'deptName',url:'${ctx}/sysadmin/deptAction_ajaxDept'" />  
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">部门名称：</td>
	            <td class="tableContent"><input type="text" name="deptName" value="${deptName }"/>
	            </td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="columnTitle">
	            	<input type="radio" name="state" value="1" <c:if test="${state==1}">checked="checked"</c:if> > 正常
	            	<input type="radio" name="state" value="0" <c:if test="${state==0}">checked="checked"</c:if>> 暂停
	            </td>
	        </tr>		
		</table>
	</div>
 </form>
</body>
</html>