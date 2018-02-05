<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>选择公众号</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <li>公众号列表</li>
        <s:iterator value="#request.officialAccounts" var="officialAccount">
          <s:url var="userListActionUrl" value="/account/account_manage/user_list.action">
            <s:param name="instanceId" value="#officialAccount.instanceId" />
          </s:url>
          <li><a data-ajax="false" href="${userListActionUrl}">${empty officialAccount.name ? '未填写' : officialAccount.name}</a></li>
        </s:iterator>
      </ul>
    </div>
  </div>
</body>
</html>