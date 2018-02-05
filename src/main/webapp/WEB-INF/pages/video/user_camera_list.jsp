<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>萌宝直播</title>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/example.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.min.css"/>
</head>

<body ontouchstart>
<div data-role="page">
  <c:if test="${not empty cameraListTopAd}">
    <!-- 请置于所有广告位代码之前 -->
    <script type="text/javascript" src="http://dup.baidustatic.com/js/zm.js"></script>
    <!-- 广告位：播放页广告 -->
    <center>
      <div id="baidu_dup_${cameraListTopAd}" style="margin-bottom:-30px;"></div>
    </center>
    <script type="text/javascript">
        (BAIDU_DUP = window.BAIDU_DUP || []).push(['fillAsync', '${cameraListTopAd}', 'baidu_dup_${cameraListTopAd}']);
    </script>
  </c:if>
</div>
<div data-role="main" class="ui-content jqm-content">
  <ul data-role="listview" data-inset="true">
    <c:if test="${cameras == null || fn:length(cameras) == 0}">
      <li>亲,还没有可用摄像头哦！</li>
    </c:if>
    <c:if test="${cameras != null && fn:length(cameras) > 0}">
      <c:forEach items="${cameras}" var="camera" varStatus="status">
        <c:url value="/biz/video/gen_video" var="statusUrl">
        </c:url>
        <div class="weui_media_box weui_media_small_appmsg">
          <div class="weui_cells weui_cells_access">
            <c:choose>
              <c:when test="${not empty camera.id}"> <!--如果 -->
                <a class="weui_cell" href="javascript:;"
                   onclick="snapshot('${openId}', '${camera.id}', '${statusUrl}')">
                  <div class="weui_cell_hd"><img
                      src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png" alt=""
                      style="width:22px;margin-right:5px;display:block"/></div>
                  <div class="weui_cell_bd weui_cell_primary">
                    <p>${empty camera.name ? '未命名' : camera.name}</p>
                  </div>
                  <span class="weui_cell_ft"></span>
                </a>
              </c:when>
              <c:otherwise> <!--否则 -->
                <a class="weui_cell" href="javascript:void(0)" onclick="this.setAttribute('disabled','disabled')">
                  <div class="weui_cell_hd"><img
                      src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png" alt=""
                      style="width:22px;margin-right:5px;display:block"/></div>
                  <div class="weui_cell_bd weui_cell_primary">
                    <p>${empty camera.name ? '未命名' : camera.name}</p>
                  </div>
                  <div class="weui_cell_bd weui_cell_primary" style="margin-left:px;">
                    <p style="color:#A6AAA9;">设备维护中</p>
                  </div>
                </a>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </c:forEach>
    </c:if>
  </ul>
</div>


<div style="margin-top:40px;">
  <p style="color:#EFEEF3;">____</p>
</div>

<c:if test="${live and find }">
  <div class="weui_tabbar" style="width:100%; position:fixed;bottom:0;" style="margin-top:20px;">
    <a class="weui_tabbar_item weui_bar_item_on ">
      <div class="weui_tabbar_icon">
        <img src="//static-10001988.cos.myqcloud.com/resource/images/icons/live_on.svg" alt="">
      </div>
      <p class="weui_tabbar_label">直播</p>
    </a>
    <a class="weui_tabbar_item" onclick="find()">
      <div class="weui_tabbar_icon">
        <img src="//static-10001988.cos.myqcloud.com/resource/images/icons/find_off.svg" alt="">
      </div>
      <p class="weui_tabbar_label">发现</p>
    </a>
  </div>
</c:if>

<!-- loading toast -->
<div id="loadingToast" class="weui_loading_toast" style="display:none;">
  <div class="weui_mask_transparent"></div>
  <div class="weui_toast">
    <div class="weui_loading">
      <div class="weui_loading_leaf weui_loading_leaf_0"></div>
      <div class="weui_loading_leaf weui_loading_leaf_1"></div>
      <div class="weui_loading_leaf weui_loading_leaf_2"></div>
      <div class="weui_loading_leaf weui_loading_leaf_3"></div>
      <div class="weui_loading_leaf weui_loading_leaf_4"></div>
      <div class="weui_loading_leaf weui_loading_leaf_5"></div>
      <div class="weui_loading_leaf weui_loading_leaf_6"></div>
      <div class="weui_loading_leaf weui_loading_leaf_7"></div>
      <div class="weui_loading_leaf weui_loading_leaf_8"></div>
      <div class="weui_loading_leaf weui_loading_leaf_9"></div>
      <div class="weui_loading_leaf weui_loading_leaf_10"></div>
      <div class="weui_loading_leaf weui_loading_leaf_11"></div>
    </div>
    <p class="weui_toast_content">视频加载中</p>
  </div>
</div>
</body>
<script type="text/javascript">
    var snapshot = snapshotWrapper();

    function snapshotWrapper() {
        var dealt = false;

        function inner(openId, cameraId, url) {
            if (!dealt) {
                dealt = true;
                loadingToast();
                <% int number = new java.util.Random().nextInt(100);%>
                $.post(url, {openId: openId, cameraId: cameraId, random: "<%=number%>"}, function (data) {
                    hideToast();
                    if (data == null || data == "") {
                        closeWindow();
                    } else {
                        var strs = new Array(); //定义一数组
                        strs = data.split("#"); //字符分割
                        var videoUrl = strs[0];
                        var shareVideoUrl = strs[1];
                        if (videoUrl == null || videoUrl == "") {
                            closeWindow();
                        } else {
                            new submitForm('${ctx}/biz/carekids/action_show_video', {
                                instanceId: '${instanceId}',
                                openId: '${openId}',
                                cameraId: cameraId,
                                videoUrl: videoUrl,
                                shareVideoUrl: shareVideoUrl
                            }).post();
                        }
                    }
                });
                _paq.push(['trackEvent', '微信', '摄像头抓拍动作', '摄像头 - ' + cameraId, 1]);
                _paq.push(['trackEvent', '微信', '用户抓拍动作', '用户 - ' + openId, 1]);
                _paq.push(['trackPageView']);
            }
        }

        return inner;
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

    function find() {
        new submitForm('${ctx}/biz/carekids/action_kid_video', {openId: '${openId}'}).post();
    }

    function loadingToast() {
        var loading = document.getElementById('loadingToast');
        loading.style.display = "block";
    }

    function hideToast() {
        var loading = document.getElementById('loadingToast');
        loading.style.display = "none";
    }

    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }
</script>
<%@ include file="/WEB-INF/includes/piwik.jsp" %>
</html>