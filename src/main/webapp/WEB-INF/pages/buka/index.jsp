<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>不卡业务管理</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <ul data-role="listview" data-inset="true">
      <li>不卡业务管理</li>
      <s:url var="appListAction" value="/buka/app/list.action"/>
      <s:url var="appRoomListAction" value="/buka/app/room/list.action"/>
      <li><a data-ajax="false" href="${appListAction}">应用管理</a></li>
      <li><a data-ajax="false" href="${appRoomListAction}">房间管理</a></li>
    </ul>
  </div>
</div>
</body>
</html>