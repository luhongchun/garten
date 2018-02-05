<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <script
      src="//static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta name="viewport"
        content="width=device-width,initial-scale=1,user-scalable=0">
  <title>精彩记录</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background: #EFEEF3;">
<c:url var="defaultImg" value="http://static-10001988.cos.myqcloud.com/resource/images/common/cover_live_news.jpeg"/>
<div class="weui_cells">
  <div class="weui_cell">
    <c:if test="${videoOneday.today}">
      <div class="weui_cell_bd weui_cell_primary">
        <p>今日精彩</p>
      </div>
    </c:if>
    <c:if test="${!videoOneday.today}">
      <div class="weui_cell_bd weui_cell_primary">
        <p>${videoOneday.dateString }</p>
      </div>
    </c:if>
  </div>
  <div class="weui_cell">
    <table border="0" width="100%">
      <c:forEach items="${videoOneday.all}" var="sVideo" varStatus=" ">
        <c:if test="${status.first}">
          <tr>
        </c:if>
        <td style="width:50%">
          <a onclick="playVideo('${sVideo.cameraId}', '${sVideo.videoUrl }')"><img src="${sVideo.coverUrl }"
                                                                                   onerror="this.src='${defaultImg }'"
                                                                                   width="99%"
                                                                                   style="margin-left:2px;"/></a>
        </td>
        <c:if test="${not status.first and not status.last and status.index%2==1}">
          </tr>
          <tr>
        </c:if>
        <c:if test="${status.last}">
          </tr>
        </c:if>
      </c:forEach>
    </table>
  </div>

</div>

<div style="margin-top: 40px;">
  <p style="color: #EFEEF3;">____</p>
</div>

</body>

<script type="text/javascript">
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

    function playVideo(cameraId, videoUrl) {
        new submitForm('${ctx}/biz/carekids/action_video_play', {
            instanceId: '${instanceId}',
            openId: '${openId}',
            cameraId: cameraId,
            videoUrl: videoUrl
        }).post();
    }
</script>
</html>