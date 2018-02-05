<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<%--
  ~ Copyright (c) 2013-2014 Wepu Information Co., Ltd. All rights reserved.
  --%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>账号管理</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <s:url var="addAccountAction" value="/account/account_manage/add_account.action" >
          <s:param name="openId" value="#request.openId" />
      </s:url>
      <s:url var="listSubUserAction" value="/account/account_manage/list_sub_user.action" >
        <s:param name="openId" value="#request.openId" />
      </s:url>
      <ul data-role="listview" data-inset="true">
        <li>账号管理</li>
        <li><a data-ajax="false" href="${addAccountAction}">添加账号</a></li>
        <li><a data-ajax="false" href="${listSubUserAction}">分配资源</a></li>
      </ul>
    </div>
  </div>
</body>
</html>