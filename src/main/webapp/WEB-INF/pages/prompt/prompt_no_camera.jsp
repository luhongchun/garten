<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>提示</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
  </meta>
  </meta>
</head>
<body ontouchstart>
<div class="weui_msg">
  <div class="weui_icon_area">
    <i class="weui_icon_msg weui_icon_info"></i>
  </div>

  <div class="weui_text_area">
    <h2 class="weui_msg_title">未分配</h2>
    <p class="weui_msg_desc">您没有可观看的区域，请联系幼儿园开放</p>
  </div>

  <div class="weui_opr_area">
    <p class="weui_btn_area">
      <a href="javascript:;" class="weui_btn weui_btn_primary" onclick="closeWindow()" ;>返回</a>
    </p>
  </div>

  <div class="weui_extra_area">
    <a href="javascript:void(0)">业务办理的最终解释权归智启科技所有</a>
  </div>
</div>
</body>

<script type="text/javascript">
    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }
</script>
</html>