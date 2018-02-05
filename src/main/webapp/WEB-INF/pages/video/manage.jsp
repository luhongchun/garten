<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>视频管理</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:url var="requestVideoReview" value="/biz/video/closeup/request_video_review"/>
    <c:url var="requestVideoIndex" value="/biz/video/index"/>

    <ul data-role="listview" data-inset="true">
      <li><a data-ajax="false" href="${requestVideoIndex}">视频处理</a></li>
      <li><a data-ajax="false" href="${requestVideoReview }">视频审核推送</a></li>
    </ul>
  </div>
</div>
</body>
</html>