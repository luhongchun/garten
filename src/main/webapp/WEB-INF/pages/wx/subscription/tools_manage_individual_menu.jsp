<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>公众号个性化菜单管理</title>
</head>
<body>
  <div data-role="page">
    <div data-role="main" class="ui-content jqm-content">
      <c:if test="${not empty msg}">
        <script type="text/javascript">
          alert('${msg}');
        </script>
      </c:if>
      <form id="send_form">
        <div>
          <h4>选择公众号</h4>
        </div>
        <div>
          <select id="subscriptionId" name="subscriptionId"></select>
        </div>
        <div>
          <h4>创建菜单:在下方输入菜单json</h4>
        </div>
        <div>
          <textarea name="menuJson" id="menuJson" rows="5"></textarea>
        </div>
        <input type="button" id="create" value="确认创建">

        <div>
          <h4>删除菜单:在下方输入菜单ID</h4>
        </div>
        <input type="text" name="menuId" id="menuId">
        <input type="button" id="delete" value="确认删除">

        <c:url var="infoAction" value="/wx/manage/subscription/menu_info" />
        <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="infoBtn">同步最新菜单信息</button>
      </form>
      <div>
        <div id="send_result"></div>
      </div>
      <ul data-role="listview" data-inset="true" data-filter="true" data-input="#myFilter">
        <li>菜单json列表</li>
        <c:forEach items="${menuList}" var="menu" varStatus="status">
          <dd class="content">
            <ul class="body">
              <li>
                <code>${menu}</code>
              </li>
            </ul>
          </dd>
        </c:forEach>
      </ul>
    </div>
  </div>
  <script type="text/javascript">
    $(function() {
      loadSubscriptionList();// 加載公众账号列表
      $('#create').click(function() {
        if(checkJson()) {
          $('#create').attr('disabled', "true");
          $.ajax({
            type : "POST",
            url : "${ctx}/wx/manage/subscription/create_individual_menu_submit",
            data : $('#send_form').serialize(),
            success : function(msg) {
              var _html;
              if (msg == 'success')
                _html = '创建成功！';
              else
                _html = '创建失败！';
              $("#send_result").append(_html);
              $('#send_form')[0].reset();
              $('#create').removeAttr("disabled");
            },
            failure: function() {
              alert("接口调用失败");
              $('#create').attr('disabled', "false");
            }
          });
        }
      });

      $('#delete').click(function() {
        $('#delete').attr('disabled', "true");
        $.ajax({
          type : "POST",
          url : "${ctx}/wx/manage/subscription/delete_individual_menu_submit",
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