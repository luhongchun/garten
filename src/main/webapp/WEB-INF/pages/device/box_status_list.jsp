<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>所有盒子</title>
</head>
<body>
<div data-role="page">
  <div data-role="content">
    <c:url var="formAction" value="/device/manage/box_searching"/>
    <form method="post" name="form_data" id="form_data" action="${formAction}">
      <input type="hidden" name="TokenName" value="bf7c846b265c4e7d9e99837705016fd6">
      <div>
        <h4>盒子sn:</h4>
      </div>
      <div>
        <textarea name="boxSn" id="boxSn" required="required"></textarea>
      </div>
      <input type="submit" value="搜索" data-role="button">
      <div id="status"></div>
    </form>
  </div>
  <div data-role="main" class="ui-content jqm-content">
    <c:if test="${not empty reportMessageList}">
      <ul data-role="listview" data-inset="true">
        <li>所有盒子</li>
        <c:forEach items="${reportMessageList}" var="reportMessage" varStatus="status">
          <c:url value="/device/manage/box_status/${reportMessage.boxMessage.boxId}" var="statusUrl">
          </c:url>
          <li>
            <a data-ajax="false" href="${statusUrl}">
              盒子--${empty reportMessage.boxMessage.name ? status.count : reportMessage.boxMessage.name}
            </a>
            <c:choose>
              <c:when test="${reportMessage.boxMessage.online eq true}"><span class="ui-li-count">在线</span></c:when>
              <c:otherwise><span class="ui-li-count" style="color:red">离线</span></c:otherwise>
            </c:choose>
          </li>
        </c:forEach>
      </ul>
    </c:if>
  </div>
</div>
</body>
</html>
