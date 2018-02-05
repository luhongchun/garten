<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>幼儿园列表</title>
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
				<li>幼儿园列表</li>
				<c:forEach items="${gartenList }" var="garten" varStatus="status">
					<li><a data-ajax="false" href="javascript:;" onclick="ge('${garten.id}')">${empty garten.name ? '未填写' : garten.name}</a></li>
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
				window.location.href = "/garten/manage/garten/add";
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

	  	function ge(gartenId) {
	  		new submitForm('${ctx}/garten/manage/garten/edit', { gartenId:gartenId, mId:'${mId}' }).post();
	  	}
	</script>
</body>
</html>