<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   浏览装箱单
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	         <tr>
	            <td class="columnTitle">卖方：</td>
	            <td class="tableContent">${seller }</td>
	            <td class="columnTitle">买方：</td>
	            <td class="tableContentAuto">
	            	${buyer} 
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">发票号：</td>
	            <td class="tableContent">${invoiceNO }</td>
	            <td class="columnTitle">发票日期：</td>
	            <td class="tableContent">
					<fmt:formatDate value='${invoiceDate }' pattern='yyyy-MM-dd' />
				</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">唛头：</td>
	            <td class="tableContent">${marks }</td>
	            <td class="columnTitle">描述：</td>
	            <td class="tableContent">${descriptions }</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent">${state }</td>
	            <td class="columnTitle">签单日期：</td>
	            <td class="tableContent">
					<fmt:formatDate value='${signingDate }' pattern='yyyy-MM-dd' />
				</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">创建人：</td>
	            <td class="tableContent">${createBy }</td>
	            <td class="columnTitle">创建部门：</td>
	            <td class="tableContent">${createDept }</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">创建日期：</td>
	            <td class="tableContent">
					<fmt:formatDate value='${createTime }' pattern='yyyy-MM-dd' />
				</td>
	           
	        </tr>		
	        
		</table>
	</div>
 </form>
 
 <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    报运单列表
  </div> 


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td class="tableHeader">序号</td>
		<td class="tableHeader">报运号</td>
		<td class="tableHeader">商品数/附件数</td>
		<td class="tableHeader">信用证号</td>
		<td class="tableHeader">收货人及地址</td>
		<td class="tableHeader">装运港</td>
		<td class="tableHeader">目的港</td>
		<td class="tableHeader">运输方式</td>
		<td class="tableHeader">价格条件</td>
		<td class="tableHeader">制单日期</td>
		<td class="tableHeader">状态</td>
	</tr>
	</thead>
	<tbody class="tableBody" >
	
	<c:forEach items="${exports}" var="ep" varStatus="status">
	<tr bgcolor="#c3f3c3" height="30" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
		<td>${status.index+1}</td>
		<td>${ep.id}</td>
		<td>${ep.productNum}/${ep.extcNum}</td>
		<td>${ep.lcno}</td>
		<td>${ep.consignee}</td>
		<td>${ep.shipmentPort}</td>
		<td>${ep.destinationPort}</td>
		<td>${ep.transportMode}</td>
		<td>${ep.priceCondition}</td>
		<td>${ep.inputDate}</td>
		<td>已装箱</td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div> 
</body>
</html>