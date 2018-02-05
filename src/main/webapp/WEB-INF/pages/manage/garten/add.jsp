<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/includes/common-res.jsp" %>
    <title>添加幼儿园</title>
</head>
<body>
<div data-role="page">
    <div data-role="content">
        <h4><font color="red">${param.tip}</font></h4>
        <c:url var="formAction" value="/garten/manage/garten/add_commit"/>
        <form method="post" name="form_data" id="form_data" action="${formAction}">
            <div>
                <h4>幼儿园名称</h4>
            </div>
            <div>
                <input type="text" id="name" name="garten.name" placeholder="请输入幼儿园名称" required="required">
            </div>
            <div>
                <h4>官网地址</h4>
            </div>
            <div>
                <input type="url" id="officialWebsite" name="gartenInfo.officialWebsite" placeholder="幼儿园官网地址"
                       required="required">
            </div>
            <div>
                <h4>联系电话</h4>
            </div>
            <div>
                <input type="tel" id="tel" name="gartenInfo.tel" placeholder="幼儿园官方电话" required="required">
            </div>
            <div>
                <h4>幼儿园LOGO地址</h4>
            </div>
            <div>
                <input type="url" id="logoUrl" name="gartenInfo.logoUrl" placeholder="幼儿园LOGO地址" required="required">
            </div>
            <div>
                <h4>描述</h4>
            </div>
            <div>
                <input type="text" id="description" name="garten.description" placeholder="幼儿园描述信息">
            </div>
            <input type="submit" value="提交">
        </form>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $("#form_data").submit(function () {
            $(":submit", this).attr("disabled", true);
        })
    });
</script>
</body>
</html>