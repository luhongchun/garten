<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>人信息</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:if test="${not empty person}">
        <div>
          <ul data-role="listview" data-inset="true">
            <li>
              <a data-ajax="false" href="#" id="restart">
            	${empty person.personName? '未命名的分组' : person.personName}
              </a>
            </li>
            <li>FR序列号<span class="ui-li-count">${empty person.personId ? '未知' : person.personId}</span></li>
            <li>Tag<span class="ui-li-count">${empty person.tag ? '无' : person.tag}</span></li>
            
            <c:url var="personInfoEditUrl" value="/operate/fr_person_edit/${person.id}">
            </c:url>
            <li><a data-ajax="false" href="${personInfoEditUrl}">编辑人信息</a></li>
            
            <c:url var="personChangeGroupUrl" value="/operate/fr_person_change_group/${person.id}">
            </c:url>
            <li><a data-ajax="false" href="${personChangeGroupUrl}">移动分组</a></li>
            
            <c:url var="faceAddUrl" value="/operate/fr_face_create/${person.id}">
            </c:url>
            <li><a data-ajax="false" href="${faceAddUrl}">添加人脸</a></li>
          </ul>
          <ul data-role="listview" data-inset="true">
          <li>人脸信息</li>
          <c:if test="${not empty faceList}">
            <c:forEach items="${faceList}" var="face" varStatus="status">
              <c:url value="/operate/fr_face_info/${face.id}/${person.id}" var="statusUrl">
              </c:url>
              <li>
              	<a data-ajax="false" href="${statusUrl}">faceId: ${empty face.faceId ? status.count : face.faceId}
              	</a>
              </li>
            </c:forEach>
          </c:if>
        </ul>
        </div>
      </c:if>
      <c:if test="${empty person}">
      	<div><span>没有获取到人的信息</span></div>
      </c:if>
    </div>
  </div>
</body>
</html>