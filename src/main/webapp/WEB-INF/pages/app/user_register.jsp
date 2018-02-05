<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
s
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>添加用户</title>
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <link rel="stylesheet" type="text/css" href="${ctx}/css/commonLogin.css"/>
  <link rel="stylesheet" type="text/css" href="${ctx}/css/styleLogin.css"/>
  <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
  <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
  <script type="text/javascript" src="${ctx}/dwr/interface/CaptchaDwrAjaxClient.js"></script>

  <script type="text/javascript">
      // 执行登录操作，登录前需要先校验验证码是否正确。
      function myRegister() {
          var registerForm = document.forms["registerForm"];
          var login_id = registerForm.login_id.value;
          var password = registerForm.password.value;
          var rePassword = registerForm.rePassword.value;
          if (login_id === "") {
              alert("登录名不能为空！");
              registerForm.login_id.focus();
              return false;
          }
          if (password === "") {
              alert("密码不能为空！");
              registerForm.password.focus();
              return false;
          }
          if (rePassword != password) {
              alert("两次密码不一致！");
              registerForm.password.focus();
              return false;
          }
          var verifyCode = document.forms[0].verifyCode.value;
          CaptchaDwrAjaxClient.validateVerifyCode(verifyCode, {
              callback: function (data) {
                  submitMyRegister(data);
              }
          });
      }

      // 根据返回的值，判断是否验证成功。
      function submitMyRegister(data) {
          if (data == true) {
              $.ajax({
                  type: "POST",
                  url: "${ctx}/sys/user/user_register.action",
                  data: $("#registerForm").serialize(),
                  success: function (msg) {
                      if (msg.code == 0) {
                          //$("#azbInfoForm").reset();
                          //resetField($("#azbInfoForm"));
                          resetForm();
                          alert("注册成功！");
                      } else {
                          alert("注册失败！");
                      }
                  }
              });
          } else {
              alert("验证码不正确");
              changeVerifyCode();
              return;
          }
      }

      //清空表单值
      function resetForm() {
          $(':input', '#registerForm')
              .not(':button, :submit, :reset')
              .val('');
      }

      // 重新获取校验码。
      function changeVerifyCode() {
          // 如果给url赋相同的值，浏览器不会重新发出请求，因此用js生成一个即时毫秒数做url中的参数。
          t = new Date().getTime();
          document.forms[0].verifyCodeImg.src = "${ctx}/security/captcha?t=" + t;
      }
  </script>
</head>
<body onload="document.getElementById('login_id').focus();">
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <div style="width:50%; margin:auto;">
      <form name="registerForm" id="registerForm" method="post"
            action='${ctx}/sys/user/user_register.action'>
        <h4>登录名：</h4>
        <input type="text" id="login_id" class="input" name="login_id" tabindex="1"/>
        <h4>真实姓名：</h4>
        <input type="text" id="user_name" class="input" name="user_name" tabindex="2"/>
        <h4>密码：</h4>
        <input type="password" class="input" name="password" tabindex="3"/>
        <h4>重复密码：</h4>
        <input type="password" class="input" name="rePassword" tabindex="4"/>
        <h4>校&nbsp;验&nbsp;码：</h4>
        <input id="verifyCode" name="verifyCode" class="input" style="width:145px" tabindex="5"/>
        <img id="verifyCodeImg" name="verifyCodeImg" src="${ctx}/security/captcha" onclick="changeVerifyCode();"
             alt="点击图片，换一张"/>

        <input type="button" value="提交" onclick="myRegister()"/>
        <input type="reset" value="重置"/>
      </form>
      <div style="margin:20px 0px 0px 160px;">
        <span style="color:red">${loginInfo }</span>
      </div>
    </div>
  </div>
</div>
</body>
</html>
