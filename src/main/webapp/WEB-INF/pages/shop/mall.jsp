<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>萌宝商城</title>
<link rel="stylesheet" href="https://weui.io/example.css" />
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css" />
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css" />
</head>
<body ontouchstart style="background:#EFEEF3;">
<div>
<img style="margin:0 auto; width:100%;" src="http://static-10001988.cossh.myqcloud.com/resource/images/market/index.jpeg" >
</div>

<div style="margin-top:20px;">
<table style="width:100%">
<c:forEach items="${goodsList }" var="goods" varStatus="status">
  <tr>
  <th style="witdh=50%">
  <a href="${goods.link }">
  <img style="width:96%" src="${goods.coverUrl }"/>
  </a>
  </th>
  </tr>
</c:forEach>
</table>
</div>

<div class="weui_extra_area" style="position:static; bottom:0; margin-bottom:10px; maring-top:20px;">
<p style="font-size:12px; color:#EFEEF3;">---------------------------</p>

<img style="width:120px;" src="http://static-10001988.cossh.myqcloud.com/resource/images/market/logo.png"/>
<p style="font-size:12px; color:#929292;">北京智启科技有限公司</p>
<p style="font-size:12px; color:#EFEEF3;">北京智启科技有限公司</p>
</div>
</body>

<script type="text/javascript">
</script>
</html>