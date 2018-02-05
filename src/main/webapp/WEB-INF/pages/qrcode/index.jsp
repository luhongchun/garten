<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>二维码管理</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <s:url var="addQrcodeAction" value="/app/application/qrcode_add.action" />
      <s:url var="listQrcodeAction" value="/app/application/qrcode_list.action" />
      <s:url var="addQrcodeTypeAction" value="/app/application/qrcode_type_add.action" />
      <s:url var="listQrcodeTypeAction" value="/app/application/qrcode_type_list.action" />
      <ul data-role="listview" data-inset="true">
        <li>二维码管理</li>
        <li><a data-ajax="false" href="${addQrcodeAction}">二维码信息添加</a></li>
        <li><a data-ajax="false" href="${listQrcodeAction}">二维码信息管理</a></li>
        <li><a data-ajax="false" href="${addQrcodeTypeAction}">二维码类型添加</a></li>
        <li><a data-ajax="false" href="${listQrcodeTypeAction}">二维码类型管理</a></li>
      </ul>
    </div>
  </div>
</body>
</html>