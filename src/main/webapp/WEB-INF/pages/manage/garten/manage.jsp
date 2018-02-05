<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>管理首页</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <ul data-role="listview" data-inset="true">
        <li>管理首页</li>
        <li><a data-ajax="false" href="javascript:;" onclick="gi()">用户数据管理</a></li>
        <li><a data-ajax="false" href="javascript:;" onclick="am()">用户权限管理</a></li>
        <li><a data-ajax="false" href="javascript:;" onclick="cm()">直播区域管理</a></li>
      </ul>
    </div>
  </div>
</body>
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

	function gi() {
		new submitForm('${ctx}/garten/manage/garten/info', { gartenId:'${garten.id}', mId:'${mId}' }).post();
	}

	function am() {
		new submitForm('${ctx}/garten/manage/action_class_account', { gartenId:'${garten.id}' }).post();
	}

	function cm() {
		new submitForm('${ctx}/garten/manage/action_camera_setting', { gartenId:'${garten.id}' }).post();
	}
</script>
</html>