<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <script src="${ctx}/btable/bootstrap-table.js"></script>
  <script src="${ctx}/btable/locale/bootstrap-table-zh-CN.js"></script>
  <link rel="stylesheet" href="http://static-10001988.file.myqcloud.com/resource/style/bootstrap.css">
  <link rel="stylesheet" href="${ctx}/btable/bootstrap-table.css">
  <%@ include file="/WEB-INF/includes/common-res.jsp" %>
  <style>
    div.pull-left {
      display: none !important;
    }

    div.fixed-table-container {
      border: 0 !important;
    }
  </style>
  <title>精彩回顾</title>
</head>
<body>
<div data-role="main" class="ui-content jqm-content">
  <ul id="list" data-role="listview" class="ui-listview-outer" data-inset="true">
  </ul>
</div>
<table id="table-pagination" data-url="${ctx}/biz/carekids/get_camera_video_data/${camera.id}"
       data-side-pagination="server" data-pagination="true" data-page-list="[20, 50, 100, 200]"
       data-query-params="queryParams" style="display:none">
  <thead>
  <tr>
    <th data-field="createDate" data-align="left" data-sortable="false" data-formatter="operateFormatter"
        data-events="operateEvents">${not empty camera.name?camera.name: '未命名摄像头'}</th>
  </tr>
  </thead>
</table>

<script>
    $(function () {
        $('#table-pagination').bootstrapTable();
    });

    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(),    //day
            "h+": this.getHours(),   //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o) if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }

    function queryParams(params) {
        return {
            pageSize: params.pageSize,
            offset: params.pageSize * (params.pageNumber - 1),
            pageNumber: params.pageNumber,
            search: params.searchText,
            name: params.sortName,
            sortOrder: params.sortOrder
        };
    }

    function operateFormatter(value, row, index) {
        if (index < 1) {
            $('#list').empty();
            $('#list').listview('refresh');
        }
        var li = '<li><a href="javascript:;" onclick="playVideo(\'' + row.url + '\')">' + new Date(row.createDate).format('yyyy年MM月dd日 hh:mm') + '</a></li>';
        $('#list').append(li);
        $('#list').listview('refresh');
        <%--return [--%>
        <%--'<a href="javascript:void(0)" onclick="javascript:window.location.href=\'' +--%>
        <%--'${ctx}/media/record_video_play_weshow.action?videoUrl=' + row.video.accessUrl + '&instanceId=${request.instanceId}' + '\'">',--%>
        <%--'拍摄于 ' + new Date(row.createDate).format('yyyy年MM月dd日 hh:mm'),--%>
        <%--'</a>',--%>
        <%--].join('');--%>
        return '';
    }

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

    function playVideo(videoUrl) {
        new submitForm('${ctx}/biz/carekids/action_video_play', {
            instanceId: '${instanceId}',
            openId: '${openId}',
            cameraId: '${camera.id}',
            videoUrl: videoUrl
        }).post();
    }

    window.operateEvents = {
        'click .like': function (e, value, row, index) {
            alert('You click like icon, row: ' + JSON.stringify(row));
        }
    }

</script>
</body>
</html>