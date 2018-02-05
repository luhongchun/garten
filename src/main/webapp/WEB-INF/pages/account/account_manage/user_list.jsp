<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>选择主账户</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <li>用户列表</li>
        <s:iterator value="#request.users" var="user">
          <s:url var="accountSettingAction" value="/account/account_manage/index.action">
            <s:param name="baseUserId" value="#user.id" />
          </s:url>
          <li><a data-ajax="false" href="${accountSettingAction}">${empty user.globalId ? '未填写' : user.globalId}: ${empty user.name ? '未填写' : user.name}</a></li>
        </s:iterator>
      </ul>
    </div>
  </div>
</body>
</html>