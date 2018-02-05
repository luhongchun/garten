<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>所有摄像头</title>
</head>
<body>
<div data-role="page">
  <div data-role="content">
    <c:url var="formAction" value="/device/manage/camera_searching.action"/>
    <form method="post" name="form_data" id="form_data" action="${formAction}">
      <input type="hidden" name="TokenName" value="bf7c846b265c4e7d9e99837705016fd6">
      <div>
        <h4>摄像头sn:</h4>
      </div>
      <div>
        <textarea name="cameraSn" id="cameraSn" required="required"></textarea>
      </div>
      <input type="submit" value="搜索" data-role="button">
      <div id="status"></div>
    </form>
  </div>
  <div data-role="main" class="ui-content jqm-content">
    <c:if test="${not empty reportMessageList}">
      <ul data-role="listview" data-inset="true">
        <li>所有摄像头</li>
        <c:forEach items="${reportMessage.reportMessageList}" var="reportMessage" varStatus="status">
          <c:url value="/device/manage/camera_status" var="statusUrl">
            <c:param name="cameraId" value="#reportMessage.cameraId"/>
          </c:url>
          <li><a data-ajax="false"
                 href="${statusUrl}">摄像头${empty reportMessage.name ? status.count : reportMessage.name}<span
              class="ui-li-count">${reportMessage.online?'在线':'离线'}</span></a></li>
        </c:forEach>
      </ul>
    </c:if>
  </div>
</div>
</body>
</html>
