<%@page import="com.sun.swing.internal.plaf.basic.resources.basic"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>批量录入界面</title>
</head>
<body>
  <div data-role="page">
  	<div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
  
    <div data-role="content">
      <s:url var="formAction" value="/coursetable/batch/batchInit.action" />
      <form method="post" name="form_data" id="form_data" action="${formAction}" enctype="multipart/form-data">
        <input type="hidden" name="TokenName" value="${session.TokenName}">
        
        <div>
          <h4>
          	<a href="downloadTemplate.action" target="_blank" title="下载数据导入模板"><font color="RED">下载数据导入模板</font></a>
          </h4>
        </div>
        
        <div>
          <h4>请选择您要导入的excel文档</h4>
        </div>
        <div>
          <input type="file" id="uploadFile" name="uploadFile" placeholder="请选择您要导入的excel文档" required="required">
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