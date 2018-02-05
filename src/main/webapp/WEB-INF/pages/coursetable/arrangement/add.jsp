<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap-datepicker.min.css">
<link rel="stylesheet" href="${ctx }/js/datetimepicker/css/bootstrap-datetimepicker.min.css">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>添加排课信息</title>
<style type="text/css">
label[for="sex"] + .ui-flipswitch {background-color: #38c!important;border-color:#38c!important;text-shadow:0 1px 0 #059!important;color:#fff!important;}
.ui-page-theme-a label[for="sex"] + .ui-flipswitch-active{background-color: pink!important;border-color:pink!important;text-shadow:0 1px 0 pink!important;}
select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{font-size:inherit;}
.ui-flipswitch .ui-btn.ui-flipswitch-on{text-indent: -3.5em;}
.ui-flipswitch .ui-flipswitch-off{text-indent: 0.2em;}
</style>
</head>
<body>
	<s:fielderror />
	<div data-role="page">
	<div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
		<div data-role="content">
			<s:url var="formAction"
				value="/coursetable/arrangement/add_summit.action" />
			<form method="post" name="form_data" id="form_data"
				action="${formAction}">
				<input type="hidden" name="TokenName" value="${session.TokenName}">

				<div data-role="main" class="ui-content">
					<div class="ui-grid-c">
						<div class="ui-block-a" style="padding-right: 10px">
							<label for="kinderId">幼儿园<font color="RED"> <strong>*</strong></font></label> 
							<!-- 幼儿园列表 -->
							<select name="kinderId" id="kinderId" data-native-menu="false" onchange="initKinder(this)">
								<option value="-1">--请选择--</option>
								<c:if test="${not empty kindergartenInfos }">
									<c:forEach items="${kindergartenInfos }" var="info">
										<option value="${info.id }">${info.name }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
						<div class="ui-block-b" style="padding-right: 10px">
							<label for="studyClassroomId">听课教室<font color="RED"> <strong>*</strong></font></label> 
							<select name="studyClassroomId" id="studyClassroomId">
								<option value="-1">--请选择--</option>
							</select>
						</div>
						<div class="ui-block-c" style="padding-right: 10px">
							<label for="classId">听课班级<font color="RED"> <strong>*</strong></font></label> 
							<select name="classId" id="classId">
								<option value="-1">--请选择--</option>
							</select>
						</div>
						<div class="ui-block-d" style="padding-right: 10px">
							<label for="assistantId">辅助教师</label> 
							<select name="assistantId" id="assistantId">
								<option value="-1">--请选择--</option>
							</select>
						</div>
					</div>

					<br>
					<div class="ui-grid-c">
						<div class="ui-block-a" style="padding-right: 10px">
							<label for="orgId">授课机构<font color="RED"> <strong>*</strong></font></label> 
							<select name="orgId" id="orgId" data-native-menu="false" onchange="initOrg(this);">
								<option value="-1">--请选择--</option>
								<c:if test="${not empty courseOrgInfos }">
									<c:forEach items="${courseOrgInfos }" var="info">
										<option value="${info.id }">${info.name }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
						<div class="ui-block-b" style="padding-right: 10px">
							<label for="teachRoomId">授课教室<font color="RED"> <strong>*</strong></font></label> 
							<select name="teachRoomId" id="teachRoomId">
								<option value="-1">--请选择--</option>
							</select>
						</div>
						<div class="ui-block-c" style="padding-right: 10px">
							<label for="teacherId">授课教师<font color="RED"> <strong>*</strong></font></label> 
							<select name="teacherId" id="teacherId" onchange="initCourse(this);">
								<option value="-1">--请选择--</option>
							</select>
						</div>
						<div class="ui-block-d" style="padding-right: 10px">
							<label for="lessionId">课程<font color="RED"> <strong>*</strong></font></label> 
							<select name="lessionId" id="lessionId">
								<option value="-1">--请选择--</option>
							</select>
						</div>
					</div>
				</div>

				<br>
				<div class="ui-grid-a">
					<div class="ui-block-a" style="padding-right: 10px">
						<label for="courseDates">课程日期<font color="RED"> <strong>*</strong></font></label> 
						<input type="text" 
							id="courseDates" 
							name="courseDates"
							class="datepicker" 
							data-date-format="yyyy/mm/dd"
							placeholder="请输入课程开始日期" 
							required="required"
							>
					</div>

					<div class="ui-block-b" style="padding-right: 10px">
						<label for="startTime">开始时间<font color="RED"> <strong>*</strong></font></label> 
						<input type="text" 
							id="startTime" 
							name="startTime"
							class="datepicker" 
							data-date-format="hh:ii"
							placeholder="请输入开始时间" 
							required="required"
							>
					</div>
				</div>
				
				<br>
				<div class="ui-grid-a">
					<div class="ui-block-a" style="padding-right: 10px">
						<label for="couresTime">课程时长(分钟数)<font color="RED"> <strong>*</strong></font></label> 
						<input type="range" name="couresTime" id="couresTime" value="50" min="0" max="200">
					</div>
					<div class="ui-block-b" style="padding-right: 10px">
						<label for="courseState">授课状态<font color="RED"> <strong>*</strong>
						</font>
						</label> <input type="checkbox" data-role="flipswitch" name="courseState"
							id="courseState" data-off-text="未授课" data-on-text="已授完">
					</div>
				</div>
				<div class="ui-field-contain" align="center">
					<s:url var="listAction"
						value="/coursetable/arrangement/list.action" />
					<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" id="submitBtn"
						type="button">提交</button>
					<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
						type="reset">重置</button>
					<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
						type="button" id="listBtn">返回列表</button>
				</div>
			</form>
		</div>
	</div>
	
	<script src="${ctx }/js/datetimepicker/js/bootstrap.min.js"></script>
  	<script src="${ctx }/js/datetimepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
  	<script src="${ctx }/js/datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="${ctx }/js/datetimepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
  	<script type="text/javascript" src="${ctx }/js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript">
		$(function() {

			$("#listBtn").click(function() {
				$("#form_data").attr("action", "${listAction}");
				$("#form_data").submit();
			});
			
			$("#submitBtn").click(function() {
				//表单提交验证
				var kinderId = $("#kinderId").val();
				if(kinderId == -1){
					alert("请选择幼儿园信息！");
					return false;
				}
				
				var studyClassroomId = $("#studyClassroomId").val();
				if(studyClassroomId == -1){
					alert("请选择听课教室信息！");
					return false;
				}
				
				var classId = $("#classId").val();
				if(classId == -1){
					alert("请选择听课班级信息！");
					return false;
				}
				
				var orgId = $("#orgId").val();
				if(orgId == -1){
					alert("请选择授课机构信息！");
					return false;
				}
				
				var teachRoomId = $("#teachRoomId").val();
				if(teachRoomId == -1){
					alert("请选择授课教室信息！");
					return false;
				}
				
				var teacherId = $("#teacherId").val();
				if(teacherId == -1){
					alert("请选择授课教师信息！");
					return false;
				}
				
				var lessionId = $("#lessionId").val();
				if(lessionId == -1){
					alert("请选择授课课程信息！");
					return false;
				}
				
				//授课日期
				var courseDates = $("#courseDates").val();
				if(courseDates == undefined || courseDates == ''){
					alert("请填写授课日期！");
					return false;
				}
				
				var startTime = $("#startTime").val();
				if(startTime == undefined || startTime == ''){
					alert("请填写课程开始时间！");
					return false;
				}
				
				$(":submit", this).attr("disabled", true);
				$("#form_data").submit();
			});
			
			$("#form_data").submit(function() {
				$(":submit", this).attr("disabled", true);
			});

			$("#courseDates").datepicker({
				language : 'zh-CN',
				multidate: true
			});
			
			$("#startTime").datetimepicker({
				language : 'zh-CN',
				weekStart: 1,
		        todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 1,
				minView: 0,
				maxView: 1,
				forceParse: 0
		    });
		});
		
		/**初始化幼儿园下的听课教室、听课班级、辅助教师信息**/
		function initKinder(obj){
			var kinderId = $(obj).val();
			if(kinderId != '-1'){
				$.getJSON(
					"${ctx}/coursetable/arrangement/initKinder.action?kinderId=" + kinderId,
					function(data){
						
						$.each(data,function (key, jsonArray) {
							
							if(key == 'studyRooms'){
								//清空
								$("#studyClassroomId ").empty();
								$("#studyClassroomId").append("<option value='-1'>--请选择--</option>"); 
								$.each(jsonArray,function (i) {
									$("#studyClassroomId").append("<option value='"+jsonArray[i].id+"'>"+jsonArray[i].name+"</option>"); 
								});
							}
							
							if(key == 'classs'){
								//清空
								$("#classId").empty();
								$("#classId").append("<option value='-1'>--请选择--</option>"); 
								$.each(jsonArray,function (i) {
									$("#classId").append("<option value='"+jsonArray[i].id+"'>"+jsonArray[i].name+"</option>"); 
								});
							}
							
							if(key == 'assistants'){
								//清空
								$("#assistantId ").empty();
								$("#assistantId").append("<option value='-1'>--请选择--</option>"); 
								$.each(jsonArray,function (i) {
									$("#assistantId").append("<option value='"+jsonArray[i].id+"'>"+jsonArray[i].name+"</option>"); 
								});
							}
						    
						});
						  
					}
				);
			}
			
		}
		
		/**初始化授课机构下的听课教师**/
		function initOrg(obj){
			var orgId = $(obj).val();
			if(orgId != '-1'){
				$.getJSON(
					"${ctx}/coursetable/arrangement/initOrg.action?orgId=" + orgId,
					function(data){
						$.each(data,function (key, jsonArray) {
							
							if(key == 'teachRooms'){
								//清空
								$("#teachRoomId ").empty();
								$("#teachRoomId").append("<option value='-1'>--请选择--</option>"); 
								$.each(jsonArray,function (i) {
									$("#teachRoomId").append("<option value='"+jsonArray[i].id+"'>"+jsonArray[i].name+"</option>"); 
								});
							}
							
							if(key == 'teachers'){
								//清空
								$("#teacherId").empty();
								$("#teacherId").append("<option value='-1'>--请选择--</option>"); 
								$.each(jsonArray,function (i) {
									$("#teacherId").append("<option value='"+jsonArray[i].id+"'>"+jsonArray[i].name+"</option>"); 
								});
							}
						    
						});
					}
				);
			}
		}
		
		/****初始化课程***/
		function initCourse(obj){
			var teacherId = $(obj).val();
			if(teacherId != '-1'){
				$.getJSON(
						"${ctx}/coursetable/arrangement/initCourse.action?teacherId=" + teacherId,
						function(data){
							//清空
							$("#lessionId ").empty();
							$("#lessionId").append("<option value='-1'>--请选择--</option>"); 
							$.each(data,function (i) {
								$("#lessionId").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>"); 
							});
						}
					);
			}
		}
	</script>
</body>
</html>