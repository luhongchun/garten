<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <script
      src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width,initial-scale=1,user-scalable=0">
  <title>直播区域设置</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background: #EFEEF3;">
<c:url var="manageCameraActionUrl"
       value="/biz/setting/edit_account_cameras"/>
<div class="weui_panel weui_panel_access" style="margin-top: 20px;">

  <div class="weui_panel_bd">
    <a href="javascript:void(0);"
       class="weui_media_box weui_media_appmsg">
      <div class="weui_media_bd">
        <h4 class="weui_media_title">${child.aliasName}</h4>
        <p class="weui_media_desc">已分配：${cameras.size()}个区域</p>
      </div>
      <span class="weui_cell_ft"></span>
    </a>
  </div>
</div>

<div class="bd" style="margin-top: 25px;">
  <p style="text-align: right; font-size: 13px; margin-right: 33px; margin-bottom: -17px;">开放直播</p>
  <div class="weui_cells weui_cells_checkbox">
    <c:if test="${not empty cameras}">
      <form name="manageForm" id="manageForm">
        <c:forEach items="${parents }" var="parent" varStatus="status">
          <a class="weui_cell" href="javascript:;">
            <div class="weui_cell_bd weui_cell_primary">
              <p>${parent.aliasName}</p>
            </div>
            <input id="cb${parent.id }" name="accountStatus"
                   class="weui_switch" type="checkbox" value="${parent.id }"
                   style="margin-right: 5px;" <c:if test="${!parent.close }">checked="checked"</c:if>>
            <div class="weui_cell_ft"></div>
          </a>
          <input name="accountIds" type="hidden" value="${parent.id }"
                 form="manageForm"/>
        </c:forEach>
        <c:forEach items="${cameras}" var="camera" varStatus="status">
          <c:if test="${!camera.available }">
            <label class="weui_cell weui_check_label" for="cb${camera.id }">
              <div class="weui_cell_bd weui_cell_primary">
                <p>${empty camera.name ? '未命名' : camera.name}</p>
              </div>
              <div class="weui_cell_hd">
                <input type="checkbox" class="weui_check" name="cameraStatus"
                       value="${camera.id }" id="cb${camera.id }">
                <i class="weui_icon_checked"></i>
              </div>

            </label>
          </c:if>
          <c:if test="${camera.available }">
            <label class="weui_cell weui_check_label" for="cb${camera.id }">
              <div class="weui_cell_bd weui_cell_primary">
                <p>${empty camera.name ? '未命名' : camera.name}</p>
              </div>
              <div class="weui_cell_hd">
                <input type="checkbox" class="weui_check" name="cameraStatus"
                       value="${camera.id }" id="cb${camera.id }" checked="checked">
                <i class="weui_icon_checked"></i>
              </div>

            </label>
          </c:if>
          <input name="cameraIds" type="hidden" value="${camera.id }"
                 form="manageForm"/>
        </c:forEach>
        <input name="childId" type="hidden" value="${child.id}"
               form="manageForm"/>
      </form>
    </c:if>
  </div>
</div>

<div style="margin-top: 40px;">
  <p style="color: #EFEEF3;">____</p>
</div>

<div class="bd" style="width: 100%; position: fixed; bottom: 0;"
     style="margin-top:20px;">
  <div class="weui_tab">
    <div class="weui_tabbar">
      <a id="savea" href="javascript:;" class="weui_tabbar_item"
         onclick="save()">
        <div class="weui_tabbar_icon">
          <img
              src="http://static-10001988.cos.myqcloud.com/resource/images/icons/save.svg"
              alt="" style="width: 24px;"/>
        </div>
        <p class="weui_tabbar_label">保存修改</p>
      </a>
    </div>
  </div>
</div>

<!--BEGIN promptDialog-->
<div class="weui_dialog_confirm" id="promptDialog"
     style="display: none;">
  <div class="weui_mask"></div>
  <div class="weui_dialog">
    <div id="dlgTitle" class="weui_dialog_hd">
      <strong class="weui_dialog_title">注册提示</strong>
    </div>
    <div class="weui_dialog_bd">
      <p id="dlgText" align="center"></p>
    </div>
    <div class="weui_dialog_ft">
      <a id="centerBtn" href="javascript:;"
         class="weui_btn_dialog primary">确定</a>
    </div>
  </div>
</div>
<!--END promptDialog-->
</body>

<%@ include file="/WEB-INF/includes/piwik.jsp" %>

<script type="text/javascript">
    function promptChange(ret) {
        var savea = document.getElementById('savea');
        savea.setAttribute("class", "weui_tabbar_item weui_bar_item_on");

        var promptDlg = document.getElementById('promptDialog');
        var dlgTitle = document.getElementById('dlgTitle');
        var dlgText = document.getElementById('dlgText');
        var centerBtn = document.getElementById('centerBtn');
        dlgTitle.innerHTML = ret == "success" ? "修改成功" : "修改失败";
        centerBtn.innerHTML = "确认";
        promptDlg.style.display = "block";
    }

    //提示框确认按钮
    var pDialog = document.getElementById("promptDialog");
    pDialog.addEventListener("click", function () {
        pDialog.style.display = "none";
        var savea = document.getElementById('savea');
        savea.setAttribute("class", "weui_tabbar_item");
        //location.reload();
        manageAccountChildren();
    });

    function save() {
        $.post("${manageCameraActionUrl}", $("#manageForm").serialize(),
            function (data) {
                promptChange(data);
            });
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

    function manageAccountChildren() {
        new submitForm('${ctx}/biz/setting/action_user_account_children', {
            openId: '${openId}',
            classId: '${classId}'
        }).post();
    }
</script>
</html>