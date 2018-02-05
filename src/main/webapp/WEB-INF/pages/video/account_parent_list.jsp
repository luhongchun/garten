<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <script
      src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width,initial-scale=1,user-scalable=0">
  <title>用户直播管理</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background: #EFEEF3;">
<c:url var="manageAccountsActionUrl"
       value="/biz/setting/manage_accounts"/>
<div class="weui_panel weui_panel_access" style="margin-top: 20px;">

  <div class="weui_panel_bd">
    <a href="javascript:void(0);"
       class="weui_media_box weui_media_appmsg">
      <div class="weui_media_bd">
        <h4 class="weui_media_title">${className }家长管理</h4>
      </div>
      <span class="weui_cell_ft"></span>
    </a>
  </div>
</div>

<div class="hd" style="margin-top: 25px; margin-bottom: 40px;">
  <p
      style="text-align: right; font-size: 13px; margin-right: 33px; margin-bottom: -17px;">开放直播</p>
  <form name="manageAccountsForm" id="manageAccountsForm">
    <div class="weui_cells weui_cells_access" height="10%">
      <c:forEach items="${childAccounts }" var="child" varStatus="status">
        <c:if test="${!child.close }">
          <a class="weui_cell"
             href="/biz/setting/action_user_camera_setting/${openId}/${child.id}">
            <div class="weui_cell_bd weui_cell_primary">
              <p>${child.aliasName }</p>
            </div>
            <input id="cb${child.id }" name="accountStatus"
                   class="weui_switch" type="checkbox" value="${child.id }"
                   style="margin-right: 5px;" checked="checked"> </input>
            <div class="weui_cell_ft"></div>
          </a>
        </c:if>
        <c:if test="${child.close }">
          <a class="weui_cell"
             href="/biz/setting/action_user_camera_setting/${openId}/${child.id}">
            <div class="weui_cell_bd weui_cell_primary">
              <p>${child.aliasName }</p>
            </div>
            <input id="cb${child.id }" name="accountStatus"
                   class="weui_switch" type="checkbox" value="${child.id }"
                   style="margin-right: 5px;"> </input>
            <div class="weui_cell_ft"></div>
          </a>
        </c:if>
        <input name="accountIds" type="hidden" value="${child.id }"
               form="manageAccountsForm"/>
      </c:forEach>
      <input name="openId" type="hidden" value="${openId}"
             form="manageAccountsForm"/>
    </div>
  </form>
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
        location.reload();
    });

    function save() {
        $.post("${manageAccountsActionUrl}", $("#manageAccountsForm")
            .serialize(), function (data) {
            promptChange(data);
        });
    }
</script>
</html>