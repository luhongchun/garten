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
      <s:url var="formEditAction" value="/coursetable/arrangement/edit_summit.action" />
      <s:url var="formDelAction" value="/coursetable/arrangement/del_commit.action" />
      <form method="post" name="form_data" id="form_data">
        <input type="hidden" name="TokenName" value="${session.TokenName}">
        <input type="hidden" name="id" value="${model.id}">
        
        <div>
          <h4>课程名称</h4>
        </div>
        <div>
          <input type="text" id="name" name="name" value="${model.name }" placeholder="请输入课程名称" required="required">
        </div>
        <div>
          <h4>课程日期</h4>
        </div>
        <div>
          <input type="date" id="courseDate" name="courseDate" value="${courseDate }" placeholder="请输入课程日期" required="required">
        </div>
        <div>
          <h4>课程开始时间</h4>
        </div>
        <div>
          <input type="datetime" id="startDatetime" value="${model.startDatetime }" name="startDatetime" placeholder="请输入课程开始时间" required="required">
        </div>
        <div>
          <h4>课程时长</h4>
        </div>
        <div>
          <input type="text" id="couresTime" value="${model.couresTime }" name="couresTime" placeholder="请输入课程时长">
        </div>
        <div>
          <label for="authorState">授课状态</label>
            <select name="sourseState" id="sourseState" data-native-menu="false">
                <option value="0" <c:if test="${model.sourseState == 0 }">selected</c:if>>未授课</option>
                <option value="1" <c:if test="${model.sourseState == 1 }">selected</c:if>>已授课</option>
            </select>
        </div>
        
        <div>
          <h4>授课教师</h4>
        </div>
        <div>
          <select name="teacherInfo.id" id="teacherInfo.id" data-native-menu="false">
          		<c:if test="${not empty teacherInfos }">
          	   		<c:forEach items="${teacherInfos }" var="info">
						<option value="${info.id }" <c:if test="${info.id == model.teacherInfo.id}">selected</c:if> >${info.name }</option>   		
          	   		</c:forEach>
          	   	</c:if>
          </select>
        </div>
        
        <div>
          <h4>授课教室</h4>
        </div>
        <div>
          <select name="teachClassroomInfo.id" id="teachClassroomInfo.id" data-native-menu="false">
          		<c:if test="${not empty teachClassroomInfos }">
          	   		<c:forEach items="${teachClassroomInfos }" var="info">
						<option value="${info.id }" <c:if test="${info.id == model.teachClassroomInfo.id}">selected</c:if> >${info.name }</option>   		
          	   		</c:forEach>
          	   	</c:if>
          </select>
        </div>
        
        <div>
          <h4>听课幼儿园</h4>
        </div>
        <div>
          <select name="kindergartenInfo.id" id="kindergartenInfo.id" data-native-menu="false">
          		<c:if test="${not empty kindergartenInfos }">
          	   		<c:forEach items="${kindergartenInfos }" var="info">
						<option value="${info.id }" <c:if test="${info.id == model.kindergartenInfo.id}">selected</c:if> >${info.name }</option>   		
          	   		</c:forEach>
          	   	</c:if>
          </select>
        </div>
        
        <div>
          <h4>听课教室</h4>
        </div>
        <div>
          <select name="studyClassroomInfo.id" id="studyClassroomInfo.id" data-native-menu="false">
          		<c:if test="${not empty studyClassroomInfos }">
          	   		<c:forEach items="${studyClassroomInfos }" var="info">
						<option value="${info.id }" <c:if test="${info.id == model.studyClassroomInfo.id}">selected</c:if> >${info.name }</option>   		
          	   		</c:forEach>
          	   	</c:if>
          </select>
        </div>
        
        <div>
          <h4>听课班级</h4>
        </div>
        <div>
          <select name="classInfo.id" id="classInfo.id" data-native-menu="false">
          		<c:if test="${not empty classInfos }">
          	   		<c:forEach items="${classInfos }" var="info">
						<option value="${info.id }" <c:if test="${info.id == model.classInfo.id}">selected</c:if> >${info.name }</option>   		
          	   		</c:forEach>
          	   	</c:if>
          </select>
        </div>
        
        <div>
          <h4>辅助教师</h4>
        </div>
        <div>
          <select name="assistantTeacherInfo.id" id="assistantTeacherInfo.id" data-native-menu="false">
          		<option>-请选择-</option>
          		<c:if test="${not empty assistantTeacherInfos }">
          	   		<c:forEach items="${assistantTeacherInfos }" var="info">
						<option value="${info.id }" <c:if test="${info.id == model.assistantTeacherInfo.id}">selected</c:if> >${info.name }</option>   		
          	   		</c:forEach>
          	   	</c:if>
          </select>
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