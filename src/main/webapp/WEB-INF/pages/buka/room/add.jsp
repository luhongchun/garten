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
<title>创建房间</title>

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
				value="/buka/app/room/create.action" />
			<form method="post" name="form_data" id="form_data"
				action="${formAction}">
				<input type="hidden" name="TokenName" value="${session.TokenName}">
				<input type="hidden" name="appKey" value="${appKey}">
				
				<div class="ui-field-contain">
					<label for="name">房间名称<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="name" name="name" placeholder="请输入房间名称"
						required="required" data-clear-btn="true">
				</div>
				<div class="ui-field-contain">
					<label for="alias">房间短域名<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="alias" name="alias" placeholder="请输入房间短域名"
						required="required" data-clear-btn="true">
				</div>
				<!-- 
				<div class="ui-field-contain">
					<label for="appKey">归属应用<font color="RED"> <strong>*</strong></font></label>
					<select name="appKey" id="appKey" data-native-menu="false">
						<c:if test="${not empty infos }">
							<c:forEach items="${infos }" var="info">
								<option value="${info.appKey }">${info.appName }</option>
							</c:forEach>
						</c:if>
					</select>	
				</div>
				 -->
				<div class="ui-field-contain">
					<label for="roomNum">创建房间数量<font color="RED"> <strong>*</strong></font></label> 
						<input type="range" name="roomNum" id="roomNum" value="1" min="1" max="500">
				</div>
				
			    <div class="ui-field-contain" align="center">
			    	<s:url var="listAction" value="/buka/app/room/list.action" />
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="submit">创建应用</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="reset">重置</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="listBtn" >返回房间列表</button>
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