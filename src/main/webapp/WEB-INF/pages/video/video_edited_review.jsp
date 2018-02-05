<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/example.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.min.css"/>
  <title>视频审核</title>
</head>

<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="https://qzonestyle.gtimg.cn/open/qcloud/video/h5/h5connect.js" charset="utf-8"></script>
<body ontouchstart style="background:#EFEEF3;">
<div class="weui_panel weui_panel_access" style="margin-top:15px;">

  <div class="weui_panel_bd">
    <a href="javascript:void(0);" class="weui_media_box weui_media_appmsg">
      <div class="weui_media_bd">
        <h4 class="weui_media_title">${gartenName }${issue }视频审核</h4>
        <table border="0" width="100%" style="margin-top:10px;">
          <tr>
            <td>
              <p class="weui_media_desc">类型：${videoType }</p>
            </td>
            <td>
              <p class="weui_media_desc">班级：${className }</p>
            </td>
            <td>
              <p class="weui_media_desc">数量：${videoCnt }</p>
            </td>
          </tr>
        </table>
      </div>
      <span class="weui_cell_ft"></span>
    </a>
  </div>
</div>

<form name="dataForm" id="dataForm">
  <input type="hidden" name="videoCnt" value="${videoCnt}">
  <input type="hidden" name="userId" value="${userId}">
  <c:forEach items="${videos}" var="video" varStatus="status">
    <div class="weui_cells weui_cells_form" style="font-size:16px">
      <label class="weui_cell weui_cells_checkbox">
        <div class="weui_cell_bd weui_cell_primary">
          <p>${status.index + 1 }.${video.childName }</p>
        </div>
        <div class="weui_cell_hd" style="margin-right:5px;">
          <p>过审</p>
        </div>
        <div class="weui_cell_hd " style="margin-right:-7px;">
          <input type="checkbox" class="weui_check" name="passStatus"
                 value="${video.id}" id="cb${video.id}">
          <i class="weui_icon_checked"></i>
        </div>
      </label>
      <div class="weui_cell"><!-- 视频控件中的style中，不能指定height属性 -->
        <div id="id_video_container_${video.videoUrl }" style="width:100%;"></div>
        <script type="text/javascript">
            (function () {
                var option = {
                    "auto_play": "0",
                    "file_id": "${video.videoUrl }",
                    "app_id": "1251014592",
                    "width": 1280,
                    "height": 720,
                    "https": 1
                };
                /*调用播放器进行播放*/
                new qcVideo.Player(/*代码中的id_video_container将会作为播放器放置的容器使用,可自行替换*/
                    "id_video_container_${video.videoUrl }",
                    option);
            })()
        </script>
      </div>
      <div class="weui_cell">
        <div class="weui_cell_bd weui_cell_primary">
          <textarea class="weui_textarea" name="des" id="${video.id}" placeholder="若视频有问题请在此处说明" rows="1"></textarea>
        </div>
      </div>
    </div>
    <input name="videoIds" type="hidden" value="${video.id }"
           form="dataForm"/>
    <input name="desArray" type="hidden" value="${video.id }," id="${video.id }"
           form="dataForm"/>
  </c:forEach>
</form>

<div style="margin-top:40px;">
  <p style="color:#EFEEF3;">____</p>
</div>

<div class="bd" style="width:100%; position:static; bottom:0;" style="margin-top:20px;">
  <div class="weui_tab">
    <div class="weui_tabbar">
      <a id="savea" href="javascript:;" class="weui_tabbar_item" onclick="saveChange()">
        <div class="weui_tabbar_icon">
          <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/save.svg" alt="" style="width:24px;"/>
        </div>
        <p class="weui_tabbar_label">提交审核</p>
      </a>
    </div>
  </div>
</div>

<div class="weui_dialog_confirm" id="promptDialog" style="display:none;">
  <div class="weui_mask"></div>
  <div class="weui_dialog">
    <div id="dlgTitle2" class="weui_dialog_hd">
      <strong class="weui_dialog_title">
        <p>是否提交本次视频审核结果 ？</p>
        <p style="font-size:14px; margin-top:10px;">视频总数：${videoCnt }</p>
        <p style="font-size:14px" id="passCntId"></p>
        <p style="font-size:14px" id="failCntId"></p>
      </strong></div>
    <div class="weui_dialog_bd"><p id="dlgText2" align="center"></p></div>
    <div class="weui_dialog_ft">
      <a id="leftBtn" href="javascript:;" onclick="submitValue()" class="weui_btn_dialog primary">提交</a>
      <a id="rightBtn" href="javascript:;" onclick="closeDialog()" class="weui_btn_dialog default">再看看</a>
    </div>
  </div>
