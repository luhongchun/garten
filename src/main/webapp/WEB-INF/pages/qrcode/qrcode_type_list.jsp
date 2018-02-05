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
        url : "${ctx}/app/application/qrcode_type_delete.action?typeId=" + id,
        success : function(msg) {
          if (msg == 'success') {
        	  alert("删除成功！");
              window.location = "${ctx}/app/application/qrcode_type_list.action";
          } else {
            alert("删除失败，该类型存在二维码");
          }
        }
      });
  }

  function gotoPage(page) {
    if (page > 0 && page <= totalPage) {
      location.href = "/app/application/qrcode_type_list.action?count=" + count + "&start=" + page;
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
      location.href = "${ctx}/app/application/qrcode_printable_img.action?" + arr.join('&') + "&repeatCnt="
          + repeatCnt + "&desc=" + desc;
    }
  }

  $(function() {
    $("[name=ids]:checkbox").bind("click", function() {
      if ($("[name=ids]:checkbox").filter(":checked").length == 12) {
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
<title>二维码类型列表</title>
</head>
<body style="height: 100%">
          <div>
           <div>
        	<table data-role="table" id="table-custom-2" data-mode="columntoggle" class="ui-body-d ui-shadow table-stripe ui-responsive" data-column-btn-theme="b" data-column-btn-text="选择显示列..." data-column-popup-theme="a">
			    <thead>
			      <tr>
			        <th data-priority="1">名称</th>
			        <th data-priority="2">描述</th>
			        <th data-priority="3">编辑地址</th>
			        <th data-priority="4">处理地址</th>
			        <th data-priority="5">编辑</th>
			        <th data-priority="6">删除</th>
			      </tr>
			    </thead>
			    <tbody>
			    	<s:iterator value="#request.typeList" id="item" status="status">
		                <tr ${status.count % 2 == 1 ? 'class=odd' : ''}>
		                  <s:url var="editActionUrl" value="%{#item.editUrl}">
		                    <s:param name="typeId" value="#item.id" />
		                  </s:url>
		                  <s:url var="editUrl" value="/app/application/qrcode_type_edit.action">
		                    <s:param name="typeId" value="#item.id" />
		                  </s:url>
		                  <td>${item.name}</td>
		                  <td>${item.description}</td>
		                  <td>${item.editUrl}</td>
		                  <td>${item.dealUrl}</td>
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
       <s:iterator begin="#request.pager.currentPage - 4" end="#request.pager.currentPage - 1" status="index">
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
       <s:iterator begin="#request.pager.currentPage + 1" end="#request.pager.currentPage + 4" status="index">
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
       <input type="text" pattern="\d{1,3}" min="1" id="gotoNum" max="${request.pager.totalPage}" size="3" />
       <input type="button" value="跳转" onclick="gotoPage($('#gotoNum').val())" />
       <br />
     </div>
   </div>
</body>
</html>