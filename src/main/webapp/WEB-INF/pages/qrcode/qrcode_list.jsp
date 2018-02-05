<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<script type="text/javascript">
    var count = ${request.pager.count};
    var totalPage = ${request.pager.totalPage};
    function deleteItem(id) {
         $.ajax({
             type : "get",
             url : "${ctx}/app/application/qrcode_delete.action?infoId=" + id,
             success : function(msg) {
                 if (msg == 'success') {
                	 alert("删除成功");
                     window.location = "${ctx}/app/application/qrcode_list.action";
                 }
             }
         });
    }

    function gotoPage(page) {
        if (page > 0 && page <= totalPage) {
            location.href = "/app/application/qrcode_list.action?count=" + count + "&start=" + page;
        } else {
            alert("请输入合法的页码");
        }
    }

    function printQrcode(repeatCnt, desc) {
        var arr = [];
        $('[name=ids]').filter(':checked').each(function(index, ele) {
            arr.push("infoIds=" + $(ele).val());
        });
        if (arr.length <= 0) {
            alert("未选中任何二维码记录");
        } else if (repeatCnt > (36 / arr.length)) {
            alert("已超过最大36条二维码");
        } else {
            desc = (desc == null ? "" : desc);
            location.href = "${ctx}/app/application/qrcode_printable_img.action?" + arr.join('&')
                    + "&repeatCnt=" + repeatCnt + "&desc=" + desc;
        }
    }

    $(function() {
        $('#repeatCnt').bind('input propertychange', function() {
            $("[name=ids]:checkbox").filter(":checked").each(function(index, ele) {
                $(ele).attr("checked", false);
            });
            $("[name=ids]:checkbox").filter(":disabled").each(function(index, ele) {
                $(ele).attr("disabled", false);
            });
        });
        $("[name=ids]:checkbox").bind("click", function() {
            var repeatCnt = $('#repeatCnt').val();
            if ($("[name=ids]:checkbox").filter(":checked").length > (36 / repeatCnt - 1)) {
                $("[name=ids]:checkbox").filter(":not(:checked)").each(function(index, ele) {
                    $(ele).attr("disabled", true);
                });
            } else {
                $("[name=ids]:checkbox").filter(":disabled").each(function(index, ele) {
                    $(ele).attr("disabled", false);
                });
            }
        });
        $("#gotoNum").keyup(function(e) {
            if (13 == e.which) {
                gotoPage($('#gotoNum').val());
            }
        });
    });
</script>
<title>二维码信息管理</title>
</head>
<body style="height: 100%">
    <div>
        <div>
        	<table data-role="table" id="table-custom-2" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="选择显示列..." data-column-popup-theme="a">
		    <thead>
		      <tr class="ui-bar-d">
		        <th>选择</th>
		        <th data-priority="1">名称</th>
		        <th data-priority="2">匹配码</th>
		        <th data-priority="3">动作</th>
		        <th data-priority="4">描述</th>
		        <th data-priority="5">动作行为</th>
		        <th data-priority="6">编辑</th>
		        <th data-priority="7">删除</th>
		      </tr>
		    </thead>
		    <tbody>
		      <s:iterator value="#request.infoList" id="item" status="status">
                 <tr>
                     <s:url var="editActionUrl" value="%{#item.type.editUrl}">
                         <s:param name="typeId" value="#item.type.id" />
                         <s:param name="infoId" value="#item.id" />
                     </s:url>
                     <s:url var="editUrl" value="/app/application/qrcode_edit.action">
                         <s:param name="typeId" value="#item.type.id" />
                         <s:param name="infoId" value="#item.id" />
                     </s:url>
                     <td>
                         <input type="checkbox" name="ids" value="${item.id}" />
                     </td>
                     <td>${item.name}</td>
                     <td>${item.matchCode.code}</td>
                     <td>${item.type == null ? "未指定" : item.type.name}</td>
                     <td>${item.description}</td>
                     <td>
                         <a href="${editActionUrl}" data-ajax="false">设置</a>
                     </td>
                     <td>
                         <a href="${editUrl}" data-ajax="false">编辑</a>
                     </td>
                     <td>
                         <a onclick="deleteItem('${item.id}')">删除</a>
                     </td>
                 </tr>
             </s:iterator>
		    </tbody>
		  </table>
        </div>
        <div style="text-align: center;">
            当前${request.pager.currentPage}页
            <s:url var="firstPage" value="/app/application/qrcode_list.action">
                <s:param name="start" value="1" />
                <s:param name="count" value="#request.pager.count" />
            </s:url>
            <s:url var="lastPage" value="/app/application/qrcode_list.action">
                <s:param name="start" value="#request.pager.totalPage" />
                <s:param name="count" value="#request.pager.count" />
            </s:url>
            <s:if test="#request.pager.totalPage > 1 && #request.pager.currentPage != 1">
                <s:a href="%{firstPage}" data-ajax="false">
                    首页
                </s:a>
            </s:if>
            <s:iterator begin="#request.pager.currentPage - 4" end="#request.pager.currentPage - 1"
                status="index">
                <s:set var="current" value="#request.pager.currentPage - 1 + #index.index - 3" />
                <s:if test="#current > 1">
                    <s:url var="page" value="/app/application/qrcode_list.action">
                        <s:param name="start" value="current" />
                        <s:param name="count" value="#request.pager.count" />
                    </s:url>
                    <s:a href="%{page}" data-ajax="false">${current}</s:a>
                </s:if>
            </s:iterator>
            ${request.pager.currentPage}
            <s:iterator begin="#request.pager.currentPage + 1" end="#request.pager.currentPage + 4"
                status="index">
                <s:set var="current" value="#request.pager.currentPage + #index.count" />
                <s:if test="#current < #request.pager.totalPage">
                    <s:url var="page" value="/app/application/qrcode_list.action">
                        <s:param name="start" value="#current" />
                        <s:param name="count" value="#request.pager.count" />
                    </s:url>
                    <s:a href="%{page}" data-ajax="false">${current}</s:a>
                </s:if>
            </s:iterator>
            <s:if test="#request.pager.totalPage > 1">
                <s:if test="#request.pager.currentPage != #request.pager.totalPage">
                    <s:a href="%{lastPage}" data-ajax="false">
                        末尾
                    </s:a>
                </s:if>
            </s:if>
            总共${request.pager.totalPage}页
            <h5>跳转到:</h5>
            <input type="text" pattern="\d{1,3}" min="1" id="gotoNum" max="${request.pager.totalPage}"
                size="3" />
            <input type="button" value="跳转" onclick="gotoPage($('#gotoNum').val())" />
            <br />
            <h5>重复次数:</h5>
            <input type="text" pattern="\d{1,2}" min="1" max="36" id="repeatCnt" size="3" value="1"
                placeholder="重复次数" />
            <input type="text" id="desc" placeholder="二维码底部信息" />
            <input type="button" value="生成二维码"
                onclick="printQrcode($('#repeatCnt').val(), $('#desc').val())" />
        </div>
     </div>
</body>
</html>