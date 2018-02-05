<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>盒子状态</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:if test="${not empty reportMessage}">
      <div>
        <ul data-role="listview" data-inset="true">
          <li>
            <a data-ajax="false" href="#" id="restart">
                ${empty reportMessage.boxMessage.name? '未命名的盒子' : reportMessage.boxMessage.name} &nbsp;&nbsp;&nbsp;&nbsp;点击重启设备-慎重使用
              <span class="ui-li-count"><fmt:formatDate
                  value="${reportMessage.boxMessage.reportTime}" pattern="M月d日 HH:mm:ss"/></span>
              <c:url var="restartBoxUrl" value="/device/manage/restart_box">
              </c:url>
            </a>
          </li>
          <li>
            <a data-ajax="false" href="#" id="rebootWecare">点击重新启动WeCare
              <c:url var="rebootWecareUrl" value="/device/manage/reboot_wecare">
              </c:url>
            </a>
          </li>
          <li>
            <a data-ajax="false" href="#" id="refreshConfig">点击重新获取盒子参数
              <c:url var="refreshBoxConfig" value="/device/manage/refresh_box_config">
              </c:url>
            </a>
          </li>
          <li>
            <a data-ajax="false" href="#" id="rescanCamera">点击重新搜索摄像头
              <c:url var="rescanCamera" value="/device/manage/rescan_camera">
              </c:url>
            </a>
          </li>
          <li>盒子状态
            <c:choose>
              <c:when test="${reportMessage.boxMessage.online eq true}"><span class="ui-li-count">在线</span></c:when>
              <c:otherwise><span class="ui-li-count" style="color:red">离线</span></c:otherwise>
            </c:choose>
          </li>
          <li>IP地址<span
              class="ui-li-count">${empty reportMessage.boxMessage.ip ? '未知' : reportMessage.boxMessage.ip}</span></li>
          <li>MAC地址<span
              class="ui-li-count">${empty reportMessage.boxMessage.mac ? '未知' : reportMessage.boxMessage.mac}</span>
          </li>
          <li>序列号<span
              class="ui-li-count">${empty reportMessage.boxMessage.sn ? '未知' : reportMessage.boxMessage.sn}</span></li>
          <li>版本号<span
              class="ui-li-count">${empty reportMessage.boxMessage.versionCode ? '未知' : reportMessage.boxMessage.versionCode}</span>
          </li>
          <li>升级时间
            <span class="ui-li-count"><fmt:formatDate
                value="${reportMessage.boxMessage.upgradeTime}" pattern="M月d日 HH:mm:ss"/></span>
          </li>
          <li>上传速度
            <c:if test="${not empty reportMessage.boxMessage.uploadRate }">
              <span class="ui-li-count">${reportMessage.boxMessage.uploadRate} kb/s</span>
            </c:if>
            <c:if test="${empty reportMessage.boxMessage.uploadRate }"><span class="ui-li-count">未知</span></c:if>
          </li>
          <li>单路录制速度
            <c:if test="${not empty reportMessage.boxMessage.recordRateOneCam }">
              <span class="ui-li-count">${reportMessage.boxMessage.recordRateOneCam} kb/s</span>
            </c:if>
            <c:if test="${empty reportMessage.boxMessage.recordRateOneCam }"><span class="ui-li-count">未知</span></c:if>
          </li>
          <li>cpu状态
            <c:if test="${not empty reportMessage.boxMessage.cpuUsage }">
              <span class="ui-li-count">${reportMessage.boxMessage.cpuUsage}</span>
            </c:if>
            <c:if test="${empty reportMessage.boxMessage.cpuUsage }"><span class="ui-li-count">未知</span></c:if>
          </li>
          <li>内存状态
            <c:if test="${not empty reportMessage.boxMessage.memFree }">
              <span class="ui-li-count">${reportMessage.boxMessage.memFree}</span>
            </c:if>
            <c:if test="${empty reportMessage.boxMessage.memFree }"><span class="ui-li-count">未知</span></c:if>
          </li>
          <li>到路由延时
            <c:if test="${not empty reportMessage.boxMessage.delayR }">
              <c:choose>
                <c:when test="${reportMessage.boxMessage.delayR >500}">
                  <span class="ui-li-count" style="color:red">${reportMessage.boxMessage.delayR}毫秒</span>
                </c:when>
                <c:otherwise><span class="ui-li-count">${reportMessage.boxMessage.delayR}毫秒</span></c:otherwise>
              </c:choose>
            </c:if>
            <c:if test="${empty reportMessage.boxMessage.delayR }"><span class="ui-li-count">未知</span></c:if>
          </li>
          <li>到服务器延时
            <c:if test="${not empty reportMessage.boxMessage.delayS}">
              <c:choose>
                <c:when test="${reportMessage.boxMessage.delayS > 500}">
                  <span class="ui-li-count" style="color:red">${reportMessage.boxMessage.delayS}毫秒</span>
                </c:when>
                <c:otherwise><span class="ui-li-count">${reportMessage.boxMessage.delayS}毫秒</span></c:otherwise>
              </c:choose>
            </c:if>
            <c:if test="${empty reportMessage.boxMessage.delayS}"><span class="ui-li-count">未知</span></c:if>
          </li>
          <c:url var="boxInfoEditUrl" value="/device/manage/box_info_edit">
            <c:param name="boxId" value="${boxId}"/>
          </c:url>
          <li><a data-ajax="false" href="${boxInfoEditUrl}">编辑盒子信息</a></li>
          <c:url var="showAllCamerasVideo" value="/device/manage/show_all_cameras_video">
            <c:param name="boxId" value="${boxId}"/>
          </c:url>
          <li><a data-ajax="false" href="#" id="snap">想TA们了</a></li>
        </ul>
        <ul data-role="listview" data-inset="true">
          <li>下属摄像机</li>
          <c:if test="${not empty reportMessage.cameraMessageList}">
            <c:forEach items="${reportMessage.cameraMessageList}" var="cameraMessage" varStatus="status">
              <c:url value="/device/manage/camera_status/${cameraMessage.cameraId}" var="statusUrl">
              </c:url>
              <li><a data-ajax="false"
                     href="${statusUrl}">摄像头${empty cameraMessage.name ? status.count : cameraMessage.name}
                <c:choose>
                  <c:when test="${cameraMessage.online eq true}">
                    <span
                        class="ui-li-count">在线 ${cameraMessage.recordStatus} ${cameraMessage.recordWeight} ${cameraMessage.videoStatistics}</span>
                  </c:when>
                  <c:otherwise><span class="ui-li-count" style="color:red">
            		  离线 ${cameraMessage.recordStatus} ${cameraMessage.recordWeight} ${cameraMessage.videoStatistics}</span>
                  </c:otherwise>
                </c:choose>
              </a></li>
            </c:forEach>
          </c:if>
        </ul>
      </div>
    </c:if>
    <c:if test="${empty reportMessage}">
      <div><span>还没有侦查的盒子的信息，盒子隐蔽性太好了，通电通网，暴露下盒子的信息吧</span></div>
    </c:if>
  </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#restart").click(function () {
            $.ajax({
                type: "POST",
                url: '${restartBoxUrl}',
                data: 'boxSn=' + '${reportMessage.boxMessage.sn}',
                success: function () {
                    alert("重启设备的请求发送成功！");
                }
            });
        });
        $("#rebootWecare").click(function () {
            $.ajax({
                type: "POST",
                url: '${rebootWecareUrl}',
                data: 'boxSn=' + '${reportMessage.boxMessage.sn}',
                success: function () {
                    alert("重启程序的请求发送成功！");
                }
            });
        });
        $("#refreshConfig").click(function () {
            $.ajax({
                type: "POST",
                url: '${refreshBoxConfig}',
                data: 'boxSn=' + '${reportMessage.boxMessage.sn}',
                success: function () {
                    alert("重新获取盒子参数的请求发送成功！");
                }
            });
        });
        $("#rescanCamera").click(function () {
            $.ajax({
                type: "POST",
                url: '${rescanCamera}',
                data: 'boxSn=' + '${reportMessage.boxMessage.sn}',
                success: function () {
                    alert("重新扫描摄像头的请求发送成功！");
                }
            });
        });
        $("#snap").click(function () {
            $.ajax({
                type: "POST",
                url: '${showAllCamerasVideo}',
                data: 'boxId=${boxId}',
                success: function (data) {
                    if (data == "success") {
                        alert("抓取成功！");
                    } else if (data == "none") {
                        alert("抓取失败！");
                    }
                }
            });
        });
    });
</script>
</body>
</html>
