<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>摄像机列表</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <ul data-role="listview" data-inset="true">
      <li>选择要看的摄像头</li>
      <s:iterator value="#request.cameraList" id="camera" status="status">
        <c:url
            value="/wx/video_record.action?cameraId=#camera.id&openId=#request.openId"
            var="statusUrl">
          <c:param name="cameraId" value="#camera.id"/>
          <c:param name="openId" value="#request.openId"/>
          <c:param name="cameraName" value="#request.name"/>
        </c:url>
        <li><a data-ajax="false"
               onclick="videoRecord('${openId}', '${camera.id }')">摄像机<span
            class="ui-li-count">${camera.name }</span></a></li>
      </s:iterator>
    </ul>
  </div>
</div>
<script type="text/javascript">
    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }

    function videoRecord(openId, cameraId) {
        $.ajax({
            type: "POST",
            url: "${ctx}/wx/video_record.action?",
            data: "cameraId=" + cameraId + "&openId=" + openId,
            success: function (msg) {
                if (msg == 'success') {
                    if (closeWindow && typeof closeWindow == 'function') {
                        closeWindow();
                    }
                } else {
                    alert('对不起，微拍失败');
                }
            }
        });
    }
</script>
</body>
</html>