<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>服务器命令执行情况</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:if test="${not empty reportMessage}">
      <ul data-role="listview" data-inset="true">
        <li>服务器命令执行情况<span class="ui-li-count"><fmt:formatDate
            value="${reportMessage.boxMessage.reportTime}" pattern="M月d日 HH:mm:ss"/></span></li>
        <c:if test="${not empty reportMessage.commandMessageList}">
          <s:iterator value="#request.reportMessage.commandMessageList" id="commandMessage" status="status">
            <li>${commandMessage.cmdName}<span
                class="ui-li-count">收到${commandMessage.cntSumPush}/执行${commandMessage.cntSumSuc}</span></li>
          </s:iterator>
        </c:if>
      </ul>
    </c:if>
  </div>
</div>
</body>
</html>