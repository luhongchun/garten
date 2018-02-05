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
    <title>萌宝直播</title>
  </c:if>
</head>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<body>
<div>
  <div style="display:none">
    <p>
      <img src='${imgUrl}'>
    </p>
  </div>
  <p>
    <video id="player" width="100%" controls="controls"
           autoplay="autoplay" webkit-playsinline>
      <source src="${videoUrl}"/>
    </video>
  </p>
  <p style="margin-left:10px;">${desc}</p>
  <div class="button_sp_area" align="right" style="margin-left:10px; margin-right:10px;">
    <c:if test="${not empty openId and not empty cameraId}">
      <c:url value="/biz/video/gen_video" var="statusUrl">
      </c:url>
      <a id="nextBtn" href="javascript:;" class="weui_btn weui_btn_mini weui_btn_primary"
         style="line-height:2.5; margin-right:5px; font-size:16px; display:none"
         onclick="snapshot('${openId}', '${cameraId}', '${statusUrl}')">再来一段</a>
    </c:if>
  </div>
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

<script type="text/javascript">
    var playLis = function () {
        this.muted = true;
    };
    $(function () {
        $("#nextBtn").hide();
        _initVideo();
    });

    var snapshot = getNext();

    function getNext() {
        var dealt = false;

        function inner(openId, cameraId, url) {
            if (!dealt) {
                dealt = true;
                loadingToast();
                <% int number = 200;%>
                $.post(url, {openId: openId, cameraId: cameraId, random: "<%=number%>"}, function (data) {
                    hideToast();
                    if ("none" == data) {
                        closeWindow();
                    } else {
                        var strs = new Array(); //定义一数组
                        strs = data.split("#"); //字符分割
                        var videoUrl = strs[0];
                        var shareVideoUrl = strs[1];
                        new submitForm('${ctx}/biz/carekids/action_show_video', {
                            instanceId: '${instanceId}',
                            openId: '${openId}',
                            cameraId: cameraId,
                            videoUrl: videoUrl,
                            shareVideoUrl: shareVideoUrl
                        }).post();   // Url中的参数被隐藏
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

    function audioPlay() {
        var audio = document.getElementById("audio");
        audio.play();
    }

    function initBtn() {
        if (!isTimeOut()) {
            $("#nextBtn").show();
        }
    }

    function isTimeOut() {
        var time = ${time};
        var now = new Date();
        var times = now.getTime();
        if (times - time <= 300000) {
            return false;
        } else {
            return true;
        }
    }

    function _initVideo() {
        var video = document.getElementById("player");
        video.addEventListener("ended", function () {
            initBtn();
        }, false);
    }

    function timer() {
        if (isStarted) {
            msCnt++;
        }
        if (isTimeOut()) {
            $("#nextBtn").hide();
        }
        console.info("msCnt = " + msCnt);
        if (isStarted && msCnt > 25) {
            clearInterval(timerInterval);
            $.post("${ctx}/credit/increase_play_video", {
                oId: '${openId}',
                instanceId: '${instanceId}'
            });
        }
    }

    function started() {
        isStarted = true;
    }

    function _initAudio() {
        var audio = document.getElementById("audio");
        audio.addEventListener("play", playLis, false);
        audio.addEventListener("ended", function () {
            audio.removeEventListener("play", playLis, false);
            if (audio.muted)
                audio.muted = false;
        }, false);
        audio.play();
    }

    var msCnt = 0;
    var isStarted = false;
    var timerInterval = setInterval(timer, 1000);

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

    function hideOptionMenu() {
        wx.hideOptionMenu();
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
        hideOptionMenu();
        /* var isStaff =
        ${isStaff};
        if (!isStaff) {
            hideOptionMenu();
        } */

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

    function openMap(lat, long, name, addr, scale, url) {
        wx.openLocation({
            latitude: 23.099994,
            longitude: 113.324520,
            name: 'TIT 创意园',
            address: '广州市海珠区新港中路 397 号',
            scale: 14,
            infoUrl: 'http://weixin.qq.com'
            //latitude: lat, // 纬度，浮点数，范围为90 ~ -90
            //longitude: long, // 经度，浮点数，范围为180 ~ -180。
            //name: '${name}', // 位置名
            //address: '${addr}', // 地址详情说明
            //scale: 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
            //infoUrl: '${url}' // 在查看位置界面底部显示的超链接,可点击跳转
        });
    }


</script>
</html>
