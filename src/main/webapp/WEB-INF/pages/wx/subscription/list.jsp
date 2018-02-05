<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>公众号列表</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <li>公众号列表</li>
        <c:forEach items="${subscriptions}" var="subscription" varStatus="status">
          <c:url value="/wx/manage/subscription/edit/${subscription.id}" var="editUrl">
          </c:url>
          <li><a data-ajax="false" href='${editUrl}'>${empty subscription.name ? '未填写' : subscription.name}</a></li>
        </c:forEach>
      </ul>
    </div>
  </div>
</body>
</html>