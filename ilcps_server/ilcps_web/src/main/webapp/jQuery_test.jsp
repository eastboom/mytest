<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
	<title>如何自定义jQuery扩展插件</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>

	
</head>
<body>
	<div id="div">div 区域内容</div>
</body>
	<script type="text/javascript">
		// $ = jQuery  这2个符号是一样的，都表示jQuery对象。
		//alert(  $("#div").html()  );
		//alert(  jQuery("#div").html()  );
		
		//1. 匿名函数定义、调用
		//   匿名函数必须在定义时候就调用，否则永远无法再调用。
		(function(){		// 定义一个匿名函数,无参数
			alert("调用匿名函数");
		})()				// () 调用匿名函数
		
		(function(num){		// 有参匿名函数
			alert("调用匿名函数"+num);
		})(5)
		
		//2. jQuery扩展插件
		// 需求： 自定义一个jQuery扩展方法，showContent, 返回div内容。    
		(function($){
			// 扩展jQuery方法，是通过$.fn.扩展的方法
			$.fn.showContent = function(){
				// this 表示jQuery对象，所以可以直接调用html()方法
				var htmlValue = this.html();
				return htmlValue;
			}
		})(jQuery)
		
		//3. 使用jQuery扩展插件，显示div内容
		var value = $("#div").showContent();
		alert("=====>" + value);
		
	</script>
</html>

