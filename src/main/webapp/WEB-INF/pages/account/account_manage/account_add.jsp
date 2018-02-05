<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>填写用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <%@ include file="/WEB-INF/includes/common-res.jsp" %>
    <script type="text/javascript" src="${ctx }/js/jquery.validate.min.js"></script>
    <style type="text/css">
        /*
        .error {
            color:red;
        }
        */
    </style>
</head>
<body>
<div data-role="page">
    <h4><font color="red">${param.tip}</font></h4>
    <s:url var="formAction" value="/account/account_manage/add_commit.action"/>
    <form method="post" name="form_data" id="form_data" action="${formAction}">
        <input type="hidden" name="openId" value="${openId}">

        <div>
            <label for="globalId" id="globalId_label">账户Id</label>
            <input type="text" name="globalId" id="globalId" placeholder="请填写GlobalId" required="required" maxlength="64">
        </div>

        <div>
            <label for="role">角色</label>
            <select name="role" id="role" data-native-menu="false">
                <option value="0">家长</option>
                <c:if test="${not isParent}">
                    <option value="1">老师</option>
                </c:if>
            </select>
        </div>

        <div>
            <label for="alias" id="alias_label">宝贝姓名</label>
            <input type="text" name="alias" id="alias" placeholder="请填写姓名" required="required" maxlength="10">
        </div>

        <div id="identity">
            <label>家长身份</label>
            <select name="identity">
                <option value="6">其他</option>
                <option value="0">爸爸</option>
                <option value="1">妈妈</option>
                <option value="2">爷爷</option>
                <option value="3">奶奶</option>
                <option value="4">姥爷</option>
                <option value="5">姥姥</option>
            </select>
        </div>

        <div>
            <label for="garden_class">班级</label>
            <select name="garden_class" id="garden_class" data-native-menu="false">
                <c:if test="${isParent}">
                    <c:forEach items="${self.gartenClasses}" var="cls">
                        <option value="${cls.id}">${cls.name}</option>
                    </c:forEach>
                </c:if>

                <c:if test="${not isParent}">
                    <c:forEach items="${classes}" var="cls">
                        <option value="${cls.id}">${cls.name}</option>
                    </c:forEach>
                </c:if>
            </select>
        </div>

        <div>
            <label for="authority">权限</label>
            <select name="authority" id="authority" data-native-menu="false">
                <option value="0">普通用户</option>
                <c:if test="${isAdmin}">
                    <option value="1">管理员</option>
                </c:if>
            </select>
        </div>

        <input type="submit" data-role="button" value="提交">
    </form>
</div>

<script type="text/javascript">
    $(function () {
        $("#role").change(function () {
            var aliasLabel = ($("#role").val() == 0 ? "宝贝姓名" : "姓名");
            $("#alias_label").html(aliasLabel);
            var showIdentity = ($("#role").val() == 0);
            if (showIdentity) {
                $("#identity").show();
            } else {
                $("#identity").hide();
            }
        });

        $('#registerForm').validate({
            rules: {
                alias: {
                    required: true,
                    maxlength: 10
                }
            },
            messages: {
                alias: {
                    required: "请填写姓名",
                    maxlength: "姓名过长，请重新填写"
                }
            },
            errorPlacement: function (error, element) {
                error.appendTo(element.parent().prev());
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
        $(":submit", this).attr("disabled", true);
    });

</script>
</body>
</html>
