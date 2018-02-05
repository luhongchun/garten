<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>二维码信息编辑</title>
<script type="text/javascript">
    $(function() {
        //提交表单
        $("#editQrcode").bind("click", function() {
            $.ajax({
                type : "POST",
                url : "${ctx}/app/application/qrcode_edit_commit.action?",
                data : $("#editQrcodeForm").serialize(),
                success : function(msg) {
                    if (msg == 'success') {
                    	alert("二维码修改成功");
                    	window.location = '${ctx}/app/application/qrcode_list.action';
                    } else {
                        alert("二维码修改失败");
                    }
                }
            });
        });
    });
    //重置表单值
    function resetForm() {
        // $(':input', '#addQrcodeForm').not(':button, :submit, :reset').val('');
        $('#editQrcodeForm').get(0).reset();
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
            <form method="post" id="editQrcodeForm">
                <input type="hidden" name="qrcodeId" value="${request.info.id}">
                <table class="commTable">
                    <tbody>
                        <tr style="vertical-align: top;">
                            <th>二维码名称:</th>
                            <td>
                                <input type="text" name="qrcodeName" id="qrcodeName"
                                    placeholder="请输入二维码名称" value="${request.info.name}" />
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>二维码描述:</label>
                            </th>
                            <td>
                                <input type="text" name="qrcodeDesc" id="qrcodeDesc"
                                    placeholder="请输入二维码描述" value="${request.info.description}" />
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>执行动作:</label>
                            </th>
                            <td>
                                <select name="qrcodeType"
                                    id="qrcodeType">
                                    <option value="-1">未指定</option>
                                    <s:iterator value="#request.typeList" id="type">
                                        <option value="${type.id}"
                                            ${request.typeId == type.id ? 'selected=selected' : ''}>${type.name}</option>
                                    </s:iterator>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="buttonTable"
                    style="margin-bottom: 50px; margin-left: 400px; text-align: left;">
                    <div id="result"></div>
                </div>
            </form>
        </div>

        <div>
            <input type="button" id="editQrcode" value="修改" />
            <input type="reset" onclick="resetForm()" value="重置" />
        </div>
    </div>
</body>
</html>