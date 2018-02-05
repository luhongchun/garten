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
  <title>账户管理</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart>
<c:url var="manageAccountActionUrl"
       value="/biz/setting/manage_accounts"/>
<div class="weui_panel">
  <!--  div class="weui_panel_hd">哈佛幼儿园</div -->
  <div class="weui_panel_bd">
    <div class="weui_media_box weui_media_small_appmsg">
      <div class="weui_cells weui_cells_form">
        <div class="weui_cell weui_cell_switch">
          <div class="weui_cell_hd weui_cell_primary">全部子账户</div>
          <img id="img_all"
               src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png"
               alt="" style="width: 28px; margin-right: 15px; display: block"/>
          <div class="weui_cell_ft">
            <input id="cb_all" class="weui_switch" type="checkbox"
                   onclick="checkStatusChange('cb_all', 'img_all')"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="weui_panel" style="margin-top: 30px;">
  <div class="weui_panel_bd">
    <div class="weui_media_box weui_media_small_appmsg">
      <c:if test="${not empty dataList}">
        <form name="manageAccountForm" id="manageAccountForm">
          <div class="weui_cells weui_cells_form">
            <c:forEach items="${dataList}" var="classAccount"
                       varStatus="status">
              <ul data-role="listview" data-inset="true"
                  style="margin-top: 10px;">
                <li data-role="list-divider">${classAccount.name}</li>
                <li>
                  <c:forEach items="${classAccount.childs}" var="child"
                             varStatus="status">
                    <div class="weui_cell weui_cell_switch">
                      <div
                          class="weui_cell_hd weui_cell_primary">${empty child.aliasName ? '未命名' : child.aliasName}</div>
                      <c:if test="${!child.close }">
                        <img id="img${child.id }"
                             src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png"
                             alt=""
                             style="width: 28px; margin-right: 15px; display: block"/>
                        <div class="weui_cell_ft">
                          <input id="cb${child.id }" name="childStatus"
                                 class="weui_switch" type="checkbox"
                                 value="${child.id }"
                                 onclick="checkStatusChange('cb${child.id }', 'img${child.id }')"
                                 checked="checked"/>
                        </div>
                      </c:if>
                      <c:if test="${child.close }">
                        <img id="img${child.id }"
                             src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png"
                             alt=""
                             style="width: 28px; margin-right: 15px; display: block"/>
                        <div class="weui_cell_ft">
                          <input id="cb${child.id }" name="childStatus"
                                 class="weui_switch" type="checkbox"
                                 value="${child.id }"
                                 onclick="checkStatusChange('cb${child.id }', 'img${child.id }')"/>
                        </div>
                      </c:if>
                      <input name="childIds" type="hidden"
                             value="${child.id }" form="manageAccountForm"/>
                    </div>
                  </c:forEach>
                </li>
              </ul>
            </c:forEach>
            <input name="openId" type="hidden" value="${openId}"
                   form="manageAccountForm"/>
          </div>
        </form>
      </c:if>
    </div>
  </div>
</div>

<div class="hd"/>

<div class="bd" style="width: 100%; position: fixed; bottom: 0;">
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
      <strong class="weui_dialog_title">提示</strong>
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
    function checkStatusChange(boxId, imgId) {
        var cbox = document.getElementById(boxId);
        var img = document.getElementById(imgId);

        if (cbox.id == "cb_all") {
            var src = new Array();
            var img = document.getElementsByTagName('img');
            //			var inp = document.getElementsByTagName('input');
            var inp = document.getElementsByClassName('weui_switch');
            for (var j in inp) {
                //alert(inp[j].id);
            }

            if (cbox.checked) {
                for (var i in img) {
                    if (img[i].id) {
                        //alert(i + "," + img[i].id);
                        var imgElement = document.getElementById(img[i].id);
                        imgElement.src = "http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png";
                        //alert(i + "," + inp[i].id);
                        var inpElement = document.getElementById(inp[i].id);
                        inpElement.checked = true;
                    }
                }

            } else {
                for (var i in img) {
                    if (img[i].id) {
                        //alert(i + "," + img[i].id);
                        var imgElement = document.getElementById(img[i].id);
                        imgElement.src = "http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png";
                        //alert(i + "," + inp[i].id);
                        var inpElement = document.getElementById(inp[i].id);
                        inpElement.checked = false;
                    }
                }
            }
        } else if (cbox.checked) {
            img.src = "http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png";
        } else {
            img.src = "http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png";
        }
    }

    function promptChange(ret) {
        var savea = document.getElementById('savea');
        savea.setAttribute("class", "weui_tabbar_item weui_bar_item_on");

        var promptDlg = document.getElementById('promptDialog');
        var dlgTitle = document.getElementById('dlgTitle');
        var dlgText = document.getElementById('dlgText');
        var centerBtn = document.getElementById('centerBtn');
        dlgTitle.innerHTML = "保存修改";
        dlgText.innerHTML = ret == "success" ? "修改成功" : "修改失败";
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
        $.post("${manageAccountActionUrl}",
            $("#manageAccountForm").serialize(), function (data) {
                promptChange(data);
            });
    }
</script>
</html>
