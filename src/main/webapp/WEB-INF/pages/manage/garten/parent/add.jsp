<%@ page import="cn.smart.cloud.biz.opadmin.entity.SexType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>添加家长</title>
</head>
<body>
	<div data-role="page">
		<div style="margin: 20px 0px 0px 160px;">
			<span style="color: red">${request.msg}</span>
		</div>

		<div data-role="content">
			<c:url var="formAction" value="/garten/manage/parent/add_submit" />
			<form method="post" name="form_data" id="form_data"
				action="${formAction}">
				<input type="hidden" name="TokenName" value="${session.TokenName}">
				<input type="hidden" name="childId" value="${childId}">
				<input type="hidden" name="mId" value="${mId}">
				
				<div class="ui-field-contain">
					<label for="name">姓名<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="name" name="name" placeholder="请输入姓名"
						required="required" data-clear-btn="true">
				</div>
				
				<div class="ui-field-contain">
					<c:set var="male" value="<%=SexType.MALE%>" />
					<c:set var="female" value="<%=SexType.FEMALE%>" />
					<label><input type="radio" name="sex" value="MALE" <c:if test="${sex == male}">checked="checked"</c:if>>男</label>
           			<label><input type="radio" name="sex" value="FEMALE" <c:if test="${sex == female}">checked="checked"</c:if>>女</label>
				</div>
				
				<div class="ui-field-contain">
					<label for="name">手机号<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="phoneNum" name="phoneNum" placeholder="请输入手机号"
						required="required" data-clear-btn="true">
				</div>

				<div class="ui-field-contain" align="center">
					<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
						type="submit">提交</button>
					<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
						type="reset">重置</button>
					<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
						type="button" id="backBtn">返回</button>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#backBtn").click(function() {
				new submitForm('${ctx}/garten/manage/child/edit', { childId:'${childId }', mId:'${mId }' }).post();
			});
			$("#form_data").submit(function() {
				$(":submit", this).attr("disabled", true);
			})
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
	</script>
</body>
</html>