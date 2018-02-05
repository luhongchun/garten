<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<%
  response.setStatus(HttpServletResponse.SC_OK); //这句一定要写,不然IE不会跳转到该页面
  String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>提示</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart>
<div class="weui_msg">
  <div class="weui_icon_area">
    <i class="weui_icon_msg weui_icon_warn"></i>
  </div>

  <div class="weui_text_area">
    <h2 class="weui_msg_title">啊哦……页面出错了</h2>
    <p class="weui_msg_desc">请返回重试或联系客服</p>
  </div>

  <div class="weui_opr_area">
    <p class="weui_btn_area">
      <a href="tel:400-022-5085" class="weui_btn weui_btn_primary">联系客服</a>
      <a href="javascript:;" class="weui_btn weui_btn_default" onclick="closeWindow()">返回</a>
    </p>
  </div>

  <div class="weui_extra_area">
    <a href="javascript:void(0)">业务办理的最终解释权归智启科技所有</a>
  </div>
</div>

<div style="display:none;">
  <div>系统执行发生错误，信息描述如下：</div>
  <div>错误状态代码是：${pageContext.errorData.statusCode}</div>
  <div>错误发生页面是：${pageContext.errorData.requestURI}</div>
  <div>错误信息：
    <pre>
	     	    <%
              exception.printStackTrace(new java.io.PrintWriter(out));
            %>
 			    </pre>
  </div>
</div>
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