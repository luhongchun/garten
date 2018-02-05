<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>公众号维护工具</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:url var="sendMessageAction" value="/wx/manage/subscription/send_notice_message" />
      <c:url var="updateMenuAction" value="/wx/manage/subscription/update_menu" />
      <c:url var="updateIndividualMenuAction" value="/wx/manage/subscription/manage_individual_menu" />
      <c:url var="userGroupManageAction" value="/wx/manage/subscription/user_group_list" />
      <c:url var="manageMaterialAction" value="/wx/manage/subscription/manage_material" />
      <ul data-role="listview" data-inset="true">
        <li>公众号管理</li>
        <li><a data-ajax="false" href="${sendMessageAction}">公众号群发通知</a></li>
        <li><a data-ajax="false" href="${updateMenuAction}">公众号菜单更新</a></li>
        <li><a data-ajax="false" href="${updateIndividualMenuAction}">公众号个性化菜单管理</a></li>
        <li><a data-ajax="false" href="${userGroupManageAction}">公众号用户组管理</a></li>
        <li><a data-ajax="false" href="${manageMaterialAction}">公众号素材管理</a></li>
      </ul>
    </div>
  </div>
</body>
</html>