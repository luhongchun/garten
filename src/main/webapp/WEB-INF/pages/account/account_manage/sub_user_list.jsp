<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>用户信息</title>
</head>
<body>
    <c:set var="defaultUserIconUrl"
        value="http://static-10001988.file.myqcloud.com/resource/images/common/default_user_icon.png" />

    <div data-role="page">
        <ul data-role="listview">
            <li><b>我的账户ID：</b>${request.baseUser.globalId}</li>
            <li><b>已购账户：</b>总计${request.accountQuota}，剩余${request.accountBalance}</li>
        </ul>
        <ul data-role="listview">
            <li>已添加的用户</li>
            <s:iterator value="#request.createdAccounts" id="createdAccount" status="status">
                <s:url value="/account/account_manage/account_setting.action" var="setAccountAction">
                    <s:param name="openId" value="#request.openId" />
                    <s:param name="toAccountId" value="#createdAccount.id" />
                </s:url>
                <li><a href="${setAccountAction}" data-ajax="false"> <img src='${defaultUserIconUrl}'>
                        <h2>${createdAccount.aliasName}</h2>
                        <p>已分配：${createdAccount.accountCreationQuota}账户，${fn:length(createdAccount.accountCameras)}摄像头</p>
                </a></li>
            </s:iterator>
        </ul>
    </div>
</body>
</html>