<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>教师列表</title>
</head>
<body>
	<div data-role="page">
		<div style="margin: 20px 0px 0px 160px;">
			<span style="color: red">${request.msg}</span>
		</div>

		<div data-role="main" class="ui-content jqm-content">
			<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
				type="button" id="addBtn">添加</button>
			<ul data-role="listview" data-inset="true" data-filter="true"
				data-input="#myFilter">
				<li>教师列表</li>
				<c:forEach items="${teachers }" var="teacher" varStatus="status">
					<li><a data-ajax="false" href="" onclick="te('${teacher.id}')">${empty teacher.aliasName ? '未填写' : teacher.aliasName}</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#addBtn").click(function() {
				new submitForm('${ctx}/garten/manage/teacher/add', { gartenId:'${gartenId }' }).post();
			});
		});

		function submitForm(url, data) {
		　　var eleForm = document.body.appendChild(document.createElement('form'));
		　　eleForm.action = url;
		　　for (var property in data) {
		　　　　var hiddenInput = document.createElement('input');
		　　　　hiddenInput.type = 'hidden';
		　　　　hiddenInput.name = property;
		　　　　hiddenInput.value = data[property];
		　　　　eleForm.appendChild(hiddenInput);
		　　}
		　　this.eleForm = eleForm;
		　　if (!submitForm._initialized) {
		　　　　submitForm.prototype.post = function () {
		　　　　　　this.eleForm.method = 'post';
		　　　　　　this.eleForm.submit();
		　　　　};
		      submitForm._initialized = true;
		　　}
		}

		function te(teacherId) {
			new submitForm('${ctx}/garten/manage/teacher/edit', { teacherId:teacherId, gartenId:'${gartenId}' }).post();
		}
	</script>
</body>
</html>