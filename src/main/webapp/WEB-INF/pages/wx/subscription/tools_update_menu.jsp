<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>更新公众号菜单</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <form id="send_form">
        <div>
          <h4>菜单json</h4>
        </div>
        <div>
          <textarea name="menuJson" id="menuJson" rows="5"></textarea>
        </div>
        <div>
          <h4>是否更新全部公众账号</h4>
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
        <input type="button" id="send" value="更新">
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
        if(checkJson()) {
          $('#send').attr('disabled', "true");
          $.ajax({
            type : "POST",
            url : "${ctx}/wx/manage/subscription/update_menu_submit.action?",
            data : $('#send_form').serialize(),
            success : function(msg) {
              var _html = "";
              $.each(msg, function (n, value) {
                _html += "<div><span>公众账户名称：" + value.subscription.name + "</span><span>, 菜单更新结果：";
                if(value.result.errcode == 0) {
                  _html += "成功</span>";
                } else {
                  _html += "失败,错误信息:" + value.result.errmsg + "</span>";
                }
              });
              $("#send_result").append(_html);
              $('#send_form')[0].reset();
              $('#send').removeAttr("disabled");
            },
            failure: function() {
              alert("更新失败");
              $('#send').attr('disabled', "false");
            }
          });
        }
      });
    });

    function checkJson() {
      var result = true;
      var text = $("#menuJson").val();
      if(text == null || text == undefined || $.trim(text).length == 0) {
        alert("输入json内容吧,要不然让奴家怎么跟你玩嘛?")
        result = false;
      } else {
        try {
          var json =  jQuery.parseJSON(text);
        }catch(err) {
          alert("json格式有误,错误参考信息:" + err);
          result = false;
        }
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