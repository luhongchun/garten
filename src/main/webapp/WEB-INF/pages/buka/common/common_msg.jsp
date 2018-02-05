<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp"%>
  <title>消息提示</title>
</head>
<body onload="load()">
<div data-role="page">
  <div data-role="content">
    <p>${request.msg}</p>
    <div id="msg_div"></div>
	<script type="text/javascript">
		var secs = 5; //倒计时的秒数 
		function load() {
			for ( var i = secs; i >= 0; i--) {
				window.setTimeout('doUpdate(' + i + ')',
						(secs - i) * 1000);
			}
		}
		function doUpdate(num) {
			document.getElementById('msg_div').innerHTML = '将在' + num
					+ '秒后自动跳转到不卡业务管理主页';
			if (num == 0) { 
				window.location = "<%=basePath%>buka/app/list.action"; 
			}
		}
	</script>
  </div>    
</div>
</body>
</html>