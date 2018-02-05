<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>发送通知消息</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <form id="send_form">
        <div>
          <h4>消息内容</h4>
        </div>
        <div>
          <textarea name="text" id="text" rows="5"></textarea>
        </div>
        <div>
          <h4>是否发送全部公众账号</h4>
        </div>
        <div>
          <select name="isSendAll" id="isSendAll" data-role="slider">
            <option value="0" selected="selected"></option>
            <option value="1"></option>
          </select>
        </div>
        <div>
          <h4>公众账号</h4>
        </div>
        <div>
          <select id="subscriptionId" name="subscriptionId"></select>
        </div>
        <input type="button" id="send" value="发送">
      </form>
      <div>
        <div id="send_result"></div>
      </div>
    </div>
  </div>
  <script type="text/javascript">
    $(function() {
      loadSubscriptionList();// 加載公众账号列表
      $('#send').click(function() {
        if(checkInput()) {
          $('#send').attr('disabled', "true");
          $.ajax({
            type : "POST",
            url : "${ctx}/wx/manage/subscription/send_notice_message_submit",
            data : $('#send_form').serialize(),
            success : function(msg) {
              var _html = "";
              $.each(msg, function (n, value) {
                _html += "<div><span>公众账户名称：" + value.subscription.name + "</span><span>, 发送总数：" + value.sendCount +
                "</span><span>,发送成功数：" + value.successCount + "</span><span>,发送失败数:" + value.failCount + "</span></div>";
              });
              $("#send_result").append(_html);
              $('#send_form')[0].reset();
              $('#send').removeAttr("disabled");
            },
            failure: function() {
              alert("发送失败");
              $('#send').attr('disabled', "false");
            }
          });
        }
      });
    });

    function checkInput() {
      var result = true;
      var text = $("#text").val();
      if(text == null || text == undefined || $.trim(text).length == 0) {
        alert("输入消息内容吧,要不然让奴家发什么消息嘛?")
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
              if(n == 1) {
                _html += "<option value='" + value.id + "'>" + value.name + "</option>";
              } else {
                _html += "<option selected='selected' value='" + value.id + "'>" + value.name + "</option>";
              }
            });
            $("#subscriptionId").append(_html);
        }
      });
    }
  </script>
</body>
</html>