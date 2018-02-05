<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>摄像机人脸识别统计</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:if test="${not empty cameraPersonInfos}">
      <ul data-role="listview" data-inset="true">
        <c:forEach items="${cameraPersonInfos}" var="ci" varStatus="status">
        <li>
          <a data-ajax="false">
            摄像机：${cameraName} &nbsp;&nbsp;&nbsp;&nbsp; 拍摄日期 <span class="ui-li-count">
            <fmt:formatDate value="${ci.recordDate}" pattern="M月d日"/></span>
          </a>
        </li>
          <c:forEach items="${ci.cameraPersons}" var="cp" varStatus="status">
            <li>人：${cp.personId }<span class="ui-li-count">视频数：${cp.recordCount}</span></li>
          </c:forEach>
        </c:forEach>
      </ul>
    </c:if>
  </div>
</div>
</body>
</html>
