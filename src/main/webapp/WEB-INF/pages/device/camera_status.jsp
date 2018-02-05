<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>摄像机状态</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:if test="${not empty reportMessage}">
      <c:url var="restartCameraUrl" value="/device/manage/restart_camera"/>
      <c:url var="cameraSetNameUrl" value="/device/manage/camera_setup_edit/${reportMessage.cameraId}"/>
      <c:url var="cameraPersonInfoUrl" value="/device/manage/camera_person/${reportMessage.cameraId}"/>
      <ul data-role="listview" data-inset="true">
        <li>
          <a data-ajax="false" href="#" id="restart">
            摄像机状态&nbsp;&nbsp;&nbsp;&nbsp;点击重启
            <span class="ui-li-count">
              <fmt:formatDate value="${reportMessage.reportTime}" pattern="M月d日 HH:mm:ss"/>
            </span>
          </a>
        </li>
        <li>摄像机状态
          <c:choose>
            <c:when test="${reportMessage.online eq true}"><span class="ui-li-count">在线</span></c:when>
            <c:otherwise><span class="ui-li-count" style="color:red">离线</span></c:otherwise>
          </c:choose>
        </li>
        <li>摄像机名称<span class="ui-li-count">${empty reportMessage.name ? '未设置' : reportMessage.name}</span></li>
        <li>IP地址<span class="ui-li-count">${empty reportMessage.ip ? '未知' : reportMessage.ip}</span></li>
        <li>MAC地址<span class="ui-li-count">${empty reportMessage.mac ? '未知' : reportMessage.mac}</span></li>
        <li>序列号<span class="ui-li-count">${empty reportMessage.sn ? '未知' : reportMessage.sn}</span></li>
        <li><a data-ajax="false" href="${cameraSetNameUrl}">设置摄像机</a></li>
        <li><a data-ajax="false" href="${cameraPersonInfoUrl}">查看人脸识别统计数据</a></li>
        <c:forEach items="${reportMessage.personVideo}" var="pv" varStatus="status">
          <li>人：${pv.id }<span class="ui-li-count">视频数：${pv.cnt }</span></li>
        </c:forEach>
      </ul>
    </c:if>
    <c:if test="${empty reportMessage}">
      <div><span>还没有侦查的摄像机的信息，摄像机隐蔽性太好了，通电通网，暴露下摄像机的信息吧</span></div>
    </c:if>
  </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#restart").click(function () {
            $.ajax({
                type: "POST",
                url: '${restartCameraUrl}',
                data: 'cameraSn=' + '${reportMessage.sn}',
                success: function () {
                    alert("重启请求发送成功！");
                }
            });
        });
    });
</script>
</body>
</html>
