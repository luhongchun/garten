<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <title>设置摄像机</title>
</head>

<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <form id="dataForm">
      <input type="hidden" name="cameraId" id="cameraId" value="${cameraId}">
      <select name="cameraName" id="cameraName">
        <c:if test="${not empty classes}">
          <c:forEach var="cls" items="${classes}" step="1" varStatus="status">
            <option value="${cls.name}">${cls.name}</option>
          </c:forEach>
        </c:if>
      </select>
      <input type="button" id="setup" value="保存">
    </form>
  </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#setup').click(function () {
            $('#setup').attr('disabled', "true");
            $.ajax({
                type: "POST",
                url: "${ctx}/device/manage/camera_set_name_commit",
                data: $('#dataForm').serialize(),
                success: function (msg) {
                    if (msg == 'success') {
                        $('#dataForm')[0].reset();
                        alert("保存成功");
                    } else {
                        alert("保存失败");
                    }
                }
            });
        });
    });
</script>
</body>
</html>
