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
<li id="save"><a href="#" onclick="formSubmit('packingListAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增装箱单
  </div> 
    <div>
		<div>
		<table class="commonTable" cellspacing="1">
	         <tr>
	            <td class="columnTitle">卖方：</td>
	            <td class="tableContent"><input type="text" name="seller" value=""/></td>
	            <td class="columnTitle">买方：</td>
	            <td class="tableContent"><input type="text" name="buyer" value=""/></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">发票号：</td>
	            <td class="tableContent"><input type="text" name="invoiceNO" value=""/></td>
	            <td class="columnTitle">发票日期：</td>
				 <td class="tableContent">
					<input type="text" style="width:90px;" name="signingDate"
	            	 value=""
	             	onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
				</td>
	           
	        </tr>		
	        <tr>
	            <td class="columnTitle">唛头：</td>
	            <td class="tableContent"><input type="text" name="marks" value=""/></td>
	            <td class="columnTitle">描述：</td>
	            <td class="tableContent"><input type="text" name="descriptions" value=""/></td>
	        </tr>			
	        
	        
		</table>
	</div>


  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
    出口报运单列表
  </div> 


<div class="eXtremeTable" >
<table id="ec_table" class="tableRegion" width="98%" >
	<thead>
	<tr>
		<td width="25" title="可以拖动下面行首,实现记录的位置移动.">
			<img src="${ctx }/images/drag.gif">
		</td>
		<td width="20">
			<input class="input" type="checkbox" name="ck_exportId" onclick="checkGroupBox(this);" />
		</td>
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
		<td></td>
		<td><input class="input" type="checkbox" name="exportId" value="${ep.id }"></td>
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
		<td>已报运未装箱</td>
	</tr>
	</c:forEach>
	
	</tbody>
</table>
</div> 
 
</form>
</body>
</html>

