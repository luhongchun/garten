<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>添加授课机构</title>
</head>
<body>
  <div data-role="page">
  <div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
    <div data-role="content">
      <s:url var="formAction" value="/coursetable/org/add_summit.action" />
      <form method="post" name="form_data" id="form_data" action="${formAction}">
        <input type="hidden" name="TokenName" value="${session.TokenName}">
        <div>
          <h4>授课机构名称</h4>
        </div>
        <div>
          <input type="text" id="name" name="name" placeholder="请输入授课机构名称" required="required">
        </div>
        <div>
          <h4>授课机构许可号</h4>
        </div>
        <div>
          <input type="text" id="license" name="license" placeholder="请输入授课机构许可号" required="required">
        </div>
        <div>
          <h4>授课机构的授权日期</h4>
        </div>
        <div>
          <input type="date" id="authorDate" name="authorDate" placeholder="请输入授权日期" required="required">
        </div>
        <div>
          <label for="authorState">授权状态</label>
            <select name="authorState" id="authorState" data-native-menu="false">
                <option value="0">已授权</option>
                <option value="1">未授权</option>
            </select>
        </div>
         <div>
          <h4>授课机构地址</h4>
        </div>
        <div>
          <input type="text" id="address" name="address" placeholder="请输入授课机构地址">
        </div>
        <input type="submit" value="提交">
      </form>
    </div>
  </div>
  <script type="text/javascript">
      $(function() {
        $("#form_data").submit(function() {
          $(":submit", this).attr("disabled", true);
        })
      });
    </script>
</body>
</html>