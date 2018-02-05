<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>布卡应用列表</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
    	<c:if test="${not empty request.msg}">
    		<script type="text/javascript">
    			alert('${request.msg}');
    		</script>
    	</c:if>
   		<form class="ui-filterable" id="addForm">
	 		<input id="myFilter" data-type="search" placeholder="根据名称搜索...">
	 		<s:url var="addAction" value="/buka/app/createInput.action" />
	 		<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="addBtn">创建应用</button>
	 		
	 		<s:url var="infoAction" value="/buka/app/info.action" />
	 		<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="infoBtn">同步最新应用信息</button>
		</form>
	    <ul data-role="listview" data-inset="true" data-filter="true" data-input="#myFilter">
	       <li>布卡应用列表</li>
	        <s:iterator value="#request.infos" var="info">
	        	<s:url var="editActionUrl" value="/buka/app/detail.action">
	            	<s:param name="id" value="#info.id" />
	          	</s:url>
	          <li><a data-ajax="false" href="${editActionUrl}">${empty info.appName ? '未填写' : info.appName}</a></li>
	       </s:iterator>
	    </ul>
    </div>
  </div>
  
  <script type="text/javascript">
		$(function() {
			$("#addBtn").click(function() {
	          $("#addForm").attr("action", "${addAction}");
	          $("#addForm").submit();
	        });
			
			$("#infoBtn").click(function() {
	          $("#addForm").attr("action", "${infoAction}");
	          $("#addForm").submit();
		    });
		});
	</script>
</body>
</html>