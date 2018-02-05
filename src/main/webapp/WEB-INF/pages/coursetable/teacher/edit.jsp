<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap-datepicker.min.css">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>

<title>编辑授课教师</title>
<style type="text/css">
label[for="sex"] + .ui-flipswitch {background-color: #38c!important;border-color:#38c!important;text-shadow:0 1px 0 #059!important;color:#fff!important;}
.ui-page-theme-a label[for="sex"] + .ui-flipswitch-active{background-color: pink!important;border-color:pink!important;text-shadow:0 1px 0 pink!important;}
</style>
</head>
<body>
	<div data-role="page">
	
    	<div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
		</div>
    	
	
		<div data-role="content">
			<s:url var="formEditAction" value="/coursetable/teacher/edit_submit.action" />
      		<s:url var="formDelAction" value="/coursetable/teacher/del_commit.action" />
			<form method="post" name="form_data" id="form_data">
				<input type="hidden" name="TokenName" value="${session.TokenName}">
				<input type="hidden" name="id" value="${model.id}">
				<input type="hidden" name="teacherId" value="${model.id}">
				
				<div class="ui-field-contain">
					<label for="name">教师名称<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="name" name="name" placeholder="请输入教师名称"
						required="required" data-clear-btn="true" value="${model.name }">
				</div>
				<div class="ui-field-contain">
					<label for="sex">性别<font color="RED"> <strong>*</strong></font></label>
					<input type="checkbox" data-role="flipswitch" name="sex" id="sex"
						data-off-text="男" data-on-text="女" 
						<c:if test="${model.sex == '女' }">
							checked="checked"
						</c:if>
					>
				</div>
				<div class="ui-field-contain">
					<label for="orgId">授课机构<font color="RED"> <strong>*</strong></font></label>
					<select name="orgId" id="orgId" data-native-menu="false">
						<c:if test="${not empty courseOrgInfos }">
		          	   		<c:forEach items="${courseOrgInfos }" var="info">
								<option value="${info.id }" <c:if test="${info.id == model.org.id }">selected</c:if> >${info.name }</option>   		
		          	   		</c:forEach>
		          	   	</c:if>
					</select>
				</div>
				<div class="ui-field-contain">
					<label for="idNum">身份证号<font color="RED"> <strong>*</strong></font></label>
					<input 
						type="text" 
						id="idNum" 
						name="idNum"
						value="${model.idNum }"
						pattern="(^\d{15}$)|(^\d{17}([Xx])$)|(^\d{18}$)" 
						placeholder="请输入正确的身份证号码" 
						required="required" 
						form="form_data" 
						data-clear-btn="true">
				</div>
				<div class="ui-field-contain">
					<label for="startWorkDate">开始工作时间<font color="RED"> <strong>*</strong></font></label>
					<input 
						type="text" 
						id="startWorkDate" 
						name="startWorkDate"
						class="datepicker"
						data-date-format="yyyy/mm/dd"
						placeholder="请输入开始工作时间" 
						required="required" 
						value="<fmt:formatDate value='${model.startWorkDate }' pattern='yyyy/MM/dd'/>"
						>
				</div>
				<div class="ui-field-contain">
					<label for="phone">联系手机号码<font color="RED"> <strong>*</strong></font></label>
					<input type="tel" id="phone" name="phone"
						pattern="^0{0,1}(13[0-9]|15[0-9]|17[0-9]|18[0-9])[0-9]{8}$"
						placeholder="请输入联系手机号码" required="required" data-clear-btn="true" value="${model.phone }">
				</div>
				<div class="ui-field-contain">
					<label for="email">邮箱地址</label>
					<input type="email" id="email" name="email" placeholder="请输入邮箱地址" data-clear-btn="true" value="${model.email }">
				</div>
				<div class="ui-field-contain">
					<label for="workState">是否在职<font color="RED"> <strong>*</strong></font></label>
					<input type="checkbox" data-role="flipswitch" name="workState" id="workState"
						data-off-text="否" data-on-text="是"
						<c:if test="${model.workState == 0 }">
							checked="checked"
						</c:if>
						>
				</div>
				<!-- 
				<div class="ui-field-contain">
					<label for="lessionss">授课课程</label>
					<textarea name="lessionss" placeholder="课程之间用‘；’分隔，如：语文；数学">${model.lessonInfo }</textarea>
				</div>
				 -->
			    <div class="ui-field-contain" align="center">
			    	<s:url var="listAction" value="/coursetable/teacher/list.action" />
			    	<s:url var="listLessionAction" value="/coursetable/teacher/listLessions.action" />
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" id="editBtn">更新</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" id="delBtn">删除</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="listLessionBtn" >查看课程列表</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="listBtn" >返回列表</button>
				</div>
			</form>
		</div>
	</div>
  <script src="${ctx }/js/datetimepicker/js/bootstrap.min.js"></script>
  <script src="${ctx }/js/datetimepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
  <script type="text/javascript" src="${ctx }/js/datetimepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
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
        });
        
        $("#listBtn").click(function() {
	          $("#form_data").attr("action", "${listAction}");
	          $("#form_data").submit();
	    });
        
        $("#listLessionBtn").click(function() {
	          $("#form_data").attr("action", "${listLessionAction}");
	          $("#form_data").submit();
	    });
        
        $('.datepicker').datepicker({
        	 language:  'zh-CN'
		});
      });
    </script>
</body>
</html>