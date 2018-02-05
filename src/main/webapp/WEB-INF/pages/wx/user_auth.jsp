<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>智启科技</title>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script type="text/javascript">
      $(document).bind("mobileinit", function () {
          $.mobile.ajaxEnabled = false;
      });
  </script>
</head>

<body ontouchstart>

<c:url var="sendVerifCodeUrl" value="/biz/user/sendVerifCode"/>
<c:url var="authUrl" value="/biz/user/base_user_auth"/>

<div class="weui_msg">
  <div class="weui_icon_area">
    <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/mengbaoriji.png" width="70px"></img>
    <p style="font-size:18px; color:#4C4C4C;">智启萌宝日记</p>
  </div>
</div>

<form>
  <div class="weui_cells weui_cells_form" style="margin-top:-15px;">
    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/phone_register.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <input id="pNumber" class="weui_input" type="number" pattern="[0-9]*" style="font-size:16px; margin-left:9px;"
               placeholder="请输入手机号码"/>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/code_register.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <input id="cNumber" class="weui_input" type="number" pattern="[0-9]*" maxlength="6"
               style="font-size:16px; margin-left:9px;" placeholder="请输入验证码"/>
      </div>
      <a type="button" id="getVCodeBtn" href="javascript:;" class="weui_btn weui_btn_primary"
         style="margin-top:-9px;margin-bottom:-9px;margin-right:-6px;font-size:16px; width:140px;padding-left:5px;padding-right:5px;">获取验证码</a>
    </div>
  </div>
</form>

<div class="bd" style="margin-top:50px;margin-left:10px;margin-right:10px;">
  <a id="registerButton" href="javascript:;" class="weui_btn weui_btn_primary">验 证 手 机</a>
</div>

<!--BEGIN promptDialog-->
<div class="weui_dialog_confirm" id="promptDialog" style="display:none;">
  <div class="weui_mask"></div>
  <div class="weui_dialog">
    <div id="dlgTitle" class="weui_dialog_hd"><strong class="weui_dialog_title">注册提示</strong></div>
    <div class="weui_dialog_bd"><p id="dlgText" align="center"></p></div>
    <div class="weui_dialog_ft">
      <a id="centerBtn" href="javascript:;" class="weui_btn_dialog primary">确定</a>
    </div>
  </div>
</div>
<!--END promptDialog-->

<!--BEGIN promptDialog2-->
<div class="weui_dialog_confirm" id="promptDialog2" style="display:none;">
  <div class="weui_mask"></div>
  <div class="weui_dialog">
    <div id="dlgTitle2" class="weui_dialog_hd"><strong class="weui_dialog_title">注册提示</strong></div>
    <div class="weui_dialog_bd"><p id="dlgText2" align="center"></p></div>
    <div class="weui_dialog_ft">
      <a id="leftBtn" href="tel:4000225085" class="weui_btn_dialog primary">联系客服</a>
      <a id="rightBtn" href="javascript:;" class="weui_btn_dialog primary">重新注册</a>
    </div>
  </div>
</div>
<!--END promptDialog2-->

