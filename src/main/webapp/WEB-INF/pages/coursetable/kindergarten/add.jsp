<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap-datepicker.min.css">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>添加授权幼儿园</title>
<style type="text/css">
label[for="sex"] + .ui-flipswitch {background-color: #38c!important;border-color:#38c!important;text-shadow:0 1px 0 #059!important;color:#fff!important;}
.ui-page-theme-a label[for="sex"] + .ui-flipswitch-active{background-color: pink!important;border-color:pink!important;text-shadow:0 1px 0 pink!important;}
select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{font-size:inherit;}
.ui-flipswitch .ui-btn.ui-flipswitch-on{text-indent: -3.5em;}
.ui-flipswitch .ui-flipswitch-off{text-indent: 0.2em;}
</style>

</head>
<body>
  <div data-role="page">
  <div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
  
    <div data-role="content">
      <s:url var="formAction" value="/coursetable/kindergarten/add_summit.action" />
      <form method="post" name="form_data" id="form_data" action="${formAction}">
        <input type="hidden" name="TokenName" value="${session.TokenName}">
        
        <div class="ui-field-contain">
			<label for="name">幼儿园名称<font color="RED"> <strong>*</strong></font></label>
			<input type="text" id="name" name="name" placeholder="请输入幼儿园名称"
				required="required" data-clear-btn="true">
		</div>
		<div class="ui-field-contain">
			<label for="license">授权许可证<font color="RED"> <strong>*</strong></font></label>
			<input type="text" id="license" name="license" placeholder="授权许可证"
				required="required" data-clear-btn="true">
		</div>
		<div class="ui-field-contain">
			<label for="authorStartDate">授权起始日期<font color="RED"> <strong>*</strong></font></label>
			<input 
						type="text" 
						id="authorStartDate" 
						name="authorStartDate"
						class="datepicker"
						data-date-format="yyyy/mm/dd"
						placeholder="请输入授权起始日期" 
						required="required" 
						>
		</div>
		<div class="ui-field-contain">
			<label for="authorEndDate">授权终止日期<font color="RED"> <strong>*</strong></font></label>
			<input 
						type="text" 
						id="authorEndDate" 
						name="authorEndDate"
						class="datepicker"
						data-date-format="yyyy/mm/dd"
						placeholder="请输入授权终止日期" 
						required="required" 
						>
		</div>
		
		<div class="ui-field-contain">
			<label for="authorState">授权状态<font color="RED"> <strong>*</strong></font></label>
			<input type="checkbox" data-role="flipswitch" name="authorState" id="authorState"
				data-off-text="未授权" data-on-text="已授权" checked="checked">
		</div>
        <div class="ui-field-contain">
			<label for="address">幼儿园地址<font color="RED"> <strong>*</strong></font></label>
			<input type="text" id="address" name="address" placeholder="请输入幼儿园地址"
				required="required" data-clear-btn="true">
		</div>
        <div class="ui-field-contain" align="center">
	    	<s:url var="listAction" value="/coursetable/kindergarten/list.action" />
	    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="submit">提交</button>
	    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="reset">重置</button>
	    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="listBtn" >返回列表</button>
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