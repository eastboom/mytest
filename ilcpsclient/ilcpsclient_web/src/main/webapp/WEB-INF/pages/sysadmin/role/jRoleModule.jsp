<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<!-- 第一步： 引入ztree资源样式 -->
	<link rel="stylesheet" href="${ctx}/components/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/components/zTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${ctx}/components/zTree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx}/components/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>	
	
	<script type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		// 保存树对象
		var treeObj;
		
		// var zNodes =[{ id:1, pId:0, name:"随意勾选 1", open:true}]
	
		// 第三步：在窗体加载事件，发送异步请求返回json数据，初始化ztree组件 
		$(function(){
			$.ajax({
				url : "${ctx}/sysadmin/roleAction_getModulesJson",//请求url
				type : "get",									  //提交方式
				data : {"id":$("#id").val()},					  //请求数据
				dataType : "json",								  //返回数据格式
				success : function(zNodesJson){ // 参数就是ajax返回的json结果
					treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodesJson);
					// 展开所有节点
					treeObj.expandAll(true);
				},
				error : function(){
					alert("异步请求出错....");
				}
			});
		});
		
		// 获取选中的节点并提交表单
		function getCheckNodesSubmit(){
			//1. 获取选中节点  (一个节点就是一个json格式)
			var nodes = treeObj.getCheckedNodes(true);
			
			// 保存所有选中节点ids
			var moduleIds = "";
			
			//2. 遍历
			if (nodes != null && nodes.length > 0) {
				for (var i=0; i<nodes.length; i++) {
					//3. 获取选中节点的id
					moduleIds += nodes[i].id + ","
				}
			}
			//4. 去掉最后逗号，设置到表单隐藏域中
			moduleIds = moduleIds.substring(0,moduleIds.length-1);
			$("#moduleIds").val(moduleIds);
			
			//5. 提交表单
			document.forms[0].action = "${ctx}/sysadmin/roleAction_saveRoleModule";
			document.forms[0].submit();
		}
		
	
	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" id="id" name="id" value="${id}"/>
	<!-- 保存选中的权限id -->
	<input type="hidden" id="moduleIds" name="moduleIds"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="getCheckNodesSubmit()">保存</a></li>
<li id="back"><a href="#" onclick="formSubmit('userAction_list','_self');this.blur();">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
    角色 [${name}] 权限展示
  </div> 
  </div>
  </div>
  
<div>
<div style="text-align:left">  
	<!-- 第二步：显示ztree的区域 -->
	<ul id="treeDemo" class="ztree"></ul>
</div>
</div>
</form>
</body>
</html>

