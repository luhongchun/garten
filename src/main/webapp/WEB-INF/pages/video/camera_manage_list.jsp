<%@ page import="cn.smart.cloud.biz.opadmin.constant.Constant.CameraServiceType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>直播开放设置</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background:#EFEEF3;">
<c:url var="manageCameraActionUrl" value="/biz/setting/manage_cameras"/>
<div class="weui_panel">
  <!--  div class="weui_panel_hd">哈佛幼儿园</div -->
  <div class="weui_panel_bd">
    <div class="weui_media_box weui_media_small_appmsg">
      <div class="weui_cells weui_cells_form">
        <c:if test="${allOpen }">
          <div class="weui_cell weui_cell_switch">
            <div class="weui_cell_hd weui_cell_primary">全部区域</div>
            <img id="img_all"
                 src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png"
                 alt="" style="width: 28px; margin-right: 15px; display: block"/>
            <div class="weui_cell_ft">
              <input id="cb_all" class="weui_switch" type="checkbox"
                     onclick="checkStatusChange('cb_all', 'img_all')"
                     checked="checked"/>
            </div>
          </div>
        </c:if>
        <c:if test="${!allOpen }">
          <div class="weui_cell weui_cell_switch">
            <div class="weui_cell_hd weui_cell_primary">全部区域</div>
            <img id="img_all"
                 src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png"
                 alt="" style="width: 28px; margin-right: 15px; display: block"/>
            <div class="weui_cell_ft">
              <input id="cb_all" class="weui_switch" type="checkbox"
                     onclick="checkStatusChange('cb_all', 'img_all')"/>
            </div>
          </div>
        </c:if>
      </div>
    </div>
  </div>
</div>

<div class="weui_panel" style="margin-top: 30px;">
  <div class="weui_panel_bd">
    <div class="weui_media_box weui_media_small_appmsg">
      <c:if test="${not empty cameras}">
        <form name="manageCameraForm" id="manageCameraForm">
          <div class="weui_cells weui_cells_form">
            <c:forEach items="${cameras}" var="camera" varStatus="status">
              <c:set var="inService"
                     value="<%=CameraServiceType.SERVICE%>"/>
              <c:if test="${inService == camera.serviceType}">
                <div class="weui_cell weui_cell_switch">
                  <div class="weui_cell_hd weui_cell_primary">${empty camera.name ? '未命名' : camera.name}</div>
                  <c:if test="${!camera.close }">
                    <img id="img${camera.id }"
                         src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png"
                         alt=""
                         style="width: 28px; margin-right: 15px; display: block"/>
                    <div class="weui_cell_ft">
                      <input id="cb${camera.id }" name="cameraStatus"
                             class="weui_switch" type="checkbox" value="${camera.id }"
                             onclick="checkStatusChange('cb${camera.id }', 'img${camera.id }')"
                             checked="checked"/>
                    </div>
                  </c:if>
                  <c:if test="${camera.close }">
                    <img id="img${camera.id }"
                         src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png"
                         alt=""
                         style="width: 28px; margin-right: 15px; display: block"/>
                    <div class="weui_cell_ft">
                      <input id="cb${camera.id }" name="cameraStatus"
                             class="weui_switch" type="checkbox" value="${camera.id }"
                             onclick="checkStatusChange('cb${camera.id }', 'img${camera.id }')"/>
                    </div>
                  </c:if>
                  <input name="cameraIds" type="hidden" value="${camera.id }" form="manageCameraForm"/>
                </div>
              </c:if>
            </c:forEach>
            <input name="openId" type="hidden" value="${openId}"
                   form="manageCameraForm"/>
          </div>
        </form>
      </c:if>
    </div>
  </div>
</div>

<div style="margin-top:40px;">
  <p style="color:#EFEEF3;">____</p>
</div>

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
        if ('${gartenId}' == null || '${gartenId}' == "") {
            location.reload();
        } else {
            mc();
        }
    });

    function save() {
        $.post("${manageCameraActionUrl}", $("#manageCameraForm").serialize(),
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

    function mc() {
        new submitForm('${ctx}/garten/manage/action_camera_setting', {gartenId: '${gartenId}'}).post();
    }
</script>
</html>
