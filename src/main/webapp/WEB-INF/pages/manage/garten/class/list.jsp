<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <style type="text/css">
        div.pull-left {
            display: none !important;
        }
    </style>
    <title>幼儿园班级列表</title>
</head>
<body>
<h4><font color="red">${param.tip}</font></h4>
<c:url var="gartenClsListActionUrl" value="/garten/manage/class/list_data"/>
<table id="table-pagination" data-url="${gartenClsListActionUrl}" data-side-pagination="server" data-pagination="true"
       data-page-list="[20, 50, 100, 200]" data-query-params="queryParams">
    <thead>
    <tr>
        <th data-field="createDate" data-align="left" data-sortable="false"
            data-formatter="operateFormatter">幼儿园班级列表
        </th>
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
        for (var k in o)if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                    RegExp.$1.length == 1 ? o[k] :
                            ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }

    function queryParams(params) {
        return {
            "pager.pageSize": params.pageSize,
            "pager.offset": params.pageSize * (params.pageNumber - 1),
            "pager.pageNumber": params.pageNumber,
            "pager.searchText": params.searchText,
            "pager.sortName": params.sortName,
            "pager.sortOrder": params.sortOrder
        };
    }

    function operateFormatter(value, row, index) {
        return [
            '<a href="javascript:void(0)" onclick="javascript:window.location.href=\'' +
            '${ctx}/garten/manage/class/edit.action?gartenCls.id=' + row.id + '\'">',
            row.name,
            '</a>',
        ].join('');
    }

</script>
</body>
</html>
