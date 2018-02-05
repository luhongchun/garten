<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>

  <title>盒子sn批量入库</title>
</head>
<body>
<div data-role="page">
  <div data-role="content">
    <form method="post" name="form_data" id="form_data">
      <input type="hidden" name="TokenName" value="bf7c846b265c4e7d9e99837705016fd6">
      <div>
        <span>请选择绑定的幼儿园:</span>
        <select name="garten_sel" id="garten_sel">
          <c:forEach items="${gartenList}" var="garten" varStatus="status">
            <option value="${garten.id}">${garten.name}</option>
          </c:forEach>
        </select>
      </div>
      <div>
        <span>请选择设备使用类型:</span>
        <select name="box_biz_type" id="box_biz_type">
          <c:forEach items="${bizTypes}" var="bizType" varStatus="status">
            <option value="${bizType}">${bizType}</option>
          </c:forEach>
        </select>
      </div>
      <div>
        <h4>盒子sn（以<span style="color:#FF0000"> ; </span>分隔）</h4>
      </div>
      <div>
        <textarea name="snList" id="snList" required="required"></textarea>
      </div>
      <input type="button" value="提交" onclick="send()">
      <div id="status"></div>
    </form>
  </div>
</div>

<script type="text/javascript">
    $(function () {
        $("#form_data").submit(function () {
            $(":submit", this).attr("disabled", true);
        })
    });
    
    function send() {
        var snList = document.getElementById("snList").value;
        if (snList.length <= 0) {
            alert("总得填点什么吧？？？");
            return;
        }
        if (snList.indexOf("&") >= 0) {
            alert("包含非法字符 & ");
            return;
        }
        if (snList.indexOf("%") >= 0) {
            alert("包含非法字符 % ");
            return;
        }
        var selObj = document.getElementById("garten_sel");
        var gartenId = selObj.options[selObj.selectedIndex].value;
        selObj = document.getElementById("box_biz_type");
        var bizType = selObj.options[selObj.selectedIndex].value;

        $.ajax({
            type: "POST",
            url: "${ctx}/device/manage/box_write_sn_to_db.action",
            data: "gartenId=" + gartenId + "&bizType=" + bizType + "&snList=" + snList,
            success: function (msg) {
                alert(msg);
                document.getElementById("status").innerHTML = "执行结果：" + msg;
                if (msg.indexOf("成功") >= 0)
                    document.getElementById("snList").value = "";
            }
        });
    }
</script>
</body>
</html>