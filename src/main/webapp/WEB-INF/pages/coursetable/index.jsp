<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>排课管理</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <li>排课管理</li>
        <!-- 授课机构信息 -->
      	<s:url var="addOrgAction" value="/coursetable/org/add.action" />
        <s:url var="listOrgAction" value="/coursetable/org/list.action" />
        <%-- <li><a data-ajax="false" href="${addOrgAction}">添加授课机构</a></li> --%>
        <li><a data-ajax="false" href="${listOrgAction}">授课机构信息管理</a></li>
        
        <!-- 授课教师信息 -->
      	<s:url var="addTeacherAction" value="/coursetable/teacher/add.action" />
        <s:url var="listTeacherAction" value="/coursetable/teacher/list.action" />
        <%-- <li><a data-ajax="false" href="${addTeacherAction}">添加授课教师</a></li> --%>
        <li><a data-ajax="false" href="${listTeacherAction}">授课教师信息管理</a></li>
        
        <!-- 授课教室信息 -->
      	<s:url var="addTeachclassroomAction" value="/coursetable/teachclassroom/add.action" />
        <s:url var="listTeachclassroomAction" value="/coursetable/teachclassroom/list.action" />
        <%-- <li><a data-ajax="false" href="${addTeachclassroomAction}">添加授课教室</a></li> --%>
        <li><a data-ajax="false" href="${listTeachclassroomAction}">授课教室信息管理</a></li>
        
        <!-- 授权幼儿园信息 -->
      	<s:url var="addKindergartenAction" value="/coursetable/kindergarten/add.action" />
        <s:url var="listKindergartenAction" value="/coursetable/kindergarten/list.action" />
        <%-- <li><a data-ajax="false" href="${addKindergartenAction}">添加授权幼儿园</a></li> --%>
        <li><a data-ajax="false" href="${listKindergartenAction}">授权幼儿园信息管理</a></li>
        
        <!-- 班级信息 -->
      	<s:url var="addClassAction" value="/coursetable/class/add.action" />
        <s:url var="listClassAction" value="/coursetable/class/list.action" />
        <%-- <li><a data-ajax="false" href="${addClassAction}">添加班级</a></li> --%>
        <li><a data-ajax="false" href="${listClassAction}">班级信息管理</a></li>
        
        <!-- 听课教室信息 -->
        <s:url var="addStudyclassroomAction" value="/coursetable/studyclassroom/add.action" />
        <s:url var="listStudyclassroomAction" value="/coursetable/studyclassroom/list.action" />
        <%-- <li><a data-ajax="false" href="${addStudyclassroomAction}">添加听课教室</a></li> --%>
        <li><a data-ajax="false" href="${listStudyclassroomAction}">听课教室信息管理</a></li>
        
        <!-- 辅助教师信息 -->
        <s:url var="addAssistantteacherAction" value="/coursetable/assistantteacher/add.action" />
        <s:url var="listAssistantteacherAction" value="/coursetable/assistantteacher/list.action" />
        <%-- <li><a data-ajax="false" href="${addAssistantteacherAction}">添加辅助教师</a></li> --%>
        <li><a data-ajax="false" href="${listAssistantteacherAction}">辅助教师信息管理</a></li>
        
        <!-- 排课信息 -->
        <s:url var="addArrangementAction" value="/coursetable/arrangement/add.action" />
        <s:url var="listArrangementAction" value="/coursetable/arrangement/list.action" />
        <%-- <li><a data-ajax="false" href="${addArrangementAction}">添加排课</a></li> --%>
        <li><a data-ajax="false" href="${listArrangementAction}">排课信息管理</a></li>
        
        <!-- excel批量导入 -->
        <s:url var="exportInputAction" value="/coursetable/batch/exportInput.action" />
        <li><a data-ajax="false" href="${exportInputAction}">批量导入</a></li>
      </ul>
    </div>
  </div>
</body>
</html>