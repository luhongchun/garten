<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>创建分组</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <form id="send_form">
        <input type="hidden" name="subscriptionId" value="${subscriptionId}">
        <div>
          <h4>分组ID</h4>
        </div>
        <div>
          <textarea name="groupId" id="groupId" rows="1"></textarea>
        </div>
        <div>
          <h4>openID列表，以","隔开</h4>
        </div>
        <div>
          <textarea name="openIds" id="openIds" rows="5"></textarea>
        </div>
        <input type="button" id="send" value="移动">
      </form>
      <div>
        <div id="send_result"></div>
      </div>
    </div>
  </div>
  <script type="text/javascript">
    $(function() {
      $('#send').click(function() {
        if(checkText()) {
          $('#send').attr('disabled', "true");
          $.ajax({
            type : "POST",
            url : "${ctx}/wx/manage/subscription/group_members_update_submit",
            data : $('#send_form').serialize(),
            success : function(msg) {
              var _html;
              if ('success' == msg) {
                _html = "<div><span>" + "移动结果：成功" + "</span>";
              } else {
                _html = "<div><span>" + "移动结果：失败" + "</span>";
              }
              $("#send_result").append(_html);
              $('#send_form')[0].reset();
              $('#send').removeAttr("disabled");
            },
            failure: function() {
              alert("方法调用失败！");
              $('#send').attr('disabled', "false");
            }
          });
        }
      });
    });

    function checkText() {
      var result = true;
      var text = $("#groupId").val();
      if(text == null || text == undefined || $.trim(text).length == 0) {
        alert("输入文本内容吧,要不然让奴家怎么跟你玩嘛?")
        result = false;
      } else {
        result = true;
      }
      return result;
    }
  </script>
</body>
</html>