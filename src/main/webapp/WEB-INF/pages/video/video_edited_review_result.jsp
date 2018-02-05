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
  <title>视频审核结果</title>
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
              <p class="weui_media_desc">数量：${passCnt+failCnt }</p>
            </td>
          </tr>
        </table>
      </div>
      <span class="weui_cell_ft"></span>
    </a>
  </div>
</div>

<div class="weui_msg">
  <div class="weui_icon_area">
    <c:if test="${passCnt <= 0 && failCnt <= 0}">
      <i class="weui_icon_msg weui_icon_warn"></i>
    </c:if>
    <c:if test="${passCnt > 0 || failCnt > 0}">
      <i class="weui_icon_msg weui_icon_info"></i>
    </c:if>
  </div>

  <div class="weui_text_area">
    <c:if test="${passCnt <= 0 && failCnt <= 0}">
      <h2 class="weui_msg_title">提交失败</h2>
    </c:if>
    <c:if test="${passCnt > 0 || failCnt > 0}">
      <h2 class="weui_msg_title">提交成功</h2>
      <p class="weui_msg_desc">视频总数：${passCnt+failCnt }</p>
      <p class="weui_msg_desc">过审个数：${passCnt }</p>
      <p class="weui_msg_desc">未过审数：${failCnt }</p>
    </c:if>
  </div>

  <div class="weui_opr_area">
    <p class="weui_btn_area">
      <a href="javascript:;" class="weui_btn weui_btn_primary" onclick="closeWindow()">返 回</a>
    </p>
  </div>

  <div class="weui_extra_area">
    <a href="javascript:void(0)">业务办理的最终解释权归智启科技所有</a>
  </div>
</div>
<%-- <div>
    <c:if test="${passCnt <= 0 && failCnt <= 0}">
      <p>提交失败</p>
    </c:if>
    <c:if test="${passCnt > 0 || failCnt > 0}">
     <p>提交成功</p>
      <p style="font-size:14px; margin-top:10px;">视频总数：${passCnt+failCnt }</p>
      <p style="font-size:14px" id="passCntId">过审个数：${passCnt }</p>
      <p style="font-size:14px" id="failCntId">未过审数：${failCnt }</p>
    </c:if>
    <a href="javascript:;" class="weui_btn weui_btn_default" onclick="closeWindow()">返 回</a>
</div>

<div style="margin-top:40px;">
    <p style="color:#EFEEF3;">____</p>
</div> --%>
</body>

<%@ include file="/WEB-INF/includes/piwik.jsp" %>

<script type="text/javascript">
    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }
</script>
</html>
