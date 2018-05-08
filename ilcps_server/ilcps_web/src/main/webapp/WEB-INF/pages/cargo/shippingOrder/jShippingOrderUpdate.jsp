<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
</head>

<body>
<form name="icform" method="post">
     <input type="hidden" name="id" value="${id }">
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('shippingOrderAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
   更新委托单
  </div> 
  </div>
  </div>
  
  
   
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">货主：</td>
	            <td class="tableContent"><input type="text" name="shipper" value="${shipper }"/></td>
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="orderType" value="海运" ${orderType=='海运'?"checked":"" } class="input">海运
	            	<input type="radio" name="orderType" value="空运" ${orderType=='空运'?"checked":"" }  class="input">空运
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">提单抬头：</td>
	            <td class="tableContent"><input type="text" name="consignee" value="${consignee }"/></td>
	            <td class="columnTitle">正本通知人：</td>
	            <td class="tableContent"><input type="text" name="notifyParty" value="${notifyParty }"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">信用证：</td>
	            <td class="tableContent"><input type="text" name="lcNo" value="${lcNo }"/></td>
	            <td class="columnTitle">装运港：</td>
	            <td class="tableContent"><input type="text" name="portOfLoading" value="${portOfLoading }"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">转船港：</td>
	            <td class="tableContent"><input type="text" name="portOfTrans" value="${portOfTrans }"/></td>
	            <td class="columnTitle">卸货港：</td>
	            <td class="tableContent"><input type="text" name="portOfDischarge" value="${portOfDischarge }"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">装期：</td>
	             <td class="tableContent">
					<input type="text" style="width:90px;" name="loadingDate"
	            	 value="<fmt:formatDate value='${loadingDate }' pattern='yyyy-MM-dd' />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	          
	            <td class="columnTitle">效期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="limitDate"
	            	 value="<fmt:formatDate value='${limitDate }' pattern='yyyy-MM-dd' />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否分批：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isBatch" value="1" ${isBatch=='1'?"checked":"" }  class="input">是
	            	<input type="radio" name="isBatch" value="0" ${isBatch=='0'?"checked":"" } class="input">否
	            </td>
	            <td class="columnTitle">是否转船：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isTrans" value="1" ${isTrans=='1'?"checked":"" } class="input">是
	            	<input type="radio" name="isTrans" value="0" ${isTrans=='0'?"checked":"" } class="input">否
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">份数：</td>
	            <td class="tableContent">
	            	<input type="text" name="copyNum" value="${copyNum }" />
	            </td>
	            <td class="columnTitle">制单人：</td>
	            <td class="tableContent">
	            	<input type="text" name="createBy" value="${createBy}" />
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">复核人：</td>
	            <td class="tableContent">
	            	<input type="text" name="checkBy" value="${checkBy }" />
	            </td>
	               <td class="columnTitle">扼要说明：</td>
	            <td class="tableContent"><textarea name="remark" style="height:50px;">${remark }</textarea>
	        </tr>
	       	
	       	
	    		
	        <tr>
	            <td class="columnTitle">运输要求：</td>
	            <td class="tableContent"><textarea name="specialCondition" style="height:50px;">${specialCondition }</textarea>
	            <td class="columnTitle">运费说明：</td>
	            <td class="tableContent"><textarea name="freight" style="height:50px;">${freight }</textarea>
	        </tr>		
		</table>
	</div>
 </div>
 

 <%--
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">客户名称：</td>
	            <td class="tableContent"><input type="text" name="customName" value="${customName }"/></td>
	            <td class="columnTitle">打印版式：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="printStyle" value="2" ${printStyle=='2'?"checked":"" }  class="input">两款
	            	<input type="radio" name="printStyle" value="1" ${printStyle=='1'?"checked":"" } class="input">一款
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">合同号：</td>
	            <td class="tableContent"><input type="text" name="contractNo" value="${contractNo }"/></td>
	            <td class="columnTitle">收购方：</td>
	            <td class="tableContent"><input type="text" name="offer" value="${offer }"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">制单人：</td>
	            <td class="tableContent"><input type="text" name="inputBy" value="${inputBy }"/></td>
	            <td class="columnTitle">审单人：</td>
	            <td class="tableContent"><input type="text" name="checkBy" value="${checkBy }"/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">验货员：</td>
	            <td class="tableContent"><input type="text" name="inspector" value="${inspector }"/></td>
	            <td class="columnTitle">签单日期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="signingDate"
	            	 value="<fmt:formatDate value='${signingDate }' pattern='yyyy-MM-dd' />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">重要程度：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="importNum" value="3" ${importNum==3?"checked":"" } class="input">★★★
	            	<input type="radio" name="importNum" value="2" ${importNum==2?"checked":"" } class="input">★★
	            	<input type="radio" name="importNum" value="1" ${importNum==1?"checked":"" } class="input">★
	            </td>
	            <td class="columnTitle">船期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="shipTime"
	            	 value="<fmt:formatDate value='${shipTime }' pattern='yyyy-MM-dd' />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">贸易条款：</td>
	            <td class="tableContent"><input type="text" name="tradeTerms" value="${tradeTerms }"/></td>
	            <td class="columnTitle">交货期限：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="deliveryPeriod"
	            	 value="<fmt:formatDate value='${deliveryPeriod }' pattern='yyyy-MM-dd' />"
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">要求：</td>
	            <td class="tableContent"><textarea name="crequest" style="height:150px;">${crequest }</textarea>
	            <td class="columnTitle">说明：</td>
	            <td class="tableContent"><textarea name="remark" style="height:150px;">${remark }</textarea>
	        </tr>		
		</table>
	</div>
  --%>
 
</form>
</body>
</html>

