<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <script src="//static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>萌宝日记</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background:#EFEEF3;">
<c:url var="defaultImg"
       value="http://static-10001988.cos.myqcloud.com/resource/images/common/cover_live_news.jpeg"/>
<div class="bd" style="height:98%;">
  <div class="weui_tab">
    <div class="weui_tab_bd">
      <c:forEach items="${videoList}" var="video" varStatus="status">
        <div class="weui_cells weui_cells_access">
          <div class="weui_cell">
            <p align="center">
              <a href="javascript:;" onclick="playVideo('${video.childId}', '${video.videoId}', '${video.cameraId}')">
                <img src="${video.coverUrl }" onerror="this.src='${defaultImg }'" width="100%"/>
              </a>
            </p>
          </div>
          <a class="weui_cell" href="javascript:;">
            <div class="weui_cell_bd weui_cell_primary">
              <p>${video.videoDescShare }</p>
            </div>
          </a>
        </div>
      </c:forEach>
    </div>
  </div>
</div>

<div style="margin-top:40px;">
  <p style="color:#EFEEF3;">____</p>
</div>

<c:if test="${live and find }">
  <div class="weui_tabbar" style="width:100%; position:fixed;bottom:0;" style="margin-top:20px;">
    <a class="weui_tabbar_item" onclick="live()">
      <div class="weui_tabbar_icon">
        <img src="//static-10001988.cos.myqcloud.com/resource/images/icons/live_off.svg" alt="">
      </div>
      <p class="weui_tabbar_label">直播</p>
    </a>
    <a id="findBtn" class="weui_tabbar_item weui_bar_item_on">
      <div class="weui_tabbar_icon">
        <img src="//static-10001988.cos.myqcloud.com/resource/images/icons/find_on.svg" alt="">
      </div>
      <p class="weui_tabbar_label">发现</p>
    </a>
  </div>
</c:if>

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

    function live() {
        new submitForm('${ctx}/biz/carekids/action_user_camera_list', {openId: '${openId}'}).post();
    }

    function playVideo(childId, videoId, cameraId) {
        //new submitForm('${ctx}/biz/carekids/action_video_play_vod', { videoId: videoId}).post();
        window.location.href = "/biz/carekids/action_video_play_vod/${instanceId}/" + childId + "/" + videoId + "/" + cameraId;
    }
</script>
</html>