<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
</head>

<body>
<div data-role="page">
  <div data-role="header">
  </div>
  <div data-role="main" class="ui-content jqm-content">
    <form id="setup_form">
      <input type="hidden" name="cameraId" id="cameraId" value="${camera.id }">
      <div>
        <h4>摄像机名称</h4>
      </div>
      <div>
        <input type="text" name="cameraName" id="cameraName" value="${camera.name }">
      </div>
      <div>
        <h4>摄像机顺序</h4>
      </div>
      <div>
        <input type="text" name="cameraSorted" id="cameraSorted" value="${camera.sorted }">
      </div>
      <div>
        <h4>是否信任该摄像头</h4>
      </div>
      <div>
        <select name="trustedEnabled" id="trustedEnabled" data-role="slider">
          <option value="0"
          ${camera.trusted == false ? 'selected="selected"' : ''}></option>
          <option value="1"
          ${camera.trusted == true ? 'selected="selected"' : ''}></option>
        </select>
      </div>
      <div>
        <h4>是否使用固定IP</h4>
      </div>
      <div>
        <select name="fixedEnabled" id="fixedEnabled" data-role="slider">
          <option value="0"
          ${request.camera.fixed == false ? 'selected="selected"' : ''}></option>
          <option value="1"
          ${request.camera.fixed == true ? 'selected="selected"' : ''}></option>
        </select>
      </div>
      <input type="button" id="setup" value="保存">
    </form>
  </div>
  <script type="text/javascript">
      $(function () {
          $('#setup').click(function () {
              $('#setup').attr('disabled', "true");
              $.ajax({
                  type: "POST",
                  url: "${ctx}/device/manage/camera_setup.action?",
                  data: $('#setup_form').serialize(),
                  success: function (msg) {
                      if (msg == 'success') {
                          // $('#setup_form')[0].reset();
                          alert("保存成功");
                          window.location.href = '${ctx}/device/manage/camera_status/${camera.id }';
                      } else {
                          $('#setup').attr('disabled', "false");
                          alert("保存失败");
                      }
                  }
              });
          });
      });
  </script>
</div>
</body>
</html>
