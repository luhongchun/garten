<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>排课列表</title>
<style type="text/css">
label[for="sex"] + .ui-flipswitch {background-color: #38c!important;border-color:#38c!important;text-shadow:0 1px 0 #059!important;color:#fff!important;}
.ui-page-theme-a label[for="sex"] + .ui-flipswitch-active{background-color: pink!important;border-color:pink!important;text-shadow:0 1px 0 pink!important;}
select, textarea, input[type="text"], input[type="password"], input[type="datetime"], input[type="datetime-local"], input[type="date"], input[type="month"], input[type="time"], input[type="week"], input[type="number"], input[type="email"], input[type="url"], input[type="search"], input[type="tel"], input[type="color"], .uneditable-input{font-size:inherit;}
.ui-flipswitch .ui-btn.ui-flipswitch-on{text-indent: -3.5em;}
.ui-flipswitch .ui-flipswitch-off{text-indent: 0.2em;}
</style>
</head>
<body>
  <div data-role="page">
  <div style="margin:20px 0px 0px 160px;">
			<span style="color:red">${request.msg}</span>
	</div>
    <div data-role="main" class="ui-content jqm-content">
    <s:url var="formAction" value="/coursetable/arrangement/list.action" />
      <form method="post" name="form_data" id="form_data"
				action="${formAction}">
				<div class="ui-grid-c">
					<div class="ui-block-a" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="lessionss">幼儿园</label>
							<select name="kinderId" id="kinderId" data-native-menu="false" onchange="initKinder(this)">
								<option value="-1">--请选择--</option>
								<c:if test="${not empty kindergartenInfos }">
									<c:forEach items="${kindergartenInfos }" var="info">
										<option value="${info.id }"
										<c:if test="${info.id == params['kinderId'] }">selected</c:if>
										>${info.name }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="ui-block-b" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="studyClassroomId">听课教室</label>
							<select name="studyClassroomId" id="studyClassroomId">
								<option value="-1">--请选择--</option>
								<c:if test="${not empty studyClassroomInfos }">
									<c:forEach items="${studyClassroomInfos }" var="info">
										<option value="${info.id }"
										<c:if test="${info.id == params['studyClassroomId'] }">selected</c:if>
										>${info.name }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="ui-block-c" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="classId">听课班级</label>
							<select name="classId" id="classId">
								<option value="-1">--请选择--</option>
								<c:if test="${not empty classInfos }">
									<c:forEach items="${classInfos }" var="info">
										<option value="${info.id }"
										<c:if test="${info.id == params['classId'] }">selected</c:if>
										>${info.name }</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					<div class="ui-block-d" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="assistantId">辅助教师</label>
							<select name="assistantId" id="assistantId">
							<option value="-1">--请选择--</option>
							<c:if test="${not empty assistantTeacherInfos }">
									<c:forEach items="${assistantTeacherInfos }" var="info">
										<option value="${info.id }"
										<c:if test="${info.id == params['assistantId'] }">selected</c:if>
										>${info.name }</option>
									</c:forEach>
								</c:if>
						</select>
						</div>
						
					</div>
			</div>
			
			<div class="ui-grid-c">
						<div class="ui-block-a" style="padding-right: 10px">
							<div class="ui-field-contain">
								<label for="orgId">授课机构</label> 
								<select name="orgId" id="orgId" data-native-menu="false" onchange="initOrg(this);">
									<option value="-1">--请选择--</option>
									<c:if test="${not empty courseOrgInfos }">
										<c:forEach items="${courseOrgInfos }" var="info">
											<option value="${info.id }"
											<c:if test="${info.id == params['courseOrgInfos'] }">selected</c:if>
											>${info.name }</option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>
						<div class="ui-block-b" style="padding-right: 10px">
							<div class="ui-field-contain">
								<label for="teachRoomId">授课教室</label> 
								<select name="teachRoomId" id="teachRoomId">
									<option value="-1">--请选择--</option>
									<c:if test="${not empty teachClassroomInfos }">
									<c:forEach items="${teachClassroomInfos }" var="info">
										<option value="${info.id }"
										<c:if test="${info.id == params['teachRoomId'] }">selected</c:if>
										>${info.name }</option>
									</c:forEach>
								</c:if>
								</select>
							</div>
						</div>
						<div class="ui-block-c" style="padding-right: 10px">
							<div class="ui-field-contain">
								<label for="teacherId">授课教师</label> 
								<select name="teacherId" id="teacherId" onchange="initCourse(this);">
									<option value="-1">--请选择--</option>
									<c:if test="${not empty teacherInfos }">
									<c:forEach items="${teacherInfos }" var="info">
										<option value="${info.id }"
										<c:if test="${info.id == params['teacherId'] }">selected</c:if>
										>${info.name }</option>
									</c:forEach>
								</c:if>
								</select>
							</div>
						</div>
						<div class="ui-block-d" style="padding-right: 10px">
							<div class="ui-field-contain">
								<label for="lessionId">课程</label> 
								<select name="lessionId" id="lessionId">
									<option value="-1">--请选择--</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			
			<div class="ui-grid-c">
					<div class="ui-block-a" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="courseDate">课程日期</label>
							<input type="text" 
							id="courseDate" 
							name="courseDate"
							class="datepicker" 
							data-date-format="yyyy/mm/dd"
							placeholder="请输入课程开始日期" 
							required="required"
							value="${ params['courseDate']}"
							>
						</div>
					</div>
					<div class="ui-block-b" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="startTime">开始时间</label>
							<input type="text" 
							id="startTime" 
							name="startTime"
							class="datepicker" 
							data-date-format="hh:ii"
							placeholder="请输入开始时间" 
							required="required"
							value="${params['startTime'] }"
							>
						</div>
					</div>
					<div class="ui-block-c" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="couresTime">课程时长(分钟数)</label> 
							<input type="range" name="couresTime" id="couresTime" value="${params['couresTime'] == null ? 0 : params['couresTime'] }" min="0" max="200">
						</div>
					</div>
					<div class="ui-block-d" style="padding-right: 10px">
						<div class="ui-field-contain">
							<label for="courseState">授课状态</label> 
							<input type="checkbox" data-role="flipswitch" name="courseState"
							id="courseState" data-off-text="未授课" data-on-text="已授完"
							<c:if test="${params['courseState'] == 'on' }">
							checked="checked"
							</c:if>
							>
						</div>
					</div>
			</div>
			
			<div class="ui-field-contain" align="center">
			    	<s:url var="addAction" value="/coursetable/arrangement/add.action" />
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all ui-icon-search ui-btn-icon-left" type="submit">搜索</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="reset">重置</button>
			    	<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="addBtn" >添加排课</button>
				</div>
	 </form>
      
      
      <table data-role="table" data-mode="columntoggle" class="ui-responsive ui-shadow" id="myTable">
      <thead>
        <tr>
          <th data-priority="9">授课教师</th>
          <th data-priority="1">课程名称</th>
          <th data-priority="2">授课教室</th>
          <th data-priority="3">幼儿园</th>
          <th data-priority="4">听课教室</th>
          <th data-priority="5">辅助教师</th>
          <th data-priority="6">开始时间</th>
          <th data-priority="7">授课时长</th>
          <th data-priority="8">授课状态</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
      	<c:if test="${not empty pv }">
      		<c:forEach items="${pv.datas }" var="info">
      			 <tr>
		          <td>${info.teacherInfo.name }</td>
		          <td>${info.lessonInfo.name }</td>
		          <td>${info.teachClassroomInfo.name }</td>
		          <td>${info.kindergartenInfo.name }</td>
		          <td>${info.studyClassroomInfo.name }</td>
		          <td>${info.assistantTeacherInfo.name }</td>
		          <td><fmt:formatDate value='${info.courseDate }' pattern='yyyy/MM/dd'/>&nbsp;${info.startTime }</td>
		          <td>${info.couresTime }(分钟)</td>
		          <td>  
		          	<c:if test="${info.courseState == 0 }">未授课</c:if>
		          	<c:if test="${info.courseState == 1 }">已授课</c:if>
		          </td>
		          <td><a href="javascript:if(confirm('确认删除该数据吗?'))window.location='${ctx }/coursetable/arrangement/del_commit.action?id=${info.id }'">删除</a></td>
		        </tr>
      		</c:forEach>
      	</c:if>
      </tbody>
    </table>
    
    <c:if test="${not empty pv }">
	    <div data-role="controlgroup" data-type="horizontal" align="center">
			 <a href="javascript:void(0);" class="ui-btn">当前共 <font color="RED">${pv.totol }</font> 条数据，当前第<font color="RED"> ${params['currentPager'] + 1 }</font> 页，共<font color="RED"> ${params['totalPage'] }</font> 页</a>
			 <a href="javascript:void(0);" class="ui-btn" onclick="firstPage('${params['currentPager']}');">首页</a>
			 <a href="javascript:void(0);" class="ui-btn" onclick="previousPage('${params['currentPager']}');">上一页</a>
			 <a href="javascript:void(0);" class="ui-btn" onclick="nextPage('${params['currentPager']}');">下一页</a>
			 <a href="javascript:void(0);" class="ui-btn" onclick="lastPage('${params['totalPage']}');">尾页</a>
		 </div>
    </c:if>
    </div>
  </div>
  
  <script src="${ctx }/js/datetimepicker/js/bootstrap.min.js"></script>
  <script src="${ctx }/js/datetimepicker/js/bootstrap-datepicker.min.js" charset="UTF-8"></script>
  <script src="${ctx }/js/datetimepicker/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
  <script type="text/javascript" src="${ctx }/js/datetimepicker/locales/bootstrap-datepicker.zh-CN.min.js" charset="UTF-8"></script>
  <script type="text/javascript" src="${ctx }/js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
  <script type="text/javascript">
		$(function() {

			$("#addBtn").click(function() {
				$("#form_data").attr("action", "${addAction}");
				$("#form_data").submit();
			});
			$("#form_data").submit(function() {
				$(":submit", this).attr("disabled", true);
			});

			$("#courseDate").datepicker({
				language : 'zh-CN'
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
		
		/**首页**/
		function firstPage(currentPager){
			window.location.href = "${ctx }/coursetable/arrangement/list.action";
		}
		
		/**上一页**/
		function previousPage(currentPager){
			if(currentPager == 0){
				firstPage(currentPager);
			}else{
				window.location.href = "${ctx }/coursetable/arrangement/list.action?currentPager=" + (Number(currentPager) - 1);
			}
		}
		
		/**下一页**/
		function nextPage(currentPager){
			var totalPage = "${params['totalPage']}";
			if((Number(currentPager)+1) >= totalPage){
				lastPage(totalPage);
			}else{
				window.location.href = "${ctx }/coursetable/arrangement/list.action?currentPager=" + (Number(currentPager) + 1);
			}
		}
		
		/**尾页**/
		function lastPage(totalPage){
			window.location.href = "${ctx }/coursetable/arrangement/list.action?currentPager=" + (totalPage-1);
		}
	</script>
  
</body>
</html>