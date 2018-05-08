function selectRowSubmit(area,url){
	//1. 获取datagrid选中的行
	var selRows = $("#" + area).datagrid("getSelections");
	
	//2. 判断
	if (selRows == null  || selRows.length != 1) {
		$.messager.alert("提示","先选中一行，再点击操作！","warning");
		return;
	}
	var id = selRows[0].id;
	// 设置id到隐藏域
	$("#id").val(id);
	//3. 表单提交
	document.forms[0].action = url;
	document.forms[0].submit();
}

// 日期转换函数
function parseDate(longDate){
	if (longDate) {
		var d = new Date(longDate);
		var y = d.getFullYear();
		var m = d.getMonth() + 1;
		var day = d.getDate();
		return y + "-" + m + "-" + day;
	}
	return null;
}



