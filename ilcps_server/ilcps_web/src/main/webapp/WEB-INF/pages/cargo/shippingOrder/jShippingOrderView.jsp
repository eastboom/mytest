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
   浏览委托单
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	         <tr>
	            <td class="columnTitle">货主：</td>
	            <td class="tableContent">${shipper }</td>
	            <td class="columnTitle">运输方式：</td>
	            <td class="tableContent">${orderType }</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">提单抬头：</td>
	            <td class="tableContent">${consignee }</td>
	            <td class="columnTitle">正本通知人：</td>
	            <td class="tableContent">${notifyParty }</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">信用证：</td>
	            <td class="tableContent">${lcNo }</td>
	            <td class="columnTitle">装运港：</td>
	            <td class="tableContent">${portOfLoading }</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">转运港：</td>
	            <td class="tableContent">${portOfTrans }</td>
	            <td class="columnTitle">卸货港：</td>
	            <td class="tableContent">${portOfDischarge }</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">装期：</td>
	            <td class="tableContent">
					<fmt:formatDate value='${loadingDate }' pattern='yyyy-MM-dd' />
				</td>
	            <td class="columnTitle">效期：</td>
	            <td class="tableContent">
					<fmt:formatDate value='${limitDate }' pattern='yyyy-MM-dd' />
				</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">是否分批：</td>
	            <td class="tableContent">${isBatch }</td>
	            <td class="columnTitle">是否转船：</td>
	            <td class="tableContent">${isTrans }</td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">份数：</td>
	            <td class="tableContent">${copyNum }</td>
	            <td class="columnTitle">扼要说明：</td>
	            <td class="tableContent"><pre>${remark }</pre></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">运输要求：</td>
	            <td class="tableContent"><pre>${specialCondition }</pre></td>
	            <td class="columnTitle">运费说明：</td>
	            <td class="tableContent"><pre>${freight }</pre></td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">复核人：</td>
	            <td class="tableContent">${checkBy }</td>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContentAuto">
	            	${state=='1'?"已提交":"草稿" } 
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">创建人：</td>
	            <td class="tableContent">
	            	${createBy }
	            </td>
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
 



</body>
</html>