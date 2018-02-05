<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>公众号素材管理</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:if test="${not empty msg}">
        <script type="text/javascript">
          alert('${msg}');
        </script>
      </c:if>
      <form id="send_form" enctype="multipart/form-data">
        <div>
          <h4>公众账号</h4>
        </div>
        <div>
          <select id="subscriptionId" name="subscriptionId"></select>
        </div>
        <div>
          <h4>请选择您要上传的媒体文件</h4>
        </div>
        <div>
          <input type="file" id="uploadFile" name="uploadFile" placeholder="请选择您要上传的媒体文件">
        </div>
        <div>
          <h4>素材id</h4>
        </div>
        <div>
          <input type="text" name="materialId" id="materialId">
        </div>
        <div>
          <h4>素材类型</h4>
        </div>
        <div>
          <input type="text" name="type" id="type">
        </div>
        <input type="button" id="upload" value="上传永久素材">
        <input type="button" id="delete" value="删除永久素材">

        <c:url var="infoAction" value="/wx/manage/subscription/material_info" />
        <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="infoBtn">同步最新素材信息</button>
      </form>
      <div>
        <div id="send_result"></div>
      </div>
      <ul data-role="listview" data-inset="true" data-filter="true" data-input="#myFilter">
        <li>素材列表</li>
        <li>素材ID           素材名    上传时间戳  下载地址</li>
        <c:forEach items="${materialList}" var="material" varStatus="status">
          <li>
            <a data-ajax="false">
              ${empty material.media_id ? 'id未填写' : material.media_id}
              ${empty material.name ? '名字未填写' : material.name}
              ${empty material.update_time ? '未知时间' : material.update_time}
              ${empty material.url ? '未知地址' : material.url}
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>
  <script type="text/javascript">
    $(function() {
      loadSubscriptionList();// 加載公众账号列表
      $('#upload').click(function() {
        if(checkText()) {
          $('#upload').attr('disabled', "true");
          $.ajax({
            type : "POST",
            url : "${ctx}/wx/manage/subscription/upload_permanent_material.action?",
            data : $('#send_form').serialize(),
            success : function(msg) {
              var _html;
              if (msg == 'success')
                _html = '上传成功！';
              else
                _html = '上传失败！';
              $("#send_result").append(_html);
              $('#send_form')[0].reset();
              $('#upload').removeAttr("disabled");
            },
            failure: function() {
              alert("接口调用失败");
              $('#upload').attr('disabled', "false");
            }
          });
        }
      });

      $('#delete').click(function() {
        $('#delete').attr('disabled', "true");
        $.ajax({
          type : "POST",
          url : "${ctx}/wx/manage/subscription/delete_permanent_material.action?",
          data : $('#send_form').serialize(),
          success : function(msg) {
            var _html;
            if (msg == 'success')
              _html = '删除成功！';
            else
              _html = '删除失败！';
            $("#send_result").append(_html);
            $('#send_form')[0].reset();
            $('#delete').removeAttr("disabled");
          },
          failure: function() {
            alert("接口调用失败");
            $('#delete').attr('disabled', "false");
          }
        });
      });

      $("#infoBtn").click(function() {
        $("#send_form").attr("action", "${infoAction}");
        $("#send_form").submit();
      });
    });

    function checkText() {
      var result = true;
      var text = $("#materialId").val();
      if(text == null || text == undefined || $.trim(text).length == 0) {
        alert("输入内容吧,要不然让奴家怎么跟你玩嘛?")
        result = false;
      }
      return result;
    }

    function loadSubscriptionList() {
      $.ajax({
        type : "GET",
        url : "${ctx}/wx/manage/subscription/load_subscription_list",
        success : function(msg) {
            var _html = "";
            $.each(msg, function (n, value) {
              _html += "<option value='" + value.id + "'>" + value.name + "</option>";
            });
            $("#subscriptionId").append(_html);
        }
      });
    }
  </script>
</body>
</html>