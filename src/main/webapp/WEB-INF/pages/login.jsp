<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>智启管理平台</title>
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <script type="text/javascript">
      // 执行登录操作，登录前需要先校验验证码是否正确。
      function myLogin() {
          var loginForm = document.forms["loginForm"];
          var username = loginForm.login_identity.value;
          var password = loginForm.password.value;
          if (username === "") {
              alert("用户名不能为空！");
              loginForm.login_identity.focus();
              return false;
          }
          if (password === "") {
              alert("密码不能为空！");
              loginForm.password.focus();
              return false;
          }
          var verifyCode = document.forms[0].verifyCode.value;

          //auth(verifyCode);
          submitMyLogin("true");
      }

      // 根据返回的值，判断是否验证成功。
      function submitMyLogin(data) {
          if (data === "true") {
              document.loginForm.submit();
          } else {
              alert("验证码不正确");
              changeVerifyCode();
          }
      }

      // 重新获取校验码。
      function changeVerifyCode() {
          // 如果给url赋相同的值，浏览器不会重新发出请求，因此用js生成一个即时毫秒数做url中的参数。
          var t = new Date().getTime();
          document.forms[0].verifyCodeImg.src = "${ctx}/security/captcha?t=" + t;
      }

      function enterKeyOnClick(e) {
          var theEvent = window.event || e;
          var code = theEvent.keyCode || theEvent.which;
          if (code === 13) {  //回车键的键值为13
              myLogin();  //调用搜索事件
          }
      }

      <c:url var="authUrl" value="/security/auth"/>
      // 校验权限
      function auth(verifyCode) {
          $.post("${authUrl}" + "/" + verifyCode, function (data) {
              submitMyLogin(data);
          });
      }
  </script>
</head>
<body onload="document.getElementById('username').focus();">
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <div style="width:90%;max-width:450px; margin:auto;">
      <form name="loginForm" id="loginForm" method="post" action='${ctx}/security/login'>
        <h4>账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：</h4>
        <input type="text" id="login_identity" class="input" name="login_identity" tabindex="1"/>

        <h4>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</h4>
        <input type="password" class="input" name="password" tabindex="2"/>

        <h4 style="display:none">校&nbsp;验&nbsp;码：</h4>
        <input style="display:none" id="verifyCode" name="verifyCode" onkeydown="enterKeyOnClick(event)" class="input" tabindex="3"/>
        <img style="display:none" id="verifyCodeImg" name="verifyCodeImg" src="${ctx}/security/captcha"
             onclick="changeVerifyCode();" alt="点击图片，换一张"/>

        <input type="button" onclick="myLogin();" tabindex="4" value="登录"/>
        <input type="reset" value="重置" tabindex="5"/>
      </form>
      <div style="margin:20px 0px 0px 160px;">
        <span style="color:red">${loginInfo }</span>
      </div>
    </div>
  </div>
</div>
</body>
</html>