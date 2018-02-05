<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>系统设置</title>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>
<body ontouchstart style="background: #EFEEF3;">
<div class="hd" style="margin-top: -14px;">
  <h1 class="page_title" style="font-size: 26px;">我的幼儿园</h1>
  <p class="page_desc" style="font-size: 12px;"></p>
</div>

<div class="bd">
  <div class="weui_grids">
    <a href="${ctx }/biz/setting/action_user_account_class/${openId}"
       class="weui_grid js_grid">
      <div class="weui_grid_icon">
        <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/account_manager.svg" alt=""
             style="width: 28px;"/>
      </div>
      <p class="weui_grid_label">用户管理</p>
    </a> <a href="${ctx }/biz/setting/action_camera_setting/${openId}"
            class="weui_grid js_grid">
    <div class="weui_grid_icon">
      <img
          src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png"
          alt="" style="width: 28px;"/>
    </div>
    <p class="weui_grid_label">直播管理</p>
  </a>
    <a href="javascript:;" class="weui_grid js_grid">
      <div class="weui_grid_icon">
        <img src="http://static-10001988.cos.myqcloud.com/resource/images/icons/more.svg" alt="" style="width: 28px;"/>
      </div>
      <p class="weui_grid_label">____</p>
    </a>
  </div>
</div>
</body>
</html>