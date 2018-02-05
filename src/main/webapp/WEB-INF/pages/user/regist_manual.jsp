<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>身份认证</title>
  <link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
  <script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
  <script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
  <style>
    .ui-block-c {
      font-weight: bold;
      text-align: left;
      padding: 10px;
    }
  </style>
  <style type="text/css">
    .ui-page {
      background: white
    }
  </style>
</head>

<body>
<s:url var="sendVerifCodeUrl" value="/wx/sendVerifCode.action"/>
<s:url var="captchaUrl" value="/security/captcha.action"/>
<s:url var="registerUrl" value="/wx/global_user_register.action"/>

<div data-role="page">
  <div data-role="header" data-theme="e">
    <h1>
      <span style="font-size:20px;color:#CC3300;">用 户 注 册</span>
    </h1>
  </div>

  <div data-role="content">
    <form id="registerForm" method="post" action="${registerUrl}">
      <input type="hidden" name="openId" value="${openId}" form="registerForm">

      <label for="telNum">手机号</label>
      <div class="ui-grid-a">
        <div class="ui-block-a">
          <input type="tel" name="telNum" id="telNum" placeholder="请输入手机号码" maxlength="11" required="required"
                 form="registerForm">
        </div>
        <div class="ui-block-b">
          <button id="sendBtn" data-corners="false" data-shadow="false" data-theme="e">
            <span style="font-size:14px;color:#CC3300;">获取手机验证码</span>
          </button>
        </div>
      </div>

      <div class="ui-grid-b">
        <div class="ui-block-a">
          <input type="text" id="captchaCode" maxlength="4" placeholder="请输入验证码" form="registerForm">
        </div>
        <div class="ui-block-b">
          <img id="captchaImg"/>
        </div>
        <div class="ui-block-c">
          <a href="#" id="captchaImgChange" data-role="label" font-size="1px">
            <span style="font-size:12px;color:#CC3300;">看不清？换一张</span>
          </a>
        </div>
      </div>

      <label for="verifCode">手机验证码</label>
      <input type="tel" type="text" name="verifCode" id="verifCode" placeholder="请输入手机验证码" maxlength="11"
             required="required" form="registerForm">
      <button id="registerSubmit" type="button" data-corners="false" data-shadow="false" data-theme="e">
        <span style="font-size:14px;color:#CC3300;">注 册</span>
      </button>
    </form>
  </div>
</div>


<script type="text/javascript">
    $(function () {
        function Timer() {
        }

        Timer.timerCount = 0;
        Timer.refresher = function () {
            $("#sendBtn").html(Timer.timerCount + "秒后重新发送");
            $("#sendBtn").attr("disabled", true);
            if (--Timer.timerCount < 0) {
                clearInterval(Timer.timerInterval);
                Timer.timerInterval = undefined;
                $("#sendBtn").attr("disabled", false);
                $("#sendBtn").html("发送验证码");
            }
        };

        Timer.start = function () {
            if (Timer.timerInterval) {
                clearInterval(Timer.timerInterval);
            }
            Timer.timerInterval = setInterval(Timer.refresher, 1000);
        };

        function loadCaptcha() {
            $("#captchaImg").attr("src", '${captchaUrl}?t=' + new Date());
        }

        loadCaptcha();
        $("#captchaImgChange").click(function () {
            loadCaptcha();
        });
        $("#sendBtn").click(function () {
            var captchaCode = $("#captchaCode").val();
            if (captchaCode == null || "" == captchaCode) {
                alert("安全验证码不能为空");
                return;
            }
            var phoneNum = $("#telNum").val();
            var reg = /^0{0,1}(13[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
            if (phoneNum == null || "" == phoneNum || !reg.test(phoneNum)) {
                alert("请输入合法的手机号码");
                return;
            }
            $("#sendBtn").attr("disabled", true);
            $.post("${sendVerifCodeUrl}", {
                    openId: '${openId}', phoneNum: $("#telNum").val(),
                    captchaCode: captchaCode
                },
                function (data) {
                    loadCaptcha();
                    if ("0" == data) {
                        Timer.timerCount = 120;
                        Timer.start();
                        alert("发送成功");
                    } else if ("1" == data) {
                        alert("验证码输入错误");
                        $("#sendBtn").attr("disabled", false);
                    } else if ("2" == data) {
                        alert("您已经注册过了");
                    } else if ("3" == data) {
                        alert("您输入的手机号码尚未认证，请联系幼儿园");
                    }
                    else {
                        alert("服务器异常");
                        $("#sendBtn").attr("disabled", false);
                    }
                });
        });

        // submit form
        $("#registerSubmit").click(function () {
            if (checkRegisterForm()) {
                $.ajax({
                    type: "GET",
                    url: '${registerUrl}',
                    data: $("#registerForm").serialize(),
                    success: function (msg) {
                        if (msg.code != 0) {
                            alert(msg.msg);
                        } else {
                            WeixinJSBridge.call('closeWindow');
                        }
                    },
                    failure: function (msg) {
                        alert("服务器异常！");
                    }
                });
            }
        });
    });

    function checkRegisterForm() {
        var phoneNum = $("#telNum").val();
        var verifCode = $("#verifCode").val();

        var reg = /^0{0,1}(13[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
        if (phoneNum == null || "" == phoneNum.trim() || !reg.test(phoneNum)) {
            alert("请输入合法的手机号码");
            return false;
        }
        if (verifCode == null || "" == verifCode.trim()) {
            alert("请输入手机验证码");
            return false;
        }
        return true;
    }
</script>
</body>
</html>