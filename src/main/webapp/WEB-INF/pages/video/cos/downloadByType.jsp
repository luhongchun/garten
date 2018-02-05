<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap-datepicker.min.css">
	<%@ include file="/WEB-INF/includes/common-res.jsp"%>
	<title>视频下载</title>
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
    <div data-role="content">
        <h4><font color="red">${tip}</font></h4>
        <c:url var="formAction" value="/video/manage/download/start"/>
        <form method="post" name="form_data" id="form_data" action="${formAction}">
            <div>
                <h4>班级与对应设备ID</h4>
            </div>
            <div>
                <input type="text" id="cameraIds" name="cameraIds" placeholder="中一班:ff1231f,fasdas;大二班:fasdfc" required="required">
            </div>
            <div>
                <h4>日期选择</h4>
            </div>
            <div class="ui-field-contain">
				<input type="text" id="date" name="date" placeholder="请输入日期, 默认为开始下载时的当天"
          		 class="datepicker" data-date-format="yyyy_mm_dd"
          	 	 value="<fmt:formatDate value='${courseOrgInfo.authorStartDate }'/>">
			</div>
            <div>
                <h4>时间段选择</h4>
            </div>
            <div>
                <input type="text" id="start" name="start" placeholder="请输入开始时间,格式如 8:05, 默认为 6:00">
                <input type="text" id="end" name="end" placeholder="请输入结尾时间,格式如 10:25, 默认为 18:00">
            </div>
            <div>
                <h4>存储路径</h4>
            </div>
            <div>
                <input type="text" id="storeDir" name="storeDir" placeholder="请填写存储路径">
            </div>
            <div>
                <h4>业务选择</h4>
            </div>
			<div class="ui-field-contain">
				<label><input type="radio" name="videoType" value="live">直播</label>
           		<label><input type="radio" name="videoType" value="selected" checked="checked">精彩</label>
			</div>            
            <div>
                <h4>种类选择</h4>
            </div>
            <div>
                <label><input type="checkbox" name="cateall" value="cateall" checked="checked">全部</label>
                <label><input type="checkbox" name="catedistantfew" value="0">远景3人以下</label>
                <label><input type="checkbox" name="catedistantnormal" value="1">远景3~5人</label>
                <label><input type="checkbox" name="catedistantmore" value="2">远景6~10人</label>
                <label><input type="checkbox" name="catedistantmany" value="3">远景10人以上</label>
                <label><input type="checkbox" name="catemidfew" value="4">中景3人以下</label>
                <label><input type="checkbox" name="catemidnormal" value="5">中景3~5人</label>
                <label><input type="checkbox" name="catemidmore" value="6">中景6~10人</label>
                <label><input type="checkbox" name="catemidmany" value="7">中景10人以上</label>
                <label><input type="checkbox" name="cateclosefew" value="8">近景3人以下</label>
                <label><input type="checkbox" name="cateclosenormal" value="9">近景3~5人</label>
                <label><input type="checkbox" name="cateclosemore" value="a">近景6~10人</label>
                <label><input type="checkbox" name="cateclosemany" value="b">近景10人以上</label>
            </div>
            <input type="submit" id="submit" value="开始下载">
            <input type="button" id="stop" value="停止下载" style="display:none;">
        </form>
    </div>
</div>
<script src="${ctx }/js/datetimepicker/js/bootstrap.min.js"></script>
<script src="${ctx }/js/datetimepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx }/js/datetimepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
<script type="text/javascript">
    $(function () {
        $('#submit').click(function () {
            $('#submit').attr('disabled', "true");
            $.ajax({
                type: "POST",
                url: "${formAction}",
                data: $('#form_data').serialize(),
                success: function (msg) {
                    //window.location.href = msg;
                    if (msg == "success") {
                    	alert("已开始下载");
                    } else {
                    	document.getElementById("submit").disabled = false;
                    	alert(msg);
                    }
                }
            });
            return false;
        });
        $('#stop').click(function () {
            $.ajax({
                type: "POST",
                url: "${ctx}/video/manage/download/cancel/"+document.getElementById('cameraId').value,
                //data: $('#form_data').serialize(),
                success: function (msg) {
                    //window.location.href = msg;
                	document.getElementById("submit").disabled = false;
                    alert(msg);
                }
            });
        });
    });
    
    //时间控件调用
  	$('.datepicker').datepicker({
     	language:  'zh-CN'
  	});
</script>
</body>
</html>
