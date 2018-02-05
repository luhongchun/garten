<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap-datepicker.min.css">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>应用详情</title>

<style type="text/css">
label[for="sex"] + .ui-flipswitch {background-color: #38c!important;border-color:#38c!important;text-shadow:0 1px 0 #059!important;color:#fff!important;}
.ui-page-theme-a label[for="sex"] + .ui-flipswitch-active{background-color: pink!important;border-color:pink!important;text-shadow:0 1px 0 pink!important;}
select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{font-size:inherit;}
</style>

</head>
<body>
	<div data-role="page">
		<div data-role="content">
			<form method="post" name="form_data" id="form_data">
				<input type="hidden" name="appKey" value="${info.appKey }">
				<div class="ui-field-contain">
					<label for="appName">应用名称</label>
					<input type="text" id="appName" name="appName" placeholder="请输入应用名称"
						required="required"  value="${info.appName }" readonly="readonly">
				</div>
				<div class="ui-field-contain">
					<label for="appDesc">应用描述</label>
					<input type="text" id="appDesc" name="appDesc" placeholder="请输入应用描述"
						required="required"  value="${info.appDesc }" readonly="readonly">
				</div>
				<div class="ui-field-contain">
					<label for="appIntroUrl">应用介绍的URL</label>
					<input type="text" id="appIntroUrl" name="appIntroUrl" placeholder="请输入应用介绍的URL"
						required="required"  value="${info.appIntroUrl }" readonly="readonly">
				</div>
				<div class="ui-field-contain">
					<label for="appInfo">应用详情</label>
					<input type="text" id="appInfo" name="appInfo" placeholder="请输入应用详情"
						required="required"  value="${info.appInfo }" readonly="readonly">
				</div>
				
			    <div class="ui-field-contain" align="center">
			    	<s:url var="roomslistAction" value="/buka/app/room/list.action" />
			    	<s:url var="listAction" value="/buka/app/list.action" />
			    	<s:url var="appDelAction" value="/buka/app/del.action" />
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="roomsBtn" >查看应用房间列表</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="delBtn" >删除应用</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="listBtn" >返回应用列表</button>
				</div>
			</form>
		</div>
	</div>
	
	<script type="text/javascript">
		$(function() {
			$("#roomsBtn").click(function() {
	          $("#form_data").attr("action", "${roomslistAction}");
	          $("#form_data").submit();
	        });
			
			$("#delBtn").click(function() {
		       $("#form_data").attr("action", "${appDelAction}");
		       $("#form_data").submit();
		    });
				
			$("#listBtn").click(function() {
		          $("#form_data").attr("action", "${listAction}");
		          $("#form_data").submit();
		    });
			
			$("#form_data").submit(function() {
				$(":submit", this).attr("disabled", true);
			});
		});
	</script>
</body>
</html>