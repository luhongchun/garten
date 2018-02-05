<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>编辑班级</title>
</head>
<body>
  <div data-role="page">
  <div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
  
    <div data-role="content">
      <s:url var="formEditAction" value="/coursetable/class/edit_summit.action" />
      <s:url var="formDelAction" value="/coursetable/class/del_commit.action" />
      <form method="post" name="form_data" id="form_data">
        <input type="hidden" name="TokenName" value="${session.TokenName}">
        <input type="hidden" name="id" value="${model.id}">
        
         <div class="ui-field-contain">
			<label for="name">班级名称<font color="RED"> <strong>*</strong></font></label>
			<input type="text" id="name" name="name" placeholder="请输入班级名称"
				required="required" data-clear-btn="true" value="${model.name }">
		</div>
		<div class="ui-field-contain">
			<label for="description">班级描述</label>
			<input type="text" id="description" name="description" placeholder="请输入班级名称" data-clear-btn="true" value="${model.description }">
		</div>
		<div class="ui-field-contain">
			<label for="kinderId">归属幼儿园<font color="RED"> <strong>*</strong></font></label>
			 <select name="kinderId" id="kinderId" data-native-menu="false">
          		<c:if test="${not empty kindergartenInfos }">
          	   		<c:forEach items="${kindergartenInfos }" var="info">
						<option value="${info.id }" <c:if test="${info.id == model.kindergartenInfo.id}">selected</c:if> >${info.name }</option>   		
          	   		</c:forEach>
          	   	</c:if>
          	</select>
		</div>
        
        <div>
          <div class="ui-field-contain" align="center">
	    	<s:url var="listAction" value="/coursetable/class/list.action" />
	    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" id="editBtn">修改</button>
	    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" id="delBtn">删除</button>
	    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="listBtn" >返回列表</button>
		</div>
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
        $("#listBtn").click(function() {
	          $("#form_data").attr("action", "${listAction}");
	          $("#form_data").submit();
	    });
        $("#form_data").submit(function() {
          $(":button", this).attr("disabled", true);
        })
      });
    </script>
</body>
</html>