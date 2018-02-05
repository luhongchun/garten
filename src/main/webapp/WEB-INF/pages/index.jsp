<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<%@ include file="/WEB-INF/includes/common-res.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8"/>
  <meta name="viewport"
        content="width=device-width initial-scale=1.0 maximum-scale=1.0 user-scalable=yes"/>
  <title>后台管理</title>
  <link type="text/css" rel="stylesheet" href="css/jquery.mmenu.all.css"/>
  <link type="text/css" rel="stylesheet"
        href="${ctx }/css/jquery.mmenu.all.css"/>
  <script type="text/javascript" src="${ctx }/js/jquery.mmenu.min.all.js"></script>
  <style type="text/css">
    body {
      overflow-x: hidden;
      overflow-y: hidden;
      overflow: hidden;
    }
  </style>
  <script type="text/javascript">
      function updateTitle() {
          $('#title').html($('#main').contents().find('title').html());
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

      function gi() {
          new submitForm('${ctx}/garten/manage/garten/info', {gartenId: '${garten.id}'}).post();
      }

      function am() {
          new submitForm('${ctx}/garten/manage/action_class_account', {gartenId: '${garten.id}'}).post();
      }

      function cm() {
          new submitForm('${ctx}/garten/manage/action_camera_setting', {gartenId: '${garten.id}'}).post();
      }
  </script>
</head>
<body>
<div id="page">
  <div data-role="header">
    <a href="#menu" data-icon="grid">菜单</a>
    <h1 id="title">首页</h1>
    <a href="#" data-rel="back" data-icon="back">返回</a>
  </div>
  <iframe id="main" name="main" width="100%" height="100%"
          src="${ctx }/manage/garten/manage.jsp" frameborder="no" border="0"
          marginwidth="0" marginheight="0" scrolling="yes"
          allowtransparency="true" onload="updateTitle();">

    <nav id="menu">
      <ul>
        <li><a data-ajax="false" href="javascript:;" onclick="gi()">用户数据管理</a></li>
        <li><a data-ajax="false" href="javascript:;" onclick="am()">用户权限管理</a></li>
        <li><a data-ajax="false" href="javascript:;" onclick="cm()">直播区域管理</a></li>
        <li><a data-ajax="false"
               onclick="javascript:window.parent.location.href='/security/logout.action';return false;">退出登录</a>
        </li>
      </ul>
    </nav>
  </iframe>
</div>
</body>
</html>