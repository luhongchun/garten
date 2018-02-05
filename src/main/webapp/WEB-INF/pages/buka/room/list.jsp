<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>布卡房间列表</title>
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
   			<input type="hidden" name="appKey" value="${appInfo.appKey }">
	 		<input id="myFilter" data-type="search" placeholder="根据名称搜索...">
	 		<s:url var="addAction" value="/buka/app/room/createInput.action" />
	 		<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="addBtn">创建房间</button>
	 		<s:url var="appListAction" value="/buka/app/list.action" />
	 		<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="appListBtn">返回应用列表</button>
	 		<s:url var="infoAction" value="/buka/app/room/bukaList.action" />
	 		<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="infoBtn">同步最新房间信息</button>
		</form>
		
		<ul data-role="listview" data-inset="true" data-filter="true" data-input="#myFilter">
	       <li>【${appInfo.appName }】应用房间列表</li>
	        <s:iterator value="#request.infos" var="info">
	        	<s:url var="editActionUrl" value="/buka/app/room/detail.action">
	            	<s:param name="id" value="#info.id" />
	          	</s:url>
	          <li><a data-ajax="false" href="${editActionUrl}">${empty info.name ? '未填写' : info.name}</a></li>
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
			
			$("#appListBtn").click(function() {
	          $("#addForm").attr("action", "${appListAction}");
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