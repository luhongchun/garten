<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html">
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
<title>开通业务</title>
<link rel="stylesheet" href="https://weui.io/example.css" />
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css" />
<link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css" />
</head>
<body ontouchstart>
<div class="weui_msg">
<div class="weui_icon_area">
<i class="weui_icon_msg weui_icon_warn"></i>
</div>

<div class="weui_text_area">
<h2 class="weui_msg_title">账户欠费</h2>
<p class="weui_msg_desc">您的账户已过期，请及时续费以恢复功能的正常使用。续费过程如遇到问题，可联系客服咨询。</p>
</div>

<div class="weui_opr_area">
<p class="weui_btn_area">
<c:choose>
  <c:when test="${not empty renewUrl }">
    <a href="${renewUrl }" class="weui_btn weui_btn_primary">续 费</a>
  </c:when>
  <c:otherwise>
    <a href="tel:${kindergartenTel }" class="weui_btn weui_btn_primary">联系幼儿园</a>
  </c:otherwise>
</c:choose>
<a href="tel:400-022-5085" class="weui_btn weui_btn_primary">联系客服</a>
<a href="javascript:;" class="weui_btn weui_btn_default" onclick="closeWindow()">返 回</a>
</p>
</div>

<div class="weui_extra_area">
<a href="javascript:void(0)">业务办理的最终解释权归智启科技所有</a>
</div>
</div>

<!--BEGIN actionSheet
<div id="actionSheet_wrap">
<div class="weui_mask_transition" id="mask" style="display:none;"></div>
<div class="weui_actionsheet" id="weui_actionsheet">
<div class="weui_actionsheet_menu">
<div class="weui_actionsheet_cell" onclick="hideMaskDlg(4000225805)">哈佛幼儿园(小宝)</div>
<div class="weui_actionsheet_cell" onclick="hideMaskDlg(4000225805)">樱花幼儿园(小小宝)</div>
<div class="weui_actionsheet_cell" onclick="hideMaskDlg(4000225805)">博苑幼儿园(大宝)</div>
<div class="weui_actionsheet_cell" onclick="hideMaskDlg(4000225805)">鞍钢副企六幼(大大宝)</div>
</div>
<div class="weui_actionsheet_action">
<div class="weui_actionsheet_cell" id="actionsheet_cancel" onclick="hideMaskDlg()">取消</div>
</div>
</div>
</div>
END actionSheet-->
</body>

<script type="text/javascript">
function closeWindow() {
WeixinJSBridge.call('closeWindow');
}

function contactCService() {

}

function showMaskDlg() {
var mask = document.getElementById('mask');
var actionsheet = document.getElementById('weui_actionsheet');
mask.setAttribute("class", "weui_mask_transition weui_fade_toggle");
actionsheet.setAttribute("class", "weui_actionsheet weui_actionsheet_toggle");
mask.style.display = "block";
}

function hideMaskDlg(phoneNumber) {
var mask = document.getElementById('mask');
var actionsheet = document.getElementById('weui_actionsheet');
mask.setAttribute("class", "weui_mask_transition");
actionsheet.setAttribute("class", "weui_actionsheet");
mask.style.display = "none";
if (phoneNumber != null) {
window.location.href = "tel://" + phoneNumber;
}
}
</script>
</html>