<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>助理教师列表</title>
</head>
<body>
  <div data-role="page">
  <div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <li>助理教师列表</li>
        <s:iterator value="#request.courseOrgInfos" var="org">
          <s:url var="editActionUrl" value="/coursetable/org/edit.action">
            <s:param name="id" value="#org.id" />
          </s:url>
          <li><a data-ajax="false" href="${editActionUrl}">${empty org.name ? '未填写' : org.name}</a></li>
        </s:iterator>
      </ul>
    </div>
  </div>
</body>
</html>