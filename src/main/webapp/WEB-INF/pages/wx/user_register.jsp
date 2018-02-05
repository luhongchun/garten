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
  <link rel="stylesheet" href="http://static-10001988.cossh.myqcloud.com/resource/style/select.css"/>
  <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
  <script type="text/javascript">
      $(document).bind("mobileinit", function () {
          $.mobile.ajaxEnabled = false;
      });
  </script>
</head>

<body ontouchstart>

<c:url var="sendVerifCodeUrl" value="/biz/user/sendVerifCode"/>
<c:url var="registerUrl" value="/biz/user/base_user_register"/>

<div class="weui_msg">
  <div class="weui_icon_area">
    <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/user.svg" width="70px"></img>
    <p style="font-size:18px; color:#4C4C4C;">信息认证</p>
  </div>
</div>

<form name="dataForm" id="dataForm" style="margin-top:50px;">
  <input type="hidden" name="openId" value="${openId}">
  <input type="hidden" name="phoneNum" value="${phoneNum}">
  <div class="weui_cells weui_cells_form">
    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/gardenLogo.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <input id="gartenName" class="weui_input" style="font-size:16px; margin-left:9px;" value="${gartenName }"
               readonly="readonly"/>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/baby.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <input id="childName" name="childName" class="weui_input" style="font-size:16px; margin-left:9px;"
               placeholder="请输入孩子姓名"/>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/class.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <label for="select" class="select" style="margin-top:-1px; margin-bottom:-1px;">
          <select name="gartenClass" id="gartenClass" style="font-size:16px; color:#000000;">
            <option value="" disabled selected>请选择您孩子的班级</option>
            <c:forEach items="${classes}" var="cls">
              <option value="${cls.id}">${cls.name}</option>
            </c:forEach>
          </select>
        </label>
      </div>
    </div>

  </div>
</form>

<div class="bd" style="margin-top:50px;margin-left:10px;margin-right:10px;">
  <a id="registerButton" href="javascript:;" class="weui_btn weui_btn_primary">完 成 认 证</a>
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
    //提示框确认按钮
    var pDialog = document.getElementById("promptDialog");
    pDialog.addEventListener("click", function () {
        pDialog.style.display = "none";
    });

    //提示框确认按钮
    var pDialog2 = document.getElementById("promptDialog2");
    pDialog2.addEventListener("click", function () {
        pDialog2.style.display = "none";
    });

    //注册按钮
    var registerBtn = document.getElementById("registerButton");
    registerBtn.addEventListener("click", function () {
        registerBtn.setAttribute("class", "weui_btn weui_btn_disabled weui_btn_primary");
        $.post("${registerUrl}" + "/" + '${openId}', $("#dataForm").serialize(), function (data) {
            if ("success" == data) {
                WeixinJSBridge.call('closeWindow');
            } else if ("registered" == data) {
                window.location.href = "/biz/user/registered";
            } else {
                showPrompt("注册失败", data, "确认");
                registerBtn.setAttribute("class", "weui_btn weui_btn_primary");
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

    $(function () {
        $("#role").change(function () {
            var aliasLabel = ($("#role").val() == 0 ? "宝贝姓名" : "姓名");
            $("#alias_label").html(aliasLabel);
            var showIdentity = ($("#role").val() == 0);
            if (showIdentity) {
                $("#identity").show();
            } else {
                $("#identity").hide();
            }
        });

        /* $('#registerForm').validate({
            rules: {
              alias: {
                    required: true,
                      maxlength: 10
                }
            },
            messages: {
              alias: {
                    required: "请填写姓名",
                      maxlength: "姓名过长，请重新填写"
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo(element.parent().prev());
            },
            submitHandler: function (form) {
              form.submit();
            }
        }); */
    });
</script>
</body>
</html>