<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<script>
	//jQuery扩展插件： form表单数据转成json
	(function($){  
	    $.fn.serializeJson=function(){  
	        var serializeObj={};  
	        var array=this.serializeArray();  
	        var str=this.serialize();  
	        $(array).each(function(){  
	            if(serializeObj[this.name]){  
	                if($.isArray(serializeObj[this.name])){  
	                    serializeObj[this.name].push(this.value);  
	                }else{  
	                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
	                }  
	            }else{  
	                serializeObj[this.name]=this.value;   
	            }  
	        });  
	        return serializeObj;  
	    };  
	})(jQuery); 


	// 初始化函数
	$(function(){
		$('#dataList').datagrid({   
			//pageList:[2,3,10],
			//pageSize : 2,   每页显示多少条，必须是pageList中的值
			
			// 异步请求后台
		    url:'${ctx}/sysadmin/deptAction_listResult',    
		    // datagrid显示的列标题对用的json格式的key
		    columns:[[    
		        {field:'chk',title:'',checkbox:true},    
		        {field:'id',title:'部门编号',width:100},    
		        {field:'deptName',title:'部门名称',width:100},
		        {field:'parent_',title:'父部门',width:100,
		        	// value：字段值。row：行记录数据。index: 行索引。 
		        	formatter: function(value,row,index){
		        		if (row.parent){
		        			return row.parent.deptName;
		        		}
		        	}

		        },
		        {field:'state',title:'状态',width:100,
		        	formatter: function(value,row,index){
		        		if (value == 1) {
		        			return "正常";
		        		}else{
		        			return "暂停";
		        		}
		        	}
		        }
		    ]],
		    // 工具栏
		    toolbar: [{
				iconCls: 'icon-add',
				text : "新增",
				handler: function(){
					// 提交后台，通过后台转发到jDeptCreate.jsp
					location.href = "${ctx}/sysadmin/deptAction_tocreate";
				}
			},'-',{
				iconCls: 'icon-edit',
				text : "修改",
				handler: function(){
					//1. 获取datagrid选中的行
					var selRows = $("#dataList").datagrid("getSelections");
					
					//2. 判断
					if (selRows == null  || selRows.length > 1) {
						$.messager.alert("提示","先选中一行，再点击修改！","warning");
						return;
					}
					var id = selRows[0].id;
					//3. 提交后台（id）
					location.href = "${ctx}/sysadmin/deptAction_toupdate?id=" + id;
				}
			},{
				iconCls: 'icon-remove',
				text : "删除",
				handler: function(){
					//1. 删除前判断
					var selRows = $("#dataList").datagrid("getSelections");
					if (selRows == null || selRows.length == 0) {
						$.messager.alert("提示","先选中至少一行，再点击删除！","warning");
						return;
					}
					
					// 保存选中记录的id，用逗号隔开
					var deptIds = "";
					for (var i=0; i<selRows.length; i++) {
						deptIds += selRows[i].id + ",";
					}
					// 去掉最后一个逗号
					deptIds = deptIds.substring(0,deptIds.length-1);
					
					//2. 删除操作
					// ajax异步请求后台
					$.ajax({
						url : "${ctx}/sysadmin/deptAction_delete",   // 请求后台地址
						data : {"id" : deptIds},				     // 请求提交数据
						type : "get",								 // 请求方式
						dataType : "json",							 // 返回数据格式
						success : function(jsn){				     // 操作成功回调函数
							
							var msg = "成功：" + jsn.success + ",失败：" + jsn.fail;
							$.messager.alert("提示",msg,"warning");
							// 重新加载datagrid
							$('#dataList').datagrid("reload");
						},
						
						error : function(){							 // ajax请求过程出现未知异常
							alert("ajax请求过程出现未知异常");
						}
					})
				}
			}],
		    // 分页条
		    pagination : true,
		    // 平均列宽
		    fitColumns : true,
		    //singleSelect:true
		}); 
		
		
		// 点击搜索，
		 $('#searchBtn').linkbutton({
			 onClick:function(){  // 绑定点击事件
				
				 // 方式1： 手动拼接json格式；   拼接表单中的搜索条件，封装json格式
				 /*
				 var condition = {
					 "deptName" : $("#deptName").val(),
					 "state" : $("input[name='state']:checked").val()
			 	 };
			 	*/
			 	
			 	// 方式2： 通过自定义的jQuery插件，自动把表单中的数据转换为json格式。
			 	var condition = $("#deptForm").serializeJson();
				 
				 // 重新加载datagrid
				 $("#dataList").datagrid("load",condition)
			 }
		 });  
	})
</script>
</head>

<body>
	<form id="deptForm" name="deptForm" method="post">
		
		<div class="textbox" id="centerTextbox">
			<div class="textbox-header">
				<div class="textbox-inner-header">
					<div class="textbox-title">部门列表</div>
				</div>
			</div>
		<div>
		
		<div class="textbox">
			<table>
				<tr>
					<td style="width:50px" >部门名称</td>
					<td style="width:250px" >
						<input type="text" id="deptName" name="deptName" class="easyui-textbox">
					</td>
					<td style="width:50px" >部门状态</td>
					<td style="width:250px" align="left">
						<input type="radio" name="state" value="1"> 正常
						<input type="radio" name="state" value="0"> 暂停
					</td>
					<td style="width: 50" align="right">
						<a id="searchBtn" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>  
					</td>
				</tr>
			</table>
		</div>
		
		<div class="eXtremeTable">
			<!-- 显示datagrid -->
			<table id="dataList"></table>
		</div>
	</form>
</body>
</html>