<script type="text/javascript">
    function Timer() {
    }

    Timer.timerCount = 0;
    Timer.refresher = function () {
        var btn = document.getElementById('getVCodeBtn');
        btn.innerHTML = --Timer.timerCount + "秒后重新获取";
        btn.disable = true;
        if (Timer.timerCount < 0) {
            resetVcodeBtn();
        }
    };

    Timer.start = function () {
        if (Timer.timerInterval) {
            clearInterval(Timer.timerInterval);
        }
        Timer.timerInterval = setInterval(Timer.refresher, 1000);
    };


    //获取验证码按钮
    var getVCodeBtn = document.getElementById("getVCodeBtn");
    getVCodeBtn.addEventListener("click", function () {
        if (Timer.timerCount > 0)
            return;
        getVCodeBtn.setAttribute("class", "weui_btn weui_btn_disabled weui_btn_primary");
        var text = document.getElementById('getVCodeBtn').innerHTML;

        //判断手机号码是否合法
        var phoneNum = document.getElementById('pNumber').value;
        var reg = /^0{0,1}(13[0-9]|147|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
        if (phoneNum == null || "" == phoneNum || !reg.test(phoneNum)) {
            showPrompt("注册提示", "请输入正确的手机号码！", "确认");
            getVCodeBtn.setAttribute("class", "weui_btn weui_btn_primary");
            return false;
        }

        //启动定时器
        Timer.timerCount = 60;
        getVCodeBtn.innerHTML = Timer.timerCount + "秒后重新获取";
        Timer.start();
        $.post("${sendVerifCodeUrl}" + "/" + '${openId}' + "/" + phoneNum, function (data) {
            if ("success" == data) {
                showPrompt("短信验证", "短信验证码已发送,请您在15分钟内填写并完成注册!", "确认");
            } else if ("registered" == data) {
                window.location.href = "/biz/user/registered";
            } else if ("invalid" == data) {
                var regWarn = document.getElementById('promptDialog2');
                var dlgTitle = document.getElementById('dlgTitle2');
                var dlgText = document.getElementById('dlgText2');
                var leftBtn = document.getElementById('leftBtn');
                var rightBtn = document.getElementById('rightBtn');
                dlgTitle.innerHTML = "获取失败";
                dlgText.innerHTML = "信息错误，请重新关注公众号！";
                regWarn.style.display = "block";
                bgetCode = true;
            } else {
                var regWarn = document.getElementById('promptDialog2');
                var dlgTitle = document.getElementById('dlgTitle2');
                var dlgText = document.getElementById('dlgText2');
                var leftBtn = document.getElementById('leftBtn');
                var rightBtn = document.getElementById('rightBtn');
                dlgTitle.innerHTML = "获取失败";
                dlgText.innerHTML = "请重新尝试或者联系客服咨询！";
                regWarn.style.display = "block";
                bgetCode = true;
            }
        });
    });

    //提示框确认按钮
    var pDialog = document.getElementById("promptDialog");
    pDialog.addEventListener("click", function () {
        pDialog.style.display = "none";
    });

    //提示框确认按钮
    var pDialog2 = document.getElementById("promptDialog2");
    pDialog2.addEventListener("click", function () {
        pDialog2.style.display = "none";
        resetVcodeBtn();
    });

    //注册按钮
    var registerBtn = document.getElementById("registerButton");
    registerBtn.addEventListener("click", function () {
        registerBtn.setAttribute("class", "weui_btn weui_btn_disabled weui_btn_primary");

        //判断手机号码是否合法
        var phoneNum = document.getElementById('pNumber').value;
        var reg = /^0{0,1}(13[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$/;
        if (phoneNum == null || "" == phoneNum.trim() || !reg.test(phoneNum)) {
            showPrompt("注册失败", "请输入正确的手机号码！", "确认");
            registerBtn.setAttribute("class", "weui_btn weui_btn_primary");
            return false;
        }

        //判断注册码是否合法
        var cText = document.getElementById('cNumber').value;
        if (cText == null || "" == cText.trim()) {
            showPrompt("注册失败", "请输入短信验证码！", "确认");
            registerBtn.setAttribute("class", "weui_btn weui_btn_primary");
            return false;
        }
        $.post("${authUrl}" + "/" + '${openId}' + "/" + phoneNum + "/" + cText, function (data) {
            if (data.code != 0) {//0：注册成功，1：用户已注册 -1：电话号码与验证码匹配或验证码错误，-2：未开通信息 -3：信息不完整
                if (data.code == 1) {
                    window.location.href = "/biz/user/registered";
                } else if (data.code == -1) {
                    showPrompt("注册失败", "电话号码与验证码不匹配或错误！", "确认");
                    registerBtn.setAttribute("class", "weui_btn weui_btn_primary");
                } else if (data.code == -2) {
                    /* showPrompt("注册失败", "您尚未开通相关业务，请联系幼儿园进行开通！", "确认");
                    registerBtn.setAttribute("class", "weui_btn weui_btn_primary"); */
                    window.location.href = "";
                    new submitForm('${ctx}/biz/user/base_user_register_page', {
                        openId: '${openId}',
                        phoneNum: phoneNum,
                        gartenId: '${gartenId}'
                    }).post();
                } else if (data.code == -3) {
                    showPrompt("注册失败", "信息不完整，请联系客服人员咨询！", "确认");
                    registerBtn.setAttribute("class", "weui_btn weui_btn_primary");
                } else {
                    showPrompt("注册失败", "未知错误，请联系客服人员咨询！", "确认");
                    registerBtn.setAttribute("class", "weui_btn weui_btn_primary");
                }
            } else {
                WeixinJSBridge.call('closeWindow');
            }
        });
    });

    function showPrompt(title, text, btn) {
        var warnDlg = document.getElementById('promptDialog');
        var dlgTitle = document.getElementById('dlgTitle');
        var dlgText = document.getElementById('dlgText');
        var centerBtn = document.getElementById('centerBtn');
        dlgTitle.innerHTML = title;
        dlgText.innerHTML = text;
        centerBtn.innerHTML = btn;
        warnDlg.style.display = "block";
    }

    function resetVcodeBtn() {
        Timer.timerCount = 0;
        clearInterval(Timer.timerInterval);
        Timer.timerInterval = undefined;
        var btn = document.getElementById('getVCodeBtn');
        btn.innerHTML = "获取验证码";
        btn.setAttribute("class", "weui_btn weui_btn_primary");
        bgetCode = false;
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
</script>
</body>
</html>