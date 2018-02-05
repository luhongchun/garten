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
	<title>建立新分组</title>
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
        <c:url var="formAction" value="/biz/video/frGroup/create"/>
        <form method="post" name="form_data" id="form_data" action="${formAction}">
            <div>
                <h4>班级ID</h4>
            </div>
            <div>
                <input type="text" id="classId" name="classId" placeholder="请输入班级ID" required="required">
            </div>
            <div>
                <h4>参考系素材路径</h4>
            </div>
            <div>
                <input type="text" id="materialPath" name="materialPath" placeholder="请填写素材路径">
            </div>
            <input type="submit" id="submit" value="开始建立">
        </form>
    </div>
</div>
<script src="${ctx }/js/datetimepicker/js/bootstrap.min.js"></script>
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
                    	alert("已开始");
                    } else {
                    	document.getElementById("submit").disabled = false;
                    	alert(msg);
                    }
                }
            });
            return false;
        });
    });
</script>
</body>
</html>