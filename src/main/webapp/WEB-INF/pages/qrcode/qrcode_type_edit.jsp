<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>二维码类型编辑</title>
<script type="text/javascript">
  $(function() {
    //提交表单
    $("#commit").bind("click", function() {
      if (checkFormInput()) {
        $.ajax({
          type : "POST",
          url : "${ctx}/app/application/qrcode_type_edit_commit.action?",
          data : $("#editForm").serialize(),
          success : function(msg) {
            if (msg == 'success') {
                alert("二维码类型编辑成功");
                window.location = "${ctx}/app/application/qrcode_type_list.action";
            } else {
                alert("二维码类型编辑失败");
            }
          }
        });
      }
    });
  });

  function checkFormInput() {
    return true;
  }

  //清空表单值
  function resetForm() {
    // $(':input', '#editForm').not(':button, :submit, :reset').val('');
    $('#editForm').get(0).reset();
  }
</script>
<style type="text/css">
div.demo {
  padding: 20px;
  border: 1px solid #E5E5E5;
  margin-bottom: 20px;
  background-color: #FFFFFF;
}
</style>
</head>
<body style="height: 100%">
  <div>
    <div style="height:40px;">
    </div>
    <div>
        <form method="post" id="editForm">
          <input type="hidden" name="typeId" value="${request.type.id}">
          <table class="commTable">
            <tbody>
              <tr style="vertical-align: top;">
                <th>类型名称:</th>
                <td>
                  <input type="text" name="typeName" id="typeName" placeholder="请输入类型名称"
                    value="${request.type.name}" required />
                </td>
              </tr>
              <tr>
                <th>
                  <label>类型描述:</label>
                </th>
                <td>
                  <input type="text" name="typeDesc" id="typeDesc" placeholder="请输入类型描述"
                    value="${request.type.description}" />
                </td>
              </tr>
              <tr>
                <th>
                  <label>编辑地址:</label>
                </th>
                <td>
                  <input type="text" placeholder="请输入数据编辑地址" id="typeEditUrl" name="typeEditUrl"
                    value="${request.type.editUrl}" required />
                </td>
              </tr>
              <tr>
                <th>
                  <label>二维码处理地址:</label>
                </th>
                <td>
                  <input type="text" placeholder="请输入数据编辑地址" id="typeDealUrl" name="typeDealUrl"
                    value="${request.type.dealUrl}" required />
                </td>
              </tr>
            </tbody>
          </table>
          <div class="buttonTable" style="margin-bottom: 50px; margin-left: 400px; text-align: left;">
            <div id="result"></div>
          </div>
        </form>
      </div>

      <div class="buttonTable">
        <input class="v_button" type="button" id="commit" value="修改" />
        <input class="v_button" type="reset" onclick="resetForm()" value="重置" />
      </div>
  </div>
</body>
</html>