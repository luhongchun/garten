<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>二维码类型添加</title>
<script type="text/javascript">
    $(function() {
        //提交表单
        $("#commit").bind("click", function() {
            if (checkFormInput()) {
                $.ajax({
                    type : "POST",
                    url : "${ctx}/app/application/qrcode_type_commit.action?",
                    data : $("#commitForm").serialize(),
                    success : function(msg) {
                        if (msg == 'success') {
                            resetForm();
                            alert("二维码类型添加成功");
                        } else {
                            alert("二维码类型添加失败");
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
        // $(':input', '#commitForm').not(':button, :submit, :reset').val('');
        $('#commitForm').get(0).reset();
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
           <form method="post" id="commitForm">
               <table class="commTable">
                   <tbody>
                       <tr style="vertical-align: top;">
                           <th><label>类型名称:</label></th>
                           <td>
                               <input type="text" name="typeName" id="typeName" placeholder="请输入类型名称" required/>
                           </td>
                       </tr>
                       <tr>
                           <th>
                               <label>类型描述:</label>
                           </th>
                           <td>
                               <input type="text" name="typeDesc" id="typeDesc" placeholder="请输入类型描述"/>
                           </td>
                       </tr>
                       <tr>
                           <th>
                               <label>编辑地址:</label>
                           </th>
                           <td>
                               <input type="text" placeholder="请输入数据编辑地址"
                                   id="typeEditUrl" name="typeEditUrl" required/>
                           </td>
                       </tr>
                       <tr>
                           <th>
                               <label>二维码处理地址:</label>
                           </th>
                           <td>
                               <input type="text" placeholder="请输入数据编辑地址"
                                   id="typeDealUrl" name="typeDealUrl" required/>
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
           <input class="v_button" type="button" id="commit" value="添加" />
           <input class="v_button" type="reset" onclick="resetForm()" value="重置" />
       </div>
    </div>
</body>
</html>