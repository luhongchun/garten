<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>人脸信息</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:if test="${not empty face}">
        <div>
          <ul data-role="listview" data-inset="true">
            <li>
              <a data-ajax="false" href="#" id="restart">
            	${empty face.faceId? '未知face' : face.faceId}
              </a>
            </li>
			<li>日期<span class="ui-li-count">${empty face.createDate ? '无' : face.createDate}</span></li>
            <li>图片ID<span class="ui-li-count">${empty face.imgId ? '未知' : face.imgId}</span></li>
            <li>Tag<span class="ui-li-count">${empty face.tag ? '无' : face.tag}</span></li>
            <li>Url<span class="ui-li-count">${empty face.url ? '无' : face.url}</span></li>
            <c:url var="delFaceUrl" value="/operate/fr_face_delete/${face.id}/${personId}">
            </c:url>
            <li><a data-ajax="false" href="${delFaceUrl}">删除此人脸</a></li>
          </ul>
        </div>
      </c:if>
      <c:if test="${empty face}">
      	<div><span>没有获取到人脸的信息</span></div>
      </c:if>
    </div>
  </div>
</body>
</html>