<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>学生列表</title>
</head>
<body>
	<div data-role="page">
		<div style="margin: 20px 0px 0px 160px;">
			<span style="color: red">${request.msg}</span>
		</div>

		<div data-role="main" class="ui-content jqm-content">
			<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
				type="button" id="addBtn">添加</button>
			<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
				type="button" id="homeBtn">返回主菜单</button>
			<ul data-role="listview" data-inset="true" data-filter="true"
				data-input="#myFilter">
				<li>学生列表</li>
				<c:forEach items="${children }" var="child" varStatus="status">
					<li><a data-ajax="false" href="javascript:;" onclick="ce('${child.id }')">${empty child.aliasName ? '未填写' : child.aliasName}</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#searchBtn").click(function() {
				$("#searchForm").attr("action", "${searchAction}");
				$("#searchForm").submit();
			});
			
			$("#addBtn").click(function() {
				ca();
			});
			
			$("#homeBtn").click(function() {
				home();
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

		function ca() {
			new submitForm('${ctx}/garten/manage/child/add', { classId:'${classId}', mId:'${mId}' }).post();
		}

		function ce(childId) {
			new submitForm('${ctx}/garten/manage/child/edit', { childId:childId, mId:'${mId}' }).post();
		}

		function home() {
			new submitForm('${ctx}/garten/manage/garten/list', { managerId:'${mId}' }).post();
		}
	</script>
</body>
</html>