<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>管理首页</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:url var="subscriptionManageIndexAction" value="/wx/manage/subscription/index"/>
    <c:url var="sysUserManage" value="/app/user_register.jsp"/>
    <c:url var="deviceStatus" value="/device/device_status_index.action"/>
    <c:url var="gartenManage" value="/garten/garten_manage/index.action"/>
    <c:url var="gartenClassManage" value="/garten/garten_class_manage/index.action"/>
    <c:url var="accountManage" value="/account/account_manage/subscription_list.action"/>
    <c:url var="courseManage" value="/coursetable/index.action"/>
    <c:url var="bukaManage" value="/buka/app/list.action"/>
    <ul data-role="listview" data-inset="true">
      <li>管理首页</li>
      <li><a data-ajax="false" href="${deviceStatus }">设备状态管理</a></li>
      <li><a data-ajax="false" href="${subscriptionManageIndexAction}">公众号管理</a></li>
      <li><a data-ajax="false" href="${sysUserManage}">添加运维系统管理员</a></li>
      <li><a data-ajax="false" href="${gartenManage}">幼儿园管理</a></li>
      <li><a data-ajax="false" href="${gartenClassManage}">幼儿园班级管理</a></li>
      <li><a data-ajax="false" href="${accountManage}">账号管理</a></li>
      <li><a data-ajax="false" href="${courseManage}">排课管理</a></li>
      <li><a data-ajax="false" href="${bukaManage}">布卡业务管理</a></li>
      <li><a data-ajax="false" href="${ctx }/app/application/index.action">二维码管理</a></li>
      <li><a data-ajax="false " href="${ctx }/user/regist_manual_page.action">用户后台注册</a></li>
      <li><a data-ajax="false" href="${ctx }/app/version.jsp">版本信息</a></li>
    </ul>
  </div>
</div>
</body>
</html>