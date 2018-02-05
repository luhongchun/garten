<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/includes/common-res.jsp"%>
    <title>孩子视频</title>
	<link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="//v3.bootcss.com/assets/css/docs.min.css">
	<style type="text/css">
	.text-danger{
		color: red;
	}
	.control-label{
		text-align: left !important; 
	}
	#resultBox {
		width: 100%;
		height: 300px;
		border: 1px solid #888;
		padding: 5px;
		overflow: auto;
		margin-bottom: 20px;
	}
	.uploaderMsgBox {
		width: 100%;
		border-bottom: 1px solid #888;
	}
	[act=cancel-upload]{
		text-decoration: none;
		cursor:pointer;
	}
	</style>
</head>
<body>
<div data-role="page">
        <div style="margin: 20px 0px 0px 160px;">
            <span style="color: red">${request.msg}</span>
        </div>

<div class="bs-docs-header" id="content">
	<div class="container">
		<h1>VOD Video Upload</h1>
	</div>
</div>
<div class="container bs-docs-container">
	<!--<div class="row" style="padding:10px;">
		<p>
		1.示例中的签名是直接从demo后台获取签名。<br>
		2.示例1点击“直接上传视频”按钮即可上传视频。<br>
		3.示例2点击“添加视频”添加视频文件，点击“添加封面”添加封面文件，然后点击“上传视频和封面”按钮即可上传视频和封面。<br>
		4.取消上传为取消上传中的视频，上传成功的视频不能取消上传。

		</p>
	</div>-->
	

	<form id="form1">
		<input id="uploadVideoNow-file" type="file" style="display:none;"/>
	</form>
	<div class="row" style="padding:10px;">
		<h4>直接上传视频</h4>
		<a id="uploadVideoNow" href="javascript:void(0);" class="btn btn-outline">直接上传视频</a>
	</div>

	<form id="form2">
		<input id="addVideo-file" type="file" style="display:none;"/>
		<input id="addCover-file" type="file" style="display:none;"/>
	</form>
	<div class="row" style="padding:10px;">
		<h4>上传视频和封面</h4>
		<a id="addVideo" href="javascript:void(0);" class="btn btn-outline">添加视频</a>
		<a id="addCover" href="javascript:void(0);" class="btn btn-outline">添加封面</a>
		<a id="uploadFile" href="javascript:void(0);" class="btn btn-outline">上传视频和封面</a>
	</div>

	<form id="form3">
		<input id="changeCover-file" type="file" style="display:none;"/>
	</form>
	<div class="row form-group form-group-sm" style="padding:10px;">
		<h4>修改封面</h4>
		<label class="col-sm-1" style="padding: 0;">fileId：</label>
		<div class="col-sm-4">
			<input name="fileId" class="form-control"></input>
		</div>
		<div class="col-sm-4">
			<a id="changeCover" href="javascript:void(0);" class="btn btn-outline">修改封面</a>
		</div>
	</div>
	<div class="row" id="resultBox"></div>
	
</div>

<div class="container-fluid">
    <div class="row row-offcanvas row-offcanvas-left">
        <!-- Content -->
        <div class="col-sm-offset-3 col-md-offset-2 col-xs-12 col-sm-9 col-md-10 " style="margin-top: 55px">
            <div class=" col-lg-8">
                <h2 class="sub-header">视频信息</h2>
                <c:url var="formSaveAction" value="/garten/manage/child/video_submit" />
                <c:url var="formDelAction" value="/garten/manage/child/video_remove" />
                <c:url var="formEditAction" value="/garten/manage/child/edit" />
                <form method="post" name="form_data" id="form_data">
                    <input type="hidden" name="id" value="${id }">
                    <input type="hidden" name="videoId" value="${videoId }">
                    <input type="hidden" name="childId" value="${childId }">
                    <input type="hidden" name="mId" value="${mId }">
                    <div class="form-group col-md-6">
                        <label for="input_videoId" class="control-label">视频地址</label>
                        <input  id="input_videoId" type="text" class="form-control" placeholder="请输入点播视频的id 或 播放页地址"
                                name="videoUrl" value="${videoUrl}">
                        <input  id="coverUrl" type="hidden" class="form-control"
                                name="coverUrl" value="${coverUrl}">
                    </div>
                    <!-- <div class="form-group col-md-6">
                        <label for="input_type" class="control-label">类型</label>
                        <input  id="input_type" type="text" class="form-control"
                                name="type" value="${type}">
                    </div> -->
                     <div class="form-group col-md-12">
                        <label for="input_videoDesc" class="control-label">视频描述</label>
                        <input  id="input_videoDesc" type="text" class="form-control"
                                name="videoDesc" value="${videoDesc}">
                    </div>
                     <div class="form-group col-md-12">
                        <label for="input_videoShareDesc" class="control-label">视频分享描述</label>
                        <input  id="input_videoShareDesc" type="text" class="form-control"
                                name="videoDescShare" value="${videoDescShare}">
                    </div>
                    <div class="ui-field-contain" align="center">
                        <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
                            id="btnSave">保存</button>
                        <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
                            id="btnDel">删除</button>
                        <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all"
                            id="btnBack">返回</button>
                    </div>
                </form>
                 <div>
                    <label for="input_qrcodeSn" class="control-label">二维码序列号:${qrcodeSn}</label>
                </div>
                 <div>
                    <label for="input_qrcodeSn" class="control-label">二维码存放地址:${qrcodeUrl}</label>
                    <img id="qrcode" src="${qrcodeUrl }" height="300" width="300" alt="二维码显示区"/>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="//imgcache.qq.com/open/qcloud/js/vod/crypto.js"></script>
<script src="//imgcache.qq.com/open/qcloud/js/vod/sdk/ugcUploader.js"></script>
<script src="//video.qcloud.com/signature/lib/ugcUploader.js"></script>
<script type="text/javascript">
        $(function() {
            $("#btnSave").click(function() {
                $("#form_data").attr("action", "${formSaveAction}");
                $("#form_data").submit();
                /* new submitForm('${ctx}/garten/manage/child/edit_submit}', { childId:'${child.id }' }).post(); */
            });
            $("#btnDel").click(function() {
                $("#form_data").attr("action", "${formDelAction}");
                $("#form_data").submit();
                /* new submitForm('${ctx}/garten/manage/child/del_submit}', { childId:'${child.id }' }).post(); */
            });
            $("#btnBack").click(function() {
                $("#form_data").attr("action", "${formEditAction}");
                $("#form_data").submit();
            });
            $("#form_data").submit(function() {
                $(":button", this).attr("disabled", true);
            });
            $("#uploadBtn").click(function() {
                $("#form_face").attr("action", "${uploadFaceAction}");
                $("#form_face").submit();
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
                    .attr('src', e.target.result)
                    .width(150)
                    .height(200);
                };
            }
            var URL = window.URL || window.webkitURL;
            var imgURL = URL.createObjectURL(input.files[0]);
            reader.readAsDataURL(input.files[0]);
        }
        
        var index = 0;
    	var cosBox = [];
    	/** 
    	 * 计算签名
    	**/
    	var getSignature = function(callback){
    		$.ajax({
    			url: '/garten/manage/child/video_upload/get_signature',
    			data: JSON.stringify({
    				"Action":"GetVodSignatureV2"
    			}),
    			type: 'POST',
    			dataType: 'json',
    			success: function(res){
    				if(res.success) {
    					callback(res.signature);
    				} else {
    					return '获取签名失败';
    				}
    			}
    		});
    	};

    	/**
    	 * 添加上传信息模块
    	 */
    	
    	var addUploaderMsgBox = function(type){
    		var html = '<div class="uploaderMsgBox" name="box'+index+'">';
    		if(!type || type == 'hasVideo') {
    			html += '视频名称：<span name="videoname'+index+'"></span>；' + 
    				'计算sha进度：<span name="videosha'+index+'">0%</span>；' + 
    				'上传进度：<span name="videocurr'+index+'">0%</span>；' + 
    				'fileId：<span name="videofileId'+index+'">   </span>；' + 
    				'上传结果：<span name="videoresult'+index+'">   </span>；<br>' + 
    				'地址：<span name="videourl'+index+'">   </span>；'+
    				'<a href="javascript:void(0);" name="cancel'+index+'" cosnum='+index+' act="cancel-upload">取消上传</a><br>';
    		}
    		
    		if(!type || type == 'hasCover') {
    			html += '封面名称：<span name="covername'+index+'"></span>；' + 
    			'计算sha进度：<span name="coversha'+index+'">0%</span>；' + 
    			'上传进度：<span name="covercurr'+index+'">0%</span>；' + 
    			'上传结果：<span name="coverresult'+index+'">   </span>；<br>' + 
    			'地址：<span name="coverurl'+index+'">   </span>；<br>' + 
    			'</div>'
    		}
    		html += '</div>';
    		
    		$('#resultBox').append(html);
    		return index++;
    	};

    	/** 
    	 * 示例1：直接上传视频
    	**/
    	$('#uploadVideoNow-file').on('change', function (e) {
    		var num = addUploaderMsgBox('hasVideo');
    		var videoFile = this.files[0];
    		$('#result').append(videoFile.name +　'\n');
    		var resultMsg = qcVideo.ugcUploader.start({
    		videoFile: videoFile,
    		getSignature: getSignature,
    		allowAudio: 1,
    		isTranscode : 1,//是否转码
    		success: function(result){
    			if(result.type == 'video') {
    				$('[name=videoresult'+num+']').text('上传成功');
    				$('[name=cancel'+num+']').remove();
    				cosBox[num] = null;
    			} else if (result.type == 'cover') {
    				$('[name=coverresult'+num+']').text('上传成功');
    			}
    		},
    		error: function(result){
    			if(result.type == 'video') {
    				$('[name=videoresult'+num+']').text('上传失败>>'+result.msg);
    			} else if (result.type == 'cover') {
    				$('[name=coverresult'+num+']').text('上传失败>>'+result.msg);
    			}
    		},
    		progress: function(result){
    			if(result.type == 'video') {
    				$('[name=videoname'+num+']').text(result.name);
    				$('[name=videosha'+num+']').text(Math.floor(result.shacurr*100)+'%');
    				$('[name=videocurr'+num+']').text(Math.floor(result.curr*100)+'%');
    				$('[name=cancel'+num+']').attr('taskId', result.taskId);
    				cosBox[num] = result.cos;
    			} else if (result.type == 'cover') {
    				$('[name=covername'+num+']').text(result.name);
    				$('[name=coversha'+num+']').text(Math.floor(result.shacurr*100)+'%');
    				$('[name=covercurr'+num+']').text(Math.floor(result.curr*100)+'%');
    			}
    			
    			
    		},
    		finish: function(result){
    			alert("upload v:"+result.fileId);
    			$('[name=videofileId'+num+']').text(result.fileId);
    			$('[name=videourl'+num+']').text(result.videoUrl);
    			if(result.message) {
    				$('[name=videofileId'+num+']').text(result.message);
    			}
    			$("#input_videoId").val(result.fileId);
    		}
    		});
    		if(resultMsg){
    			$('[name=box'+num+']').text(resultMsg);
    		}
    		$('#form1')[0].reset();
    	});
    	$('#uploadVideoNow').on('click', function () {
    		$('#uploadVideoNow-file').click();
    	});
    	/*
    	 * 取消上传绑定事件，示例一与示例二通用
    	 */
    	$('#resultBox').on('click', '[act=cancel-upload]', function() {
    		var cancelresult = qcVideo.ugcUploader.cancel({
    			cos: cosBox[$(this).attr('cosnum')],
    			taskId: $(this).attr('taskId')
    		});
    		console.log(cancelresult);
    	});


    	/** 
    	 * 示例2：上传视频+封面
    	**/
    	var videoFileList = [];
    	var coverFileList = [];
    	// 给addVideo添加监听事件
    	$('#addVideo-file').on('change', function (e) {
    		var videoFile = this.files[0];
    		videoFileList[0] = videoFile;
    		$('#result').append(videoFile.name +　'\n');

    	});
    	$('#addVideo').on('click', function () {
    		$('#addVideo-file').click();
    	});
    	// 给addCover添加监听事件
    	$('#addCover-file').on('change', function (e) {
    		var coverFile = this.files[0];
    		coverFileList[0] = coverFile;
    		$('#result').append(coverFile.name +　'\n');

    	});
    	$('#addCover').on('click', function () {
    		$('#addCover-file').click();
    	});

    	var startUploader = function(){
    		if(videoFileList.length){
    			var num = addUploaderMsgBox();
    			if(!coverFileList[0]){
    				$('[name=covername'+num+']').text('没有上传封面');
    			}
    			var resultMsg = qcVideo.ugcUploader.start({
    				videoFile: videoFileList[0],
    				coverFile: coverFileList[0],
    				getSignature: getSignature,
    				allowAudio: 1,
    				success: function(result){
    					if(result.type == 'video') {
    						$('[name=videoresult'+num+']').text('上传成功');
    						$('[name=cancel'+num+']').remove();
    						cosBox[num] = null;
    					} else if (result.type == 'cover') {
    						$('[name=coverresult'+num+']').text('上传成功');
    					}
    				},
    				error: function(result){
    					if(result.type == 'video') {
    						$('[name=videoresult'+num+']').text('上传失败>>'+result.msg);
    					} else if (result.type == 'cover') {
    						$('[name=coverresult'+num+']').text('上传失败>>'+result.msg);
    					}
    				},
    				progress: function(result){
    					if(result.type == 'video') {
    						$('[name=videoname'+num+']').text(result.name);
    						$('[name=videosha'+num+']').text(Math.floor(result.shacurr*100)+'%');
    						$('[name=videocurr'+num+']').text(Math.floor(result.curr*100)+'%');
    						$('[name=cancel'+num+']').attr('taskId', result.taskId);
    						cosBox[num] = result.cos;
    					} else if (result.type == 'cover') {
    						$('[name=covername'+num+']').text(result.name);
    						$('[name=coversha'+num+']').text(Math.floor(result.shacurr*100)+'%');
    						$('[name=covercurr'+num+']').text(Math.floor(result.curr*100)+'%');
    					}
    				},
    				finish: function(result){
    					alert("upload c v:"+result.fileId);
    					$('[name=videofileId'+num+']').text(result.fileId);
    					$('[name=videourl'+num+']').text(result.videoUrl);
    					if(result.coverUrl) {
    						$('[name=coverurl'+num+']').text(result.coverUrl);
    					}
    					if(result.message) {
    						$('[name=videofileId'+num+']').text(result.message);
    					}
    					$("#coverUrl").val(result.coverUrl);
    					$("#input_videoId").val(result.fileId);
    				}
    			});
    			if(resultMsg){
    				$('[name=box'+num+']').text(resultMsg);
    			}
    		} else {
    			$('#result').append('请添加视频！\n');
    		}
    		
    	}

    	// 上传按钮点击事件
    	$('#uploadFile').on('click', function () {
    		//var secretId = $('#secretId').val();
    		//var secretKey = $('#secretKey').val();
    		startUploader();
    		$('#form2')[0].reset();
    	});

    	/** 
    	 * 示例3：直修改封面
    	**/
    	$('#changeCover-file').on('change', function (e) {
    		var num = addUploaderMsgBox('hasCover');
    		var changeCoverFile = this.files[0];
    		var fileId = $('[name=fileId]').val();
    		var resultMsg = qcVideo.ugcUploader.start({
    		fileId: fileId,
    		coverFile: changeCoverFile,
    		getSignature: getSignature,
    		success: function(result){
    			if(result.type == 'video') {
    				$('[name=videoresult'+num+']').text('上传成功');
    			} else if (result.type == 'cover') {
    				$('[name=coverresult'+num+']').text('上传成功');
    			}
    		},
    		error: function(result){
    			if(result.type == 'video') {
    				$('[name=videoresult'+num+']').text('上传失败>>'+result.msg);
    			} else if (result.type == 'cover') {
    				$('[name=coverresult'+num+']').text('上传失败>>'+result.msg);
    			}
    		},
    		progress: function(result){
    			if(result.type == 'video') {
    				$('[name=videoname'+num+']').text(result.name);
    				$('[name=videosha'+num+']').text(Math.floor(result.shacurr*100)+'%');
    				$('[name=videocurr'+num+']').text(Math.floor(result.curr*100)+'%');
    			} else if (result.type == 'cover') {
    				$('[name=covername'+num+']').text(result.name);
    				$('[name=coversha'+num+']').text(Math.floor(result.shacurr*100)+'%');
    				$('[name=covercurr'+num+']').text(Math.floor(result.curr*100)+'%');
    			}
    			
    		},
    		finish: function(result){
    			$('[name=coverurl'+num+']').text(result.coverUrl);
    			if(result.message) {
    				$('[name=coverurl'+num+']').text(result.message);
    			}
    			$("#coverUrl").val(result.coverUrl);
    		}
    		});
    		if(resultMsg){
    			$('[name=box'+num+']').text(resultMsg);
    		}
    		$('#form1')[0].reset();
    	});
    	$('#changeCover').on('click', function () {
    		$('#changeCover-file').click();
    	});
    </script>
</body>
</html>