</div>

<!--BEGIN loadingToast Loading-->
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
    <p class="weui_toast_content">审核提交中</p>
  </div>
</div>
<!--END loadingToast-->

<!--BEGIN completeToast-->
<div id="completeToast" style="display: none;">
  <div class="weui_mask_transparent"></div>
  <div class="weui_toast">
    <i class="weui_icon_toast"></i>
    <p class="weui_toast_content">提交完成</p>
  </div>
</div>
<!--BEGIN completeToast 提交完成提示框，如果提交失败，则显示提交失败，提交失败时，不返回微信公众号菜单-->

<!--BEGIN failToast-->
<div id="failToast" style="display: none;">
  <div class="weui_mask_transparent"></div>
  <div class="weui_toast">
    <i class="weui_icon_toast"></i>
    <p class="weui_toast_content">提交失败</p>
  </div>
</div>
</body>

<%@ include file="/WEB-INF/includes/piwik.jsp" %>

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

    function saveChange() {
        countPass();
        summaryDes();
        showLoadingToast();
        submitResult();
        //var promptDlg = document.getElementById('promptDialog');
        //promptDlg.style.display = "block";
    }

    function countPass() {
        var passCnt = 0;
        var cb = document.getElementsByName('passStatus');
        for (var i in cb) {
            if (cb[i].checked) {
                passCnt++;
            }
        }
        if (passCnt > ${videoCnt})
            passCnt = ${videoCnt};
        var failCnt = ${videoCnt} -passCnt;
        $("#passCntId").text("过审个数：" + passCnt);
        $("#failCntId").text("未过审数：" + failCnt);
    }

    function summaryDes() {//统计未过原因
        var ta = document.getElementsByTagName('textarea');
        var des = document.getElementsByName('desArray');
        for (var i in ta) {
            if (ta[i].id) {
                var taElement = document.getElementById(ta[i].id);
                for (var j in des) {
                    if (ta[i].id == des[j].id) {
                        des[j].value = des[j].id + "#" + taElement.value;
                        break;
                    }
                }
            }
        }
    }

    function submitResult() {
        $.post("${ctx}/biz/video/closeup/action_review_video_result", $("#dataForm").serialize(),
            function (data) {
                //promptChange(data);
                window.location.href = data;
            });
    }

    function promptChange(ret) {
        //var savea = document.getElementById('savea');
        //savea.setAttribute("class", "weui_tabbar_item weui_bar_item_on");
        showComlpeteToast(ret == "success");
    }

    function showComlpeteToast(success) {
        hideLoadingToast()
        if (success) {
            showCompleteToast()
            setTimeout("hideCompleteToast()", 1000);
        } else {
            showFailToast();
            setTimeout("hideFailToast()", 1000);
        }
    }

    function showLoadingToast() {
        var loading = document.getElementById('loadingToast');
        loading.style.display = "block";
    }

    function hideLoadingToast() {
        var loading = document.getElementById('loadingToast');
        loading.style.display = "none";
    }

    function showCompleteToast() {
        var comlpete = document.getElementById('completeToast');
        comlpete.style.display = "block";
    }

    function hideCompleteToast() {
        var comlpete = document.getElementById('completeToast');
        comlpete.style.display = "none";
        closeWindow();
    }

    function showFailToast() {
        var comlpete = document.getElementById('failToast');
        comlpete.style.display = "block";
    }

    function hideFailToast() {
        var comlpete = document.getElementById('failToast');
        comlpete.style.display = "none";
    }

    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }

    /* function submitValue() {
        var pDialog = document.getElementById("promptDialog");
        pDialog.style.display = "none";
        showLoadingToast();
        videoReview();
    }

    function closeDialog() {
        var pDialog = document.getElementById("promptDialog");
        pDialog.style.display = "none";
    } */

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
