<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>精彩回顾</title>
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
              <c:when test="${not empty camera.id}">
                <a class="weui_cell" href="javascript:;" onclick="videoList('${camera.id}')">
                  <div class="weui_cell_hd">
                    <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png" alt=""
                         style="width:22px;margin-right:5px;display:block"/>
                  </div>
                  <div class="weui_cell_bd weui_cell_primary">
                    <p>${empty camera.name ? '未命名' : camera.name}</p>
                  </div>
                  <span class="weui_cell_ft"></span>
                </a>
              </c:when>
              <c:otherwise>
                <a class="weui_cell" href="javascript:void(0)" onclick="this.setAttribute('disabled','disabled')">
                  <div class="weui_cell_hd">
                    <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_close.png" alt=""
                         style="width:22px;margin-right:5px;display:block"/>
                  </div>
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
    <p class="weui_toast_content">加载中</p>
  </div>
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

    function videoList(cameraId) {
        new submitForm('${ctx}/biz/carekids/get_one_camera_video_list', {
            instanceId: '${instanceId}',
            openId: '${openId}',
            cameraId: cameraId
        }).post();
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