<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>FR接口测试</title>
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
            <c:url var="groupInfoEditUrl" value="/operate/fr_group_edit/${group.id}">
            </c:url>
            <li><a data-ajax="false" href="${groupInfoEditUrl}">编辑分组信息</a></li>
          </ul>
          <ul data-role="listview" data-inset="true">
          <li>分组内所有人</li>
          <c:if test="${not empty personList}">
            <c:forEach items="${personList}" var="person" varStatus="status">
              <c:url value="/operate/fr_person_info/${person.id}" var="statusUrl">
              </c:url>
              <li>
              	<a data-ajax="false" href="${statusUrl}">人${empty person.personName ? status.count : person.personName}
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