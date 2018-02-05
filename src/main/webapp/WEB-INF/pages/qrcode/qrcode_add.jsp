<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<head>
<title>二维码信息添加</title>
<script type="text/javascript">
    $(function() {
        //提交表单
        $("#addQrcode").bind("click", function() {
            if (checkFormInput()) {
                $.ajax({
                    type : "POST",
                    url : "${ctx}/app/application/qrcode_commit.action?",
                    data : $("#addQrcodeForm").serialize(),
                    success : function(msg) {
                        if (msg == 'success') {
                            resetForm();
                            alert("二维码添加成功");
                        } else {
                            alert("二维码添加失败");
                        }
                    }
                });
            }
        });

        $('#qrcodeBatch').bind('input propertychange', function() {
            /*if ($(this).val() > 1) {
                $('#qrcodeName').attr("disabled", true);
                $('#qrcodeDesc').attr("disabled", true);
            } else {
                $('#qrcodeName').attr("disabled", false);
                $('#qrcodeDesc').attr("disabled", false);
            }*/
        });
    });

    function checkFormInput() {
        if (!/^\d{0,3}$/.test($("#qrcodeBatch").val())) {
            $.alert("批量操作1 - 999");
            return false;
        }
        return true;
    }
    //清空表单值
    function resetForm() {
        // $(':input', '#addQrcodeForm').not(':button, :submit, :reset').val('');
        $('#addQrcodeForm').get(0).reset();
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
            <form method="post" id="addQrcodeForm">
                <table class="commTable">
                    <tbody>
                        <tr style="vertical-align: top;">
                            <th><label>二维码名称:</label></th>
                            <td>
                                <input type="text" name="qrcodeName" id="qrcodeName" placeholder="请输入二维码名称"/>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>二维码描述:</label>
                            </th>
                            <td>
                                <input type="text" name="qrcodeDesc" id="qrcodeDesc" placeholder="请输入二维码描述"/>
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
                                        <option value="${type.id}">${type.name}</option>
                                    </s:iterator>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>
                                <label>批量操作:</label>
                            </th>
                            <td>
                                <input type="text" pattern="\d{1,3}" value="1" min="1"  placeholder="请输入批量操作数量"
                                    id="qrcodeBatch" max="999" name="qrcodeBatch" required/>
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

        <div class="buttonTable">
            <input class="v_button" type="button" id="addQrcode" value="添加" />
            <input class="v_button" type="reset" onclick="resetForm()" value="重置" />
        </div>
    </div>
</body>
</html>