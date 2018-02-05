<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/includes/common-res.jsp" %>
    <title>幼儿园业务管理</title>
</head>
<body>
<div data-role="page">
    <div data-role="content">
        <h4><font color="red">${param.tip}</font></h4>
        <c:url var="formAction" value="/garten/manage/garten/edit_commit.action"/>
        <form method="post" name="form_data" id="form_data" action="${formAction}">
            <input type="hidden" name="garten.id" value="${garten.id}">

            <div>
                <h4>幼儿园名称：${garten.name}</h4>
            </div>

			<%-- <div>
				<h4>教师列表</h4>
			</div>
			<div>
				<ul data-role="listview" data-inset="true">
					<c:forEach items="${teachers }" var="teacher" varStatus="status">
						<li><a data-ajax="false" href="javascript:;" onclick="te('${teacher.id}', '${garten.id}')">${empty teacher.aliasName ? '未填写' : teacher.aliasName}</a></li>
					</c:forEach>
				</ul>
			</div> --%>
            
            <div>
                <h4>班级列表</h4>
            </div>
			<div>
				<ul data-role="listview" data-inset="true">
					<c:forEach items="${gartenClasses }" var="gartenClass" varStatus="status">
						<li><a data-ajax="false" href="javascript:;" onclick="ce('${gartenClass.id}')">${empty gartenClass.name ? '未填写' : gartenClass.name}</a></li>
					</c:forEach>
				</ul>
			</div>
        </form>
    </div>
</div>
<script type="text/javascript">
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

	function ce(classId) {
		new submitForm('${ctx}/garten/manage/class/edit', { classId:classId, mId:'${mId}' }).post();
	}

	function te(teacherId, gartenId) {
		new submitForm('${ctx}/garten/manage/teacher/edit', { teacherId:teacherId, gartenId:gartenId }).post();
	}
</script>
</body>
</html>