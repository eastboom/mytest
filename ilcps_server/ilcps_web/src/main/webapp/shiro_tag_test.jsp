<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<html>
<head>
</head>
<body>
	<shiro:hasPermission name="部门列表">
		<a href="#">部门列表</a>	<br>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="添加部门">
		<a href="#">添加部门</a>	<br>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="修改部门">
		<a href="#">修改部门</a>	<br>
	</shiro:hasPermission>
	
	<shiro:hasPermission name="部门删除">
		<a href="#">部门删除</a>	<br>
	</shiro:hasPermission>
</body>
</html>
