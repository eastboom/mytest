<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
	<script type="text/javascript">

	</script>
</head>

<body>
<form name="icform" method="post">
	<input type="hidden" name="state" value="0"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('invoiceAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增发票
  </div> 
    <div>
		<div>
		<table class="commonTable" cellspacing="1">
	         <tr>
	            <td class="columnTitle">贸易条款：</td>
	            <td class="tableContent"><input type="text" name="tradeTerms" value="" style="width: 200px"/></td>
	        </tr>		
	        
		</table>
	</div>


  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   委托单列表
  </div> 


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td width="25" title="可以拖动下面行首,实现记录的位置移动.">
			<img src="${ctx }/images/drag.gif">
		</td>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">海运/空运</td>
		<td class="tableHeader">货主</td>
		<td class="tableHeader">信用证</td>
		<td class="tableHeader">装运港</td>
		<td class="tableHeader">装运港</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
<c:forEach items="${ShippingOrderList}" var="shippingOrder" varStatus="status">
	<tr bgcolor="#c3f3c3" height="30" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td></td>
		<td><input class="input" type="radio" name="shippingOrderId" value="${shippingOrder.id}"></td>
		<td>${status.index+1}</td>
		<td>${shippingOrder.id}</td>
		<td>${shippingOrder.shipper}</td>
		<td>${shippingOrder.consignee}</td>
		<td>${shippingOrder.lcNo}</td>
		<td>${shippingOrder.portOfLoading}</td>
		<td>${shippingOrder.portOfTrans}</td>
	</tr>
</c:forEach>
	
	</tbody>
</table>
</div> 
 
</form>
</body>
</html>

