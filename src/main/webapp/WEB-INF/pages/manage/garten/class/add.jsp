<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/includes/common-res.jsp" %>
    <title>添加幼儿园班级</title>
</head>
<body>
<div data-role="page">
    <div data-role="content">
        <h4><font color="red">${param.tip}</font></h4>
        <c:url var="formAction" value="/garten/manage/class/add_commit"/>
        <form method="post" name="form_data" id="form_data" action="${formAction}">
            <div>
                <h4>幼儿园班级名称</h4>
            </div>
            <div>
                <input type="text" id="name" name="gartenCls.name" placeholder="请输入幼儿园班级名称" required="required">
            </div>
            <div>
                <h4>幼儿园</h4>
            </div>
            <div>
                <select name="gartenCls.kinderGarten.id">
                    <c:forEach var="garten" items="${gartenList}" varStatus="status">
                        <option value="${garten.id}">${garten.name}</option>
                    </c:forEach>
                </select>
            </div>
            <input type="submit" value="添加">
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