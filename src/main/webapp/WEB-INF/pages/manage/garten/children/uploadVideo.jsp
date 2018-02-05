<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<link rel="icon" type="image/png" sizes="64x64"
    href="/img/favicon-64x64.png">
<link rel="icon" type="image/png" sizes="32x32"
    href="/img/favicon-32x32.png">
<title>上传视频</title>
</head>
<body>
    <!-- <div id="browser">
        <h4>支持浏览器及其版本列表</h4>
        <style>
            #browser table td{
                border: 1px solid #aaa;
                padding: 0.2em 0.4em;
            }
        </style>
        <table style="width: 100%;">
            <thead>
                <tr>
                    <th>IE</th>
                    <th>Edge</th>
                    <th>Firefox</th>
                    <th>chrome</th>
                    <th>safari</th>
                    <th>opera</th>
                    <th>andriod</th>
                    <th>ios</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>10+</td>
                    <td>12+</td>
                    <td>3.5+</td>
                    <td>4+</td>
                    <td>5+</td>
                    <td>12.1+</td>
                    <td>4.4+</td>
                    <td>ios safari 5.1+</td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td>魅族默认浏览器需flyme5.0+</td>
                    <td>内嵌webview无法正常使用<br/>包括Chrome,QQ浏览器等<br/>目前仅手Q和微信正常</td>
                </tr>
            </tbody>
        </table>
    </div> -->
    <!-- <div class="container-fluid">
        <div class="input_area">
            <span>转 码 : </span>
            <input type="radio" name="transcode" value="1" checked /> 开 启
            <input type="radio" name="transcode" value="0" /> 禁 用
        </div>
        <div class="input_area">
            <span>水 印: </span>
            <input type="radio" name="watermark" value="1" /> 开 启
            <input type="radio" name="watermark" value="0" checked /> 禁 用
        </div>
        <div class="input_area">
            <span>分类ID: </span> <input type="input" name="classId" value="" /> <br />
        </div>
        <button id="start">确定</button>
    </div> -->

    <div class="out" id='pickfiles_area'>
        <button id="pickfiles" type="button">添加文件</button>
    </div>
    <div class="result" id="result" style="width: 400px;"></div>
    <div class="count" id="count" style="width: 400px;"></div>
    <div class="out btn-wrap" id="btnarea" style="width: 400px;">
        <button id="start_upload" type="button">开始上传</button>
        <button id="stop_upload" type="button">取消上传</button>
        <button id="re_upload" type="button">重新上传</button>
    </div>
    <div class="out" id="error" style="color: red; width: 400px; text-align: left;">
    </div>

    <script src="//imgcache.qq.com/open/qcloud/js/vod/sdk/uploaderh5V3.js" charset="utf-8"></script>
    <script type="text/javascript">
        function getParameterByName(name, url) {
            if (!url)
                url = window.location.href;
            name = name.replace(/[\[\]]/g, "\\$&");
            var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
                    .exec(url);
            if (!results)
                return null;
            if (!results[2])
                return '';
            return decodeURIComponent(results[2].replace(/\+/g, " "));
        }
        (function() {
            var $ = qcVideo.get('$');
            var Version = qcVideo.get('Version');
            if (!qcVideo.uploader.supportBrowser()) {
                if (Version.IS_MOBILE) {
                    alert('当前浏览器不支持上传，请升级系统版本或者下载最新的chrome浏览器');
                } else {
                    alert('当前浏览器不支持上传，请升级浏览器或者下载最新的chrome浏览器');
                }
                return;
            }
            /* $('#start').on('click', function(e) {
                $('#result').show();
                $('#count').show();
                $('#pickfiles_area').show();
                $('#btnarea').show();
                $('#error').show();

                accountDone('pickfiles',
                        $('input[name="transcode"]:checked').val(),
                        $('input[name="watermark"]:checked').val(),
                        null,
                        $('input[name="classId"]').val());
                }); */
            accountDone('pickfiles',
                    $('input[name="transcode"]:checked').val(),
                    $('input[name="watermark"]:checked').val(),
                    null,
                    $('input[name="classId"]').val());
        })();

        /**
         * @param upBtnId 上传按钮ID
         * @param isTranscode 是否转码
         * @param isWatermark 是否设置水印
         * @param [transcodeNotifyUrl] 转码成功后的回调
         * @param [classId] 分类ID
         */
        function accountDone(upBtnId, isTranscode, isWatermark,
                transcodeNotifyUrl, classId) {

            var $ = qcVideo.get('$'), ErrorCode = qcVideo.get('ErrorCode'), Log = qcVideo
                    .get('Log'), JSON = qcVideo.get('JSON'), util = qcVideo
                    .get('util'), Code = qcVideo.get('Code'), Version = qcVideo
                    .get('Version');
            //截取本地URL
            var pathName = window.document.location.pathname
            var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
            //alert(location.origin);
            //alert(pathName);
            var rootName = location.origin + "/js";
            //var shaJs ="http://static-10001988.file.myqcloud.com/resource/js/calculator_worker_sha1.js";
            qcVideo.uploader.init(
                    //1: 上传基础条件
                    {
                        web_upload_url : location.protocol
                                + '//vod2.qcloud.com/v3/index.php',

                        getSignature : function(argObj, callback){
                            alert(argObj.ft);
                            // 调用APP后台服务器，返回签名
                            var sigUrl = '${ctx}/garten/manage/child/video_upload/get_signature?'
                            + 'f=' + encodeURIComponent(argObj.f)
                            + '&ft=' + encodeURIComponent(argObj.ft)
                            + '&fs=' + encodeURIComponent(argObj.fs);

                            $.get(sigUrl).done(function(ret) {
                                alert(ret.signature);
                                callback(ret.signature);
                            })
                        },
                        upBtnId : upBtnId, //上传按钮ID（任意页面元素ID）
                        isTranscode : isTranscode,//是否转码
                        isWatermark : isWatermark,//是否设置水印
                        after_sha_start_upload : true,//sha计算完成后，开始上传 (默认关闭立即上传)
                        sha1js_path : rootName + '/calculator_worker_sha1.js', //计算sha1的位置
                        disable_multi_selection : false, //禁用多选 ，默认为false

                        transcodeNotifyUrl: transcodeNotifyUrl,//(转码成功后的回调地址)isTranscode==true,时开启； 回调url的返回数据格式参考  https://www.qcloud.com/document/product/266/1407
                        classId : classId,
                        // mime_types, 默认是常用的视频和音频文件扩展名，如MP4, MKV, MP3等, video_only 默认为false，可允许音频文件上传
                        filters: {max_file_size: '8gb', mime_types: [], video_only: true},
                        forceH5Worker: true // 使用HTML5 webworker计算
                    },
                    //2: 回调
                    {
                        /**
                         * 更新文件状态和进度
                         * @param args { id: 文件ID, size: 文件大小, name: 文件名称, status: 状态, percent: 进度 speed: 速度, errorCode: 错误码,serverFileId: 后端文件ID }
                         */
                        onFileUpdate : function(args) {
                            if (args.code == Code.SHA_FAILED)
                                return alert('该浏览器无法计算SHA')
                            var $line = $('#' + args.id);
                            if (!$line.get(0)) {
                                $('#result')
                                        .append(
                                                '<div class="line" id="' + args.id + '"></div>');
                                $line = $('#' + args.id);
                            }

                            var finalFileId = '';

                            if (args.code == Code.UPLOAD_DONE) {
                                finalFileId = '文件ID>>'
                                        + args.serverFileId
                                $.ajax({
                                    url : '/getVideoInfo',
                                    type : 'POST',
                                    dataType : 'json',
                                    data : finalFileId
                                });
                            }

                            $line.html(''
                                    + '文件名：' + args.name
                                    + ' >> 大小：' + util.getHStorage(args.size)
                                    + ' >> 状态：' + util.getFileStatusName(args.status) + ''
                                    + ( args.percent ? ' >> 进度：' + args.percent + '%' : '')
                                    + ( args.speed ? ' >> 速度：' + args.speed + '' : '')
                                    + '<span data-act="del" class="delete">删除</span>'
                                    + finalFileId
                            );
                        },
                        // 文件状态发生变化 @param info  { done: 完成数量 , fail: 失败数量 , sha: 计算SHA或者等待计算SHA中的数量 , wait: 等待上传数量 , uploading: 上传中的数量 }
                        onFileStatus: function (info) {
                            $('#count').text('各状态总数-->' + JSON.stringify(info));
                        },
                        //上传时错误文件过滤提示 @param args {code:{-1: 文件类型异常,-2: 文件名异常} , message: 错误原因 ， solution: 解决方法}
                        onFilterError: function (args) {
                            var msg = 'message:' + args.message + (args.solution ? (';solution==' + args.solution) : '');
                            $('#error').html(msg);
                        }
                    }
            );
        }
        //事件绑定
        $('#start_upload').on('click', function () {
            //@api 上传
            qcVideo.uploader.startUpload();
        });

        $('#stop_upload').on('click', function () {
            //@api 暂停上传
            qcVideo.uploader.stopUpload();
        });

        $('#re_upload').on('click', function () {
            //@api 恢复上传（错误文件重新）
            qcVideo.uploader.reUpload();
        });

        $('#result').on('click', '[data-act="del"]', function (e) {
            var $line = $(this).parent();
            var fileId = $line.get(0).id;
            Log.debug('delete', fileId);
            $line.remove();
            //@api 删除文件
            qcVideo.uploader.deleteFile(fileId);
        });
    </script>
</body>
</html>