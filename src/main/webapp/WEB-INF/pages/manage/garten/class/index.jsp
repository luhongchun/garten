<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/includes/common-res.jsp" %>
    <title>幼儿园班级管理</title>
</head>
<body>
<div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
        <c:url var="addGartenClassAction" value="/garten/manage/class/add"/>
        <c:url var="listGartenClassAction" value="/garten/manage/class/list"/>
        <ul data-role="listview" data-inset="true">
            <li>幼儿园班级管理</li>
            <li><a data-ajax="false" href="${addGartenClassAction}">添加幼儿园班级</a></li>
            <li><a data-ajax="false" href="${listGartenClassAction}">幼儿园班级列表</a></li>
        </ul>
    </div>
</div>
</body>
</html>