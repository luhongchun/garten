<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/example.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.min.css"/>
  <c:if test="${not empty title}">
    <title>${title}</title>
  </c:if>
  <c:if test="${empty title}">
    <title>萌宝记录</title>
  </c:if>
</head>

<body>
<div>
  <div style="display:none">
    <p>
      <img src='${imgUrl}'>
    </p>
  </div>
  <div id="id_video_container" style="width:100%"></div>
  <p style="margin-left:10px;">${desc}</p>
</div>

<c:if test="${not empty kindergartenName}">
  <div style="margin-top:20px;">
    <div class="weui_cells_title">对该幼儿园感兴趣？</div>
    <div class="weui_cells">
      <div class="weui_cell">
        <div class="weui_cell_hd">
          <c:choose>
            <c:when test="${not empty logo}">
              <img src="${logo}" alt="" style="width:30px;margin-right:5px;display:block">
            </c:when>
            <c:otherwise>
              <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/gardenLogo.svg" alt=""
                   style="width:30px;margin-right:5px;display:block">
            </c:otherwise>
          </c:choose>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
          <p style="font-size:16px;">${kindergartenName}招生热线</p>
        </div>
        <a href="tel:${kindergartenTel}">
          <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/phone.svg" alt=""
               style="width:24px;margin-right:5px;display:block"/>
        </a>
      </div>
    </div>
  </div>
</c:if>
<c:if test="${not empty slogan}">
  <div class="weui_cells" style="margin-top:10px;">
    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/garden.svg" alt=""
             style="width:29px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <p style="font-size:15px; color:#4C4C4C;">${slogan}</p>
      </div>
    </div>
  </div>
</c:if>
<div style="margin-top:20px;">
  <div class="weui_cells_title">视频观看有异常？</div>
  <div class="weui_cells">
    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/c_service.svg" alt=""
             style="width:29px;margin-right:5px;display:block">
      </div>

      <div class="weui_cell_bd weui_cell_primary">
        <p style="font-size:16px;">联系客服</p>
      </div>
      <a href="tel:4000225085">
        <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/phone.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </a>
    </div>
  </div>
</div>
<c:if test="${not empty qrcode}">
  <div style="margin-top:20px;">
    <div class="weui_cells">
      <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
          <img src="${qrcode }" alt="" style="width:98%;display:block">
        </div>
      </div>
      <div class="weui_cell">
        <p>长按二维码识别关注，完成用户注册</p>
      </div>
    </div>
  </div>
</c:if>

<c:if test="${not empty videoPlayBottom}">
  <!-- 请置于所有广告位代码之前 -->
  <script type="text/javascript" src="http://dup.baidustatic.com/js/zm.js"></script>
  <!-- 广告位：播放页广告 -->
  <center>
    <div id="baidu_dup_${videoPlayBottom}"></div>
  </center>
  <script type="text/javascript">
      (BAIDU_DUP = window.BAIDU_DUP || []).push(['fillAsync', '${videoPlayBottom}', 'baidu_dup_${videoPlayBottom}']);
  </script>
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

<%@ include file="/WEB-INF/includes/piwik.jsp" %>

<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="https://qzonestyle.gtimg.cn/open/qcloud/video/h5/h5connect.js" charset="utf-8"></script>
<script type="text/javascript">
    (function () {
        var option = {
            "auto_play": "0",
            "file_id": "${videoId}",
            "app_id": "1251014592",
            "width": 1280,
            "height": 720,
            "https": 1
        };
        /*调用播放器进行播放*/
        new qcVideo.Player(
            /*代码中的id_video_container将会作为播放器放置的容器使用,可自行替换*/
            "id_video_container",
            option
        );
    })()

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

    wx.config({
        debug: ${debug}, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: '${appId}', // 必填，公众号的唯一标识
        timestamp: ${timestamp}, // 必填，生成签名的时间戳
        nonceStr: '${nonceStr}', // 必填，生成签名的随机串
        signature: '${signature}',// 必填，签名，见附录1
        jsApiList: ['checkJsApi',
            'closeWindow',
            'hideOptionMenu',
            'onMenuShareTimeline',
            'onMenuShareAppMessage',
            'onMenuShareQQ',
            'onMenuShareWeibo',
            'onMenuShareQZone'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });

    wx.ready(function () {

        wx.checkJsApi({
            jsApiList: ['checkJsApi',
                'closeWindow',
                'hideOptionMenu',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'onMenuShareQZone'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function (res) {
                // 以键值对的形式返回，可用的api值true，不可用为false
                // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
            },
            cancel: function () {
            }
        });

        wx.onMenuShareAppMessage({
            title: '${title}',
            desc: '${desc}',
            link: '${shareUrl}',
            imgUrl: '${imgUrl}',
            trigger: function (res) {
            },
            success: function (res) {
            },
            cancel: function (res) {
            },
            fail: function (res) {
            }
        });
        wx.onMenuShareTimeline({
            title: '${title}',
            desc: '${desc}',
            link: '${shareUrl}',
            imgUrl: '${imgUrl}',
            trigger: function (res) {
            },
            success: function (res) {
            },
            cancel: function (res) {
            },
            fail: function (res) {
            }
        });
        wx.onMenuShareQQ({
            title: '${title}', // 分享标题
            desc: '${desc}', // 分享描述
            link: '${shareUrl}', // 分享链接
            imgUrl: '${imgUrl}', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        wx.onMenuShareWeibo({
            title: '${title}', // 分享标题
            desc: '${desc}', // 分享描述
            link: '${shareUrl}', // 分享链接
            imgUrl: '${imgUrl}', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        wx.onMenuShareQZone({
            title: '${title}', // 分享标题
            desc: '${desc}', // 分享描述
            link: '${shareUrl}', // 分享链接
            imgUrl: '${imgUrl}', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
    });

    wx.error(function (res) {
        // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    });

</script>
</html>
