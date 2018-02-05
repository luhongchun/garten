<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>视频下载</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:url var="videoDownloadP" value="/video/manage/downloadByPerson"/>
      <c:url var="videoDownloadT" value="/video/manage/downloadByType"/>
      <ul data-role="listview" data-inset="true">
        <li><a data-ajax="false" href="${videoDownloadP }">按人下载</a></li>
        <li><a data-ajax="false" href="${videoDownloadT }">按类型下载</a></li>
      </ul>
    </div>
  </div>
</body>
</html>