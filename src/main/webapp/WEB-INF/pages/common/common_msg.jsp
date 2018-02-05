<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>提示</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>
<body>
<div class="weui_msg">
  <div class="weui_icon_area">
    <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/401.png" width="96px"></img>
  </div>

  <div class="weui_text_area">
    <h2 class="weui_msg_title">消息</h2>
    <p class="weui_msg_desc">${msg }</p>
  </div>

  <div class="weui_opr_area">
    <p class="weui_btn_area">
      <a href="javascript:;" class="weui_btn weui_btn_primary" onclick="closeWindow()">返 回</a>
    </p>
  </div>
</div>
</body>
<script type="text/javascript">
    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }
</script>
</html>