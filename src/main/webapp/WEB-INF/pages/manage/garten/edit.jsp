<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/includes/common-res.jsp" %>
    <title>编辑幼儿园</title>
</head>
<body>
<div data-role="page">
    <div data-role="content">
        <h4><font color="red">${param.tip}</font></h4>
        <c:url var="formAction" value="/garten/manage/garten/edit_commit"/>
        <form method="post" name="form_data" id="form_data" action="${formAction}">
            <input type="hidden" name="garten.id" value="${garten.id}">

            <div>
                <h4>幼儿园名称</h4>
            </div>
            <div>
                <input type="text" id="name" name="garten.name" placeholder="请输入幼儿园名称" required="required"
                       value="${garten.name}">
            </div>
            <div>
                <h4>有效家长用户的数量:${gartenUserCnt}</h4>
            </div>
            <div>
                <h4>官网地址</h4>
            </div>
            <div>
                <input type="text" id="officialWebsite" name="gartenInfo.officialWebsite" placeholder="幼儿园官网地址"
                       required="required" value="${gartenInfo.officialWebsite}">
            </div>
            <div>
                <h4>联系电话</h4>
            </div>
            <div>
                <input type="tel" id="tel" name="gartenInfo.tel" placeholder="幼儿园官方电话" required="required"
                       value="${gartenInfo.tel}">
            </div>
            <div>
                <h4>幼儿园LOGO地址</h4>
            </div>
            <div>
                <input type="url" id="logoUrl" name="gartenInfo.logoUrl" placeholder="幼儿园LOGO地址" required="required"
                       value="${gartenInfo.logoUrl}">
            </div>
            <div>
                <h4>描述</h4>
            </div>
            <div>
                <input type="text" id="description" name="garten.description" placeholder="幼儿园描述信息"
                       value="${garten.description}">
            </div>
            
      		<ul data-role="listview" data-inset="true">
      		    <li>管理首页</li>
      		    <li><a data-ajax="false" href="javascript:;" onclick="gi()">用户数据管理</a></li>
        		<li><a data-ajax="false" href="javascript:;" onclick="am()">用户权限管理</a></li>
        		<li><a data-ajax="false" href="javascript:;" onclick="cm()">直播区域管理</a></li>
     		</ul>
            <input type="submit" value="修改">
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#form_data").submit(function () {
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
</body>
</html>