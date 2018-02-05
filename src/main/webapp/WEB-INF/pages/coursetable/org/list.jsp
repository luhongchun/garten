<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>授课机构列表</title>
</head>
<body>
  <div data-role="page">
    	<div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
		</div>
  	
    <div data-role="main" class="ui-content jqm-content">
    	<form class="ui-filterable" id="addForm">
	 		<input id="myFilter" data-type="search" placeholder="根据名称搜索...">
	 		
	 		<s:url var="addAction" value="/coursetable/org/add.action" />
	 		<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="addBtn">添加</button>
		</form>
    
      <ul data-role="listview" data-inset="true"  data-filter="true" data-input="#myFilter">
        <li>授课机构列表</li>
        <s:iterator value="#request.courseOrgInfos" var="org">
          <s:url var="editActionUrl" value="/coursetable/org/edit.action">
            <s:param name="id" value="#org.id" />
          </s:url>
          <li><a data-ajax="false" href="${editActionUrl}">${empty org.name ? '未填写' : org.name}</a></li>
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
		});
  </script>
</body>
</html>