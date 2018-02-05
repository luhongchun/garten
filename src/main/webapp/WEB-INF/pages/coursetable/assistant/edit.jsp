<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>编辑授课机构</title>
</head>
<body>
  <div data-role="page">
  <div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
    <div data-role="content">
      <s:url var="formEditAction" value="/coursetable/org/edit_summit.action" />
      <s:url var="formDelAction" value="/coursetable/org/del_commit.action" />
      <form method="post" name="form_data" id="form_data">
        <input type="hidden" name="TokenName" value="${session.TokenName}">
        <input type="hidden" name="id" value="${model.id}">
        <div>
          <h4>授课机构名称</h4>
        </div>
        <div>
          <input type="text" id="name" name="name" value="${model.name }" placeholder="请输入授课机构名称" required="required">
        </div>
        <div>
          <h4>授课机构许可号</h4>
        </div>
        <div>
          <input type="text" id="license" name="license" value="${model.license }" placeholder="请输入授课机构许可号" required="required">
        </div>
        <div>
          <h4>授课机构的授权日期</h4>
        </div>
        <div>
          <input type="date" id="authorDate" name="authorDate" value="${model.authorDate }" placeholder="请输入授权日期" required="required">
        </div>
        <div>
          <label for="authorState">授权状态</label>
            <select name="authorState" id="authorState" data-native-menu="false">
                <option value="0" <c:if test="${model.authorState == 0}">selected</c:if>>已授权</option>
                <option value="1" <c:if test="${model.authorState == 1}">selected</c:if>>未授权</option>
            </select>
        </div>
         <div>
          <h4>授课机构地址</h4>
        </div>
        <div>
          <input type="text" id="address" name="address" value="${model.address }" placeholder="请输入授课机构地址">
        </div>
        <div>
          <input type="button" id="delBtn" value="删除">
          <input type="button" id="editBtn" value="修改">
        </div>
      </form>
    </div>
  </div>
  <script type="text/javascript">
      $(function() {
        $("#delBtn").click(function() {
          $("#form_data").attr("action", "${formDelAction}");
          $("#form_data").submit();
        });
        $("#editBtn").click(function() {
          $("#form_data").attr("action", "${formEditAction}");
          $("#form_data").submit();
        });
        $("#form_data").submit(function() {
          $(":button", this).attr("disabled", true);
        })
      });
    </script>
</body>
</html>