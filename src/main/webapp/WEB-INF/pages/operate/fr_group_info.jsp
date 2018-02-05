<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>分组信息</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:if test="${not empty group}">
        <div>
          <ul data-role="listview" data-inset="true">
            <li>
              <a data-ajax="false" href="#" id="restart">
            	${empty group.groupName? '未命名的分组' : group.groupName}
              </a>
            </li>
            <li>序列号<span class="ui-li-count">${empty group.groupId ? '未知' : group.groupId}</span></li>
            <li>Tag<span class="ui-li-count">${empty group.tag ? '无' : group.tag}</span></li>
     	 	<c:url value="/operate/fr_group_sync/${group.id}" var="syncUrl">
    	  	</c:url>
     	 	<li>
     	 	  <a data-ajax="false" href="${syncUrl}">点击同步本组信息</a>
      		</li>
            <c:url var="groupInfoEditUrl" value="/operate/fr_group_edit/${group.id}">
            </c:url>
            <li><a data-ajax="false" href="${groupInfoEditUrl}">编辑分组信息</a></li>
            <c:url var="personAddUrl" value="/operate/fr_person_create/${group.id}">
            </c:url>
            <li><a data-ajax="false" href="${personAddUrl}">添加人</a></li>
            
            <c:url var="groupAddParentUrl" value="/operate/fr_group_add_parent/${group.id}">
            </c:url>
            <li><a data-ajax="false" href="${groupAddParentUrl}">给本组全体增加家长</a></li>
            
            <c:url var="trainIdentifyUrl" value="/operate/fr_train_identify/${group.id}">
            </c:url>
            <li style="display:none">
            	<a data-ajax="false" href="${trainIdentifyUrl}">训练本组的人脸标识模型</a>
            </li>
          </ul>
          <ul data-role="listview" data-inset="true">
          <li>分组内所有人</li>
          <c:if test="${not empty personList}">
            <c:forEach items="${personList}" var="person" varStatus="status">
              <c:url value="/operate/fr_person_info/${person.id}" var="statusUrl">
              </c:url>
              <li>
              	<a data-ajax="false" href="${statusUrl}">${empty person.personName ? status.count : person.personName}
              	</a>
              </li>
            </c:forEach>
          </c:if>
        </ul>
        </div>
      </c:if>
      <c:if test="${empty group}">
      	<div><span>没有获取到分组的信息</span></div>
      </c:if>
    </div>
  </div>
  <script type="text/javascript">
  	$(function(){
        $("#snap").click(function() {
            $.ajax({
                type : "POST",
                url : '${showAllCamerasVideo}',
                data: 'boxId=${request.boxId}',
                success : function(data) {
                    if(data == "success"){
                      alert("抓取成功！");
                    }else if(data == "none"){
                      alert("抓取失败！");
                    }
                }
            });
        });
  	});
  </script>
</body>
</html>