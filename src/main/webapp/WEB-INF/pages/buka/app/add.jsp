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
<title>创建应用</title>

<style type="text/css">
label[for="sex"] + .ui-flipswitch {background-color: #38c!important;border-color:#38c!important;text-shadow:0 1px 0 #059!important;color:#fff!important;}
.ui-page-theme-a label[for="sex"] + .ui-flipswitch-active{background-color: pink!important;border-color:pink!important;text-shadow:0 1px 0 pink!important;}
select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{font-size:inherit;}
</style>

</head>
<body>
	<div data-role="page">
		<div data-role="content">
			<s:url var="formAction"
				value="/buka/app/create.action" />
			<form method="post" name="form_data" id="form_data"
				action="${formAction}">
				<input type="hidden" name="TokenName" value="${session.TokenName}">
				
				<div class="ui-field-contain">
					<label for="appName">应用名称<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="appName" name="appName" placeholder="请输入应用名称"
						required="required" data-clear-btn="true">
				</div>
				<div class="ui-field-contain">
					<label for="appDesc">应用描述<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="appDesc" name="appDesc" placeholder="请输入应用描述"
						required="required" data-clear-btn="true">
				</div>
				<div class="ui-field-contain">
					<label for="appIntroUrl">应用介绍的URL<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="appIntroUrl" name="appIntroUrl" placeholder="请输入应用介绍的URL"
						required="required" data-clear-btn="true">
				</div>
				<div class="ui-field-contain">
					<label for="appInfo">应用详情<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="appInfo" name="appInfo" placeholder="请输入应用详情"
						required="required" data-clear-btn="true">
				</div>
				
			    <div class="ui-field-contain" align="center">
			    	<s:url var="listAction" value="/buka/app/list.action" />
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="submit">创建应用</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="reset">重置</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="listBtn" >返回应用列表</button>
				</div>
			</form>
		</div>
	</div>
	
	<script src="${ctx }/js/datetimepicker/js/bootstrap.min.js"></script>
  	<script src="${ctx }/js/datetimepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="${ctx }/js/datetimepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
	<script type="text/javascript">
		$(function() {
			$("#listBtn").click(function() {
	          $("#form_data").attr("action", "${listAction}");
	          $("#form_data").submit();
	        });
			
			$("#form_data").submit(function() {
				$(":submit", this).attr("disabled", true);
			});
			
			$('.datepicker').datepicker({
	        	 language:  'zh-CN'
			});
		});
	</script>
</body>
</html>