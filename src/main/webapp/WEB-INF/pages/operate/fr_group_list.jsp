<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>FR分组</title>
</head>
<body>
  <div data-role="page">
    <div data-role="content">
      <c:url var="formAction" value="/operate/group_search"/>
      <form method="post" name="form_data" id="form_data" action="${formAction}">
        <input type="hidden" name="TokenName" value="bf7c846b265c4e7d9e99837705016fd6">
        <div>
          <h4>组名:</h4>
        </div>
        <div>
          <textarea name="groupName"  id="groupName" required="required"></textarea>
        </div>
        <input type="submit" value="搜索" data-role="button">
        <div id="status"></div>
      </form>
    </div>
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <c:url var="groupCreateUrl" value="/operate/fr_group_create/${group.id}">
        </c:url>
        <li style="display:none">
          <a data-ajax="false" href="${groupCreateUrl}">新建空分组</a>
        </li>
      	
      	<c:url var="groupCreateWithDataUrl" value="/operate/fr_group_create_with_persons/${group.id}">
        </c:url>
        <li><a data-ajax="false" href="${groupCreateWithDataUrl}">基于业务数据新建分组</a></li>
      	
      	<c:url value="/operate/fr_group_sync" var="syncUrl">
      	</c:url>
      	<li>
      	  <a data-ajax="false" href="${syncUrl}">点击同步全部分组信息</a>
      	</li>
      </ul>
      <c:if test="${not empty groupList}">
        <ul data-role="listview" data-inset="true">
          <li>所有组</li>
          <c:forEach items="${groupList}" var="group" varStatus="status">
            <c:url value="/operate/fr_group_info/${group.id}" var="statusUrl">
            </c:url>
            <li>
            	<a data-ajax="false" href="${statusUrl}">
            		组名或组ID： ${empty group.groupName ? group.groupId : group.groupName}
            	</a>
            </li>
          </c:forEach>
        </ul>
      </c:if>
    </div>
  </div>
</body>
</html>