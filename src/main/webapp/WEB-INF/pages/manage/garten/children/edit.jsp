<%@ page import="cn.smart.cloud.biz.opadmin.entity.SexType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>编辑学生</title>
</head>
<body>
	<div data-role="page">
		<div style="margin: 20px 0px 0px 160px;">
			<span style="color: red">${request.msg}</span>
		</div>

		<div data-role="content">
			<c:url var="formEditAction" value="/garten/manage/child/edit_submit" />
            <c:url var="formDelAction" value="/garten/manage/child/del_submit" />
            <c:url var="formListAction" value="/garten/manage/child/list" />
			<form method="post" name="form_data" id="form_data">
				<input type="hidden" name="TokenName" value="${session.TokenName}">
				<input type="hidden" name="classId" value="${classId}">
				<input type="hidden" name="mId" value="${mId}">
				<input type="hidden" name="childId" value="${child.id }">

				<div class="ui-field-contain">
					<label for="name">名称<font color="RED"> <strong>*</strong></font></label>
					<input type="text" id="name" name="name" placeholder="请输入名称"
						required="required" data-clear-btn="true" value="${child.aliasName }">
				</div>
				
				<div class="ui-field-contain">
					<c:set var="male" value="<%=SexType.MALE%>" />
					<c:set var="female" value="<%=SexType.FEMALE%>" />
					<label><input type="radio" name="sex" value="MALE" <c:if test="${sex == male}">checked="checked"</c:if>>男</label>
					<label><input type="radio" name="sex" value="FEMALE" <c:if test="${sex == female}">checked="checked"</c:if>>女</label>
				</div>

				<div class="ui-field-contain" style="display:none">
					<label for="kinderId">归属班级<font color="RED"> <strong>*</strong></font></label>
					<select name="gClassId" id="gClassId" data-native-menu="false">
						<c:if test="${not empty gClasses }">
							<c:forEach items="${gClasses }" var="gClass">
								<option value="${gClass.id }"
									<c:if test="${gClass.id == classId}">selected</c:if>>
									${gClass.name }</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
				<div>
                	<h4>家长列表</h4>
            	</div>
				<div>
					<ul data-role="listview" data-inset="true">
						<c:forEach items="${parents }" var="parent" varStatus="status">
							<li><a data-ajax="false" href="javascript:;" onclick="ep('${parent.id}')">${empty parent.aliasName ? '未填写' : parent.aliasName}</a></li>
						</c:forEach>
					</ul>
				</div>
				<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
					type="button" id="addParentBtn">添加家长</button>
				<c:if test="${bizCloseup }">
				<div>
                	<h4>视频列表</h4>
            	</div>
				<div>
					<ul data-role="listview" data-inset="true">
						<c:forEach items="${videos }" var="video" varStatus="status">
							<li><a data-ajax="false" href="javascript:;" onclick="ev('${video.id}')">${empty video.videoDesc ? '未填写' : video.videoDesc}</a></li>
						</c:forEach>
					</ul>
				</div>
				<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
					type="button" id="addVideoBtn">添加视频</button>
				</c:if>
				<div>
					<div class="ui-field-contain" align="center">
						<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
							id="editBtn">修改</button>
						<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
							id="delBtn">删除</button>
						<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
							id="listBtn">返回列表</button>
					</div>
				</div>
			</form>
			<c:if test="${bizCloseup }">
			<c:url var="uploadFaceAction" value="/garten/manage/child/face_add" />
			<form method="post" name="face_data" id="face_data" enctype="multipart/form-data">
				<input type="hidden" name="childId" value="${child.id }">
				<input type="hidden" name="mId" value="${mId}">
				<div class="ui-field-contain">
					<label for="name">头像</label>
					<img id="face" src="${faceImage }" height="200" width="150" alt="图片显示区"/>
					<p><span id="image_size"></span></p>
					<input type="file" id="file" name="file" value="file" onchange="updateData(this)">
					<button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
							type="button" id="uploadBtn">更新头像</button>
				</div>
			</form>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#delBtn").click(function() {
				$("#form_data").attr("action", "${formDelAction}");
				$("#form_data").submit();
				/* new submitForm('${ctx}/garten/manage/child/del_submit}', { childId:'${child.id }' }).post(); */
			});
			$("#editBtn").click(function() {
				$("#form_data").attr("action", "${formEditAction}");
				$("#form_data").submit();
				/* new submitForm('${ctx}/garten/manage/child/edit_submit}', { childId:'${child.id }' }).post(); */
			});
			$("#listBtn").click(function() {
				$("#form_data").attr("action", "${formListAction}");
				$("#form_data").submit();
			});
			$("#form_data").submit(function() {
				$(":button", this).attr("disabled", true);
			});
			$("#addParentBtn").click(function() {
				new submitForm('${ctx}/garten/manage/parent/add', { childId:'${child.id }', mId:'${mId }' }).post();
			});
			$("#addVideoBtn").click(function() {
				new submitForm('${ctx}/garten/manage/child/video_add', { childId:'${child.id }', mId:'${mId }' }).post();
			});
			$("#uploadBtn").click(function() {
				//new submitForm('${ctx}/garten/manage/child/face_add', { childId:'${child.id }' }).post();
				$("#face_data").attr("action", "${uploadFaceAction}");
				$("#face_data").submit();
			});
		});

		function submitForm(url, data) {
		    var eleForm = document.body.appendChild(document.createElement('form'));
		    eleForm.action = url;
		    for (var property in data) {
		        var hiddenInput = document.createElement('input');
		        hiddenInput.type = 'hidden';
		        hiddenInput.name = property;
		        hiddenInput.value = data[property];
		        eleForm.appendChild(hiddenInput);
		    }
		    this.eleForm = eleForm;
		    if (!submitForm._initialized) {
		        submitForm.prototype.post = function () {
		            this.eleForm.method = 'post';
		            this.eleForm.submit();
		        };
		      submitForm._initialized = true;
		    }
		}

		function ep(parentId) {
			new submitForm('${ctx}/garten/manage/parent/edit', { parentId:parentId, childId:'${child.id }', mId:'${mId }' }).post();
		}

		function ev(videoId) {
			new submitForm('${ctx}/garten/manage/child/video_edit', { videoId:videoId, childId:'${child.id }', mId:'${mId }' }).post();
		}

		function updateData(input) {
			var params = {};
			var nBytes =input.files[0].size;
			var path =input.value;
			var sOutput = nBytes + "Bytes";

			document.getElementById("image_size").innerHTML = "文件大小："+nBytes+" 字节";
			params["size"] = sOutput;
			params["path"] = path;
			if (input.files && input.files[0]) {
			    var reader = new FileReader();
			    reader.onload = function (e) {
			      $('#face')
			        .attr('src', e.target.result);
			    };
			}
	        var URL = window.URL || window.webkitURL;
	        var imgURL = URL.createObjectURL(input.files[0]);
			reader.readAsDataURL(input.files[0]);
		}
	</script>
</body>
</html>