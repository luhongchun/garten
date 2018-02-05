<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>用户管理</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background:#EFEEF3;">
<div class="hd">
  <div class="weui_cells weui_cells_access" style="margin-top:20px;">
    <c:forEach items="${classList }" var="gartenclass" varStatus="status">
      <a class="weui_cell" href="javascript:;" onclick="manageAccountChildren('${gartenclass.id}')">
        <div class="weui_cell_bd weui_cell_primary">
          <p>${gartenclass.name }</p>
        </div>
        <span class="weui_cell_ft"></span>
      </a>
    </c:forEach>
  </div>
</div>
</body>

<script type="text/javascript">
    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }

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

    function manageAccountChildren(classId) {
        new submitForm('${ctx}/biz/setting/action_user_account_children', {
            openId: '${openId}',
            classId: classId
        }).post();
    }
</script>
</html>

