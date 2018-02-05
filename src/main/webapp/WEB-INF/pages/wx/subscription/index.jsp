<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>公众号管理</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:url var="addSubscriptionAction" value="/wx/manage/subscription/add" />
      <c:url var="listSubscriptionAction" value="/wx/manage/subscription/list" />
      <c:url var="manageSubscriptionAction" value="/wx/manage/subscription/tools" />
      <ul data-role="listview" data-inset="true">
        <li>公众号管理</li>
        <li><a data-ajax="false" href="${addSubscriptionAction}">公众号注册</a></li>
        <li><a data-ajax="false" href="${listSubscriptionAction}">公众号列表</a></li>
        <li><a data-ajax="false" href="${manageSubscriptionAction}">公众号维护工具</a></li>
      </ul>
    </div>
  </div>
</body>
</html>