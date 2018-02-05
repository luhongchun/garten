<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>视频处理</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <c:url var="snapshotUrl" value="/biz/video/snapshot">
        </c:url>
        <li><a data-ajax="false" href="${snapshotUrl}">视频抽帧</a></li>
      	
      	<c:url var="classifyUrl" value="/biz/video/classify">
        </c:url>
        <li><a data-ajax="false" href="${classifyUrl}">视频归类</a></li>
      	
      	<c:url var="frGroupUrl" value="/biz/video/frGroup">
        </c:url>
        <li><a data-ajax="false" href="${frGroupUrl}">初始化分组</a></li>
      	
      </ul>
    </div>
  </div>
</body>
</html>