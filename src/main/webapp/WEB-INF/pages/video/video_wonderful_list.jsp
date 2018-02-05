<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <script src="//static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>精彩发现</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background:#EFEEF3;">
<c:url var="defaultImg"
       value="http://static-10001988.cos.myqcloud.com/resource/images/common/cover_live_news.jpeg"/>
<c:forEach items="${videoAllday}" var="videoList" varStatus="status">
  <div class="weui_cells weui_cells_access">
    <c:if test="${not empty videoList and videoList.all.size() > 5}">
      <a class="weui_cell" href="javascript:;" onclick="videoOneday('${videoList.date}')">
        <c:if test="${videoList.today}">
          <div class="weui_cell_bd weui_cell_primary">
            <p>今日精彩</p>
          </div>
          <div class="weui_cell_ft">更多发现</div>
        </c:if>
        <c:if test="${!videoList.today}">
          <div class="weui_cell_bd weui_cell_primary">
            <p>${videoList.dateString }</p>
          </div>
          <div class="weui_cell_ft">更多记录</div>
        </c:if>
      </a>
    </c:if>
    <c:if test="${not empty videoList and videoList.all.size() <= 5}">
      <a class="weui_cell">
        <c:if test="${videoList.today}">
          <div class="weui_cell_bd weui_cell_primary">
            <p>今日精彩</p>
          </div>
        </c:if>
        <c:if test="${!videoList.today}">
          <div class="weui_cell_bd weui_cell_primary">
            <p>${videoList.dateString }</p>
          </div>
        </c:if>
      </a>
    </c:if>

    <div class="weui_cell">
      <a onclick="playVideo('${videoList.all[0].cameraId}', '${videoList.all[0].videoUrl }')">
        <img src="${videoList.all[0].coverUrl }" onerror="this.src='${defaultImg }'" width="99%"
             style="margin-left:2px;"/>
      </a>
    </div>
    <c:if test="${videoList.all.size() > 1}">
      <div class="weui_cell">
        <table border="0" width="100%">
          <tr>
            <td>
              <a onclick="playVideo('${videoList.all[1].cameraId}', '${videoList.all[1].videoUrl }')"><img
                  src="${videoList.all[1].coverUrl }" onerror="this.src='${defaultImg }'" width="99%"
                  style="margin-left:2px;"/></a>
            </td>
            <c:if test="${videoList.all.size() > 2 }">
              <td style="width:50%">
                <a onclick="playVideo('${videoList.all[2].cameraId}', '${videoList.all[2].videoUrl }')"><img
                    src="${videoList.all[2].coverUrl }" onerror="this.src='${defaultImg }'" width="99%"
                    style="margin-left:2px;"/></a>
              </td>
            </c:if>
          </tr>
          <c:if test="${videoList.all.size() > 3 }">
            <tr>
              <td style="width:50%">
                <a onclick="playVideo('${videoList.all[3].cameraId}', '${videoList.all[3].videoUrl }')"><img
                    src="${videoList.all[3].coverUrl }" onerror="this.src='${defaultImg }'" width="99%"
                    style="margin-left:2px;"/></a>
              </td>
              <c:if test="${videoList.all.size() > 4 }">
                <td style="width:50%">
                  <a onclick="playVideo('${videoList.all[4].cameraId}', '${videoList.all[4].videoUrl }')"><img
                      src="${videoList.all[4].coverUrl }" onerror="this.src='${defaultImg }'" width="99%"
                      style="margin-left:2px;"/></a>
                </td>
              </c:if>
            </tr>
          </c:if>
        </table>
      </div>
    </c:if>
  </div>
</c:forEach>

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

    function playVideo(cameraId, videoUrl) {
        new submitForm('${ctx}/biz/carekids/action_video_play', {
            instanceId: '${instanceId}',
            openId: '${openId}',
            cameraId: cameraId,
            videoUrl: videoUrl
        }).post();
    }

    function videoOneday(time) {
        new submitForm('${ctx}/biz/carekids/action_kid_video_oneday', {openId: '${openId}', time: time}).post();
    }
</script>
</html>