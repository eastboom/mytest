<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js""></script>
</head>

<body>
<form name="icform" method="post">
	<!-- 委托单状态，默认为0 -->
	<input type="hidden" name="state" value="0">
	
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('shippingOrderAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
     <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
   新增委托单
  </div> 
  </div>
  </div>
  
<div class="textbox" id="centerTextbox">


 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">货主：</td>
	            <td class="tableContent"><input type="text" name="shipper" value=""/></td>
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="orderType" value="海运" checked class="input">海运
	            	<input type="radio" name="orderType" value="空运" class="input">空运
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">提单抬头：</td>
	            <td class="tableContent"><input type="text" name="consignee" value=""/></td>
	            <td class="columnTitle">正本通知人：</td>
	            <td class="tableContent"><input type="text" name="notifyParty" value=""/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">信用证：</td>
	            <td class="tableContent"><input type="text" name="lcNo" value=""/></td>
	            <td class="columnTitle">装运港：</td>
	            <td class="tableContent"><input type="text" name="portOfLoading" value=""/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">转船港：</td>
	            <td class="tableContent"><input type="text" name="portOfTrans" value=""/></td>
	            <td class="columnTitle">卸货港：</td>
	            <td class="tableContent"><input type="text" name="portOfDischarge" value=""/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">装期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="loadingDate"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	            <td class="columnTitle">效期：</td>
	            <td class="tableContent">
					<input type="text" style="width:90px;" name="limitDate"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">是否分批：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isBatch" value="1" checked class="input">是
	            	<input type="radio" name="isBatch" value="0" class="input">否
	            </td>
	            <td class="columnTitle">是否转船：</td>
	            <td class="tableContentAuto">
	            	<input type="radio" name="isTrans" value="1" checked class="input">是
	            	<input type="radio" name="isTrans" value="0" class="input">否
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">份数：</td>
	            <td class="tableContent">
	            	<input type="text" name="copyNum" value="" />
	            </td>
	            <td class="columnTitle">制单人：</td>
	            <td class="tableContent">
	            	<input type="text" name="createBy" value="${_CURRENT_USER.userName }" readonly="readonly"/>
	            </td>
	        </tr>
	         <tr>
	            <td class="columnTitle">复核人：</td>
	            <td class="tableContent">
	            	<input type="text" name="checkBy" value="" />
	            </td>
	               <td class="columnTitle">扼要说明：</td>
	            <td class="tableContent"><textarea name="remark" style="height:50px;"></textarea>
	        </tr>
	       	
	       	
	    		
	        <tr>
	            <td class="columnTitle">运输要求：</td>
	            <td class="tableContent"><textarea name="specialCondition" style="height:50px;"></textarea>
	            <td class="columnTitle">运费说明：</td>
	            <td class="tableContent"><textarea name="freight" style="height:50px;"></textarea>
	        </tr>		
		</table>
	</div>
 </div>
 
 <div class="textbox" id="centerTextbox">
	  <div class="textbox-header">
	  <div class="textbox-inner-header">
	  <div class="textbox-title">
	   装箱单列表
	  </div> 
	  </div>
	  </div>
	  
	  
    <div class="eXtremeTable" >
	<table id="ec_table" class="tableRegion" width="98%" >
		<thead>
		<tr>
			<td width="25" title="可以拖动下面行首,实现记录的位置移动.">
				<img src="${ctx }/images/drag.gif">
			</td>
			<td width="20">
				<input class="input" type="radio" name="ck_exportId" onclick="checkGroupBox(this);" />
			</td>
			<td class="tableHeader">序号</td>
			<td class="tableHeader">装箱单编号</td>
			<td class="tableHeader">卖方</td>
			<td class="tableHeader">买方</td>
			<td class="tableHeader">发票号</td>
			<td class="tableHeader">发票日期</td>
			<td class="tableHeader">唛头</td>
			<td class="tableHeader">描述</td>
			<td class="tableHeader">创建日期</td>
			<td class="tableHeader">创建人</td>
			<td class="tableHeader">状态</td>
		</tr>
		</thead>
		<tbody class="tableBody" >
	<c:forEach items="${PackingList}" var="pl" varStatus="status">
		<tr bgcolor="#c3f3c3" height="30" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'" >
			<td></td>
			<td><input class="input" type="radio" name="id" value="${pl.id }"></td>
			<td>${status.index+1}</td>
			<td>${pl.id}</td>
			<td>${pl.seller}</td>
			<td>${pl.buyer}</td>
			<td>${pl.invoiceNo}</td>
			<td>${pl.invoiceDate}</td>
			<td>${pl.marks}</td>
			<td>${pl.descriptions}</td>
			<td>${pl.createTime}</td>
			<td>${pl.createBy}</td>
			<td>${pl.state=='1'?"待处理":"" }</td>
			
		</tr>
		</c:forEach>
	
	</tbody>
</table>
</div> 
 
 
 
 
            
 
 </div>
</form>
</body>
</html>

