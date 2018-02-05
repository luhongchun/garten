<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>智启科技-运维管理</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:url var="deviceStatus" value="/device/manage/device_status_index"/>
    <c:url var="videoManage" value="/biz/video/manage/"/>
    <c:url var="faceManage" value="/operate/fr_group_list.action"/>
    <c:url var="gartenManage" value="/garten/manage/garten/index/${pageContext.request.getAttribute(\"managerId\")}"/>
    <c:url var="subscriptionManageIndexAction" value="/wx/manage/subscription/index"/>
    <c:url var="courseManage" value="/coursetable/index.action"/>
    <c:url var="bukaManage" value="/buka/app/list.action"/>
    <c:url var="qrcodeManage" value="/app/application/index.action"/>
    <c:url var="sysUserManage" value="/app/sys_user_register.jsp"/>
    <c:url var="accountManage" value="/account/account_manage/subscription_list.action"/>
    <c:url var="userRegister" value="/user/regist_manual_page.action"/>
    <c:url var="version" value="/version"/>
    <ul data-role="listview" data-inset="true">
      <li>管理首页</li>
      <li><a data-ajax="false" href="${deviceStatus }">设备状态管理</a></li>
      <li><a data-ajax="false" href="${videoManage }">视频管理</a></li>
      <li><a data-ajax="false" href="${faceManage}">人脸管理</a></li>
      <li><a data-ajax="false" href="${gartenManage}">幼儿园管理</a></li>
      <li><a data-ajax="false" href="${subscriptionManageIndexAction}">公众号管理</a></li>
      <!--li><a data-ajax="false" href="${courseManage}">排课管理</a></li>
      <li><a data-ajax="false" href="${bukaManage}">布卡业务管理</a></li>
      <li><a data-ajax="false" href="${qrcodeManage }">二维码管理</a></li>
      <li><a data-ajax="false" href="${sysUserManage}">添加运维系统管理员</a></li>
      <li><a data-ajax="false" href="${accountManage}">账号管理</a></li>
      <li><a data-ajax="false" href="${userRegister }">用户后台注册</a></li-->
      <li><a data-ajax="false" href="${version}">版本信息</a></li>
    </ul>
  </div>
</div>
</body>
</html>