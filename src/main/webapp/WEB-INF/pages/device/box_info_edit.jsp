<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>盒子信息编辑</title>
</head>

<body>
<div data-role="page">
  <div data-role="header">
  </div>
  <div data-role="main" class="ui-content jqm-content">
    <form id="setup_form">
      <input type="hidden" name="boxId" id="boxId" value="${box.id }">

      <div>
        <h4>盒子名称</h4>
      </div>
      <div>
        <input type="text" name="name" id="boxName" value="${box.name }">
      </div>
      <div>
        <h4>盒子描述</h4>
      </div>
      <div>
        <input type="text" name="description" id="description" value="${box.description }">
      </div>
      <div>
        <h4>绑定GlobalID</h4>
      </div>
      <div>
        <input type="text" name="globalId" id="globalId" value="${maintainerGlobalId }">
      </div>
      <div>
        <h4>是否开启报警</h4>
      </div>
      <div>
        <select name="alarmEnabled" id="alarmEnabled" data-role="slider">
          <option value="0"
          ${boxConfig.alarmEnabled == false ? 'selected="selected"' : ''}></option>
          <option value="1"
          ${boxConfig.alarmEnabled == true ? 'selected="selected"' : ''}></option>
        </select>
      </div>
      <div>
        <h4>是否开启信任列表</h4>
      </div>
      <div>
        <select name="trustedListEnabled" id="trustedListEnabled" data-role="slider">
          <option value="0"
          ${boxConfig.trustedListEnabled == false ? 'selected="selected"' : ''}></option>
          <option value="1"
          ${boxConfig.trustedListEnabled == true ? 'selected="selected"' : ''}></option>
        </select>
      </div>
      <input type="button" id="setup" value="保存">
    </form>
    <form name="cameraConfigForm" id="cameraConfigForm">
      <div class="ui-content jqm-content" data-role="collapsible" data-iconpos="right" data-inset="true">
        <h4>调整摄像头配置并保存到数据库</h4>
        <input name="boxId" type="hidden" value="${box.id}" form="cameraConfigForm">
        <label for="inVolume">输入音量 (1~100)</label>
        <input type="range" name="inVolume" id="inVolume" form="cameraConfigForm"
               value="${cConfig.involume }" min="1" max="100">
        <h4>码率</h4>
        <input type="text" name="bps" id="bps" value="${cConfig.bps }">
        <h4>帧率</h4>
        <input type="text" name="fps" id="fps" value="${cConfig.fps }">
        <h4>主帧间隔</h4>
        <input type="text" name="gop" id="gop" value="${cConfig.gop }">
        <h4>固定码率</h4>
        <select name="cbrmode" id="cbrmode" data-role="slider">
          <option value="0"
          ${cConfig.cbrmode == false ? 'selected="selected"' : ''}></option>
          <option value="1"
          ${cConfig.cbrmode == true ? 'selected="selected"' : ''}></option>
        </select>
        <label for="imagegrade">画面质量 (1~6)</label>
        <input type="range" name="imagegrade" id="imagegrade" form="cameraConfigForm"
               value="${cConfig.imagegrade }" min="1" max="6">

        <input type="submit" form="cameraConfigForm" class="ui-btn" value="确认保存">
      </div>
    </form>
    <ul data-role="listview" data-inset="true">
      <li>
        <a data-ajax="false" href="#" id="adjustVolume">按数据库内配置远程调节所有上报摄像头的输入音量
          <c:url var="adjustVolumeActionUrl" value="/device/manage/adjust_volume.action">
          </c:url>
        </a>
      </li>
      <li>
        <a data-ajax="false" href="#" id="adjustVideoParams">按数据库内配置远程调节所有上报摄像头的视频参数
          <c:url var="adjustVideoParamsActionUrl" value="/device/manage/adjust_video_params.action">
          </c:url>
        </a>
      </li>
      <li>
        <a data-ajax="false" href="#" id="genRebootAppCmd">生成重启程序的命令
          <c:url var="genRebootAppCmdActionUrl" value="/device/manage/gen_reboot_app_cmd.action">
          </c:url>
        </a>
      </li>
      <li>
        <a data-ajax="false" href="#" id="genRebootBoxCmd">生成重启盒子的命令
          <c:url var="genRebootBoxCmdActionUrl" value="/device/manage/gen_reboot_box_cmd.action">
          </c:url>
        </a>
      </li>
    </ul>
  </div>
  <script type="text/javascript">
      $(function () {
          $('#setup').click(function () {
              $('#setup').attr('disabled', "true");
              $.ajax({
                  type: "POST",
                  url: "${ctx}/device/manage/box_info_edit_submit.action?",
                  data: $('#setup_form').serialize(),
                  success: function (msg) {
                      if (msg == 'success') {
                          // $('#setup_form')[0].reset();
                          alert("保存成功");
                          window.location.href = '${ctx}/device/manage/box_status/${box.id }';
                      } else {
                          $('#setup').attr('disabled', "false");
                          alert("保存失败");
                      }
                  }
              });
          });

          $("#cameraConfigForm").submit(
              function () {
                  showLoader();
                  $.post("${ctx}/device/manage/adjust_camera_config.action?", $("#cameraConfigForm").serialize(),
                      function (data) {
                          hiderLoader();
                          window.location.href = '${ctx}/device/manage/box_status/${box.id }';
                      }
                  );
                  return false;
              }
          );

          $("#adjustVolume").click(function () {
              $.ajax({
                  type: "POST",
                  url: '${adjustVolumeActionUrl}',
                  data: 'boxSn=' + '${box.sn}',
                  success: function () {
                      alert("摄像头输入音量设置完成！");
                  }
              });
          });

          $("#adjustVideoParams").click(function () {
              $.ajax({
                  type: "POST",
                  url: '${adjustVideoParamsActionUrl}',
                  data: 'boxSn=' + '${box.sn}',
                  success: function () {
                      alert("摄像头视频参数调整请求已发送！");
                  }
              });
          });

          $("#genRebootAppCmd").click(function () {
              $.ajax({
                  type: "POST",
                  url: '${genRebootAppCmdActionUrl}',
                  data: 'boxSn=' + '${box.sn}',
                  success: function () {
                      alert("已生成程序重启命令！");
                  }
              });
          });

          $("#genRebootBoxCmd").click(function () {
              $.ajax({
                  type: "POST",
                  url: '${genRebootBoxCmdActionUrl}',
                  data: 'boxSn=' + '${box.sn}',
                  success: function () {
                      alert("已生成盒子重启命令！");
                  }
              });
          });


          function showLoader() {
              var $this = $(this),
                  theme = $this.jqmData("theme") || $.mobile.loader.prototype.options.theme,
                  msgText = $this.jqmData("msgtext") || $.mobile.loader.prototype.options.text,
                  textVisible = $this.jqmData("textvisible") || $.mobile.loader.prototype.options.textVisible,
                  textonly = !!$this.jqmData("textonly");
              html = $this.jqmData("html") || "";
              $.mobile.loading("show", {
                  text: msgText,
                  textVisible: textVisible,
                  theme: theme,
                  textonly: textonly,
                  html: html
              });
          }

          function hiderLoader() {
              $.mobile.loading("hide");
          }
      });
  </script>
</div>
</body>
</html>
