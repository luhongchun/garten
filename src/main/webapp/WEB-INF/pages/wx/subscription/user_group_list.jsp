<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>用户分组管理</title>
</head>
<body>
<div data-role="page">
  <div data-role="main" class="ui-content jqm-content">
    <c:if test="${not empty msg}">
      <script type="text/javascript">
        alert('${msg}');
      </script>
    </c:if>

    <form class="ui-filterable" id="addForm">
      <div>
        <h4>公众账号</h4>
      </div>
      <div>
        <select id="subscriptionId" name="subscriptionId"></select>
      </div>

      <c:url var="addAction" value="/wx/manage/subscription/group_create" >
        <c:param name="subscriptionId" value="#subscriptionId" />
      </c:url>
      <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="addBtn">创建新分组</button>


      <c:url var="addAction" value="/wx/manage/subscription/group_delete" >
        <c:param name="subscriptionId" value="#subscriptionId" />
        <c:param name="groupId" value="#groupId" />
      </c:url>
      <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="addBtn">删除分组</button>

      <c:url var="infoAction" value="/wx/manage/subscription/user_group_list" >
        <c:param name="subscriptionId" value="#subscriptionId" />
      </c:url>
      <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="infoBtn">显示最新分组信息</button>

      <c:url var="groupUpdateAction" value="/wx/manage/subscription/group_update" >
        <c:param name="subscriptionId" value="#subscriptionId" />
      </c:url>
      <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="updateBtn">修改分组</button>

      <c:url var="membersMoveAction" value="/wx/manage/subscription/group_members_update" >
        <c:param name="subscriptionId" value="#subscriptionId" />
      </c:url>
      <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="moveUserBtn">移动用户所在分组</button>

      <div>
        <h5>请输入分组ID</h5>
        <input type="text" name="groupId" id="groupId">
        <c:url var="deleteAction" value="/wx/manage/subscription/group_delete" >
          <c:param name="subscriptionId" value="#subscriptionId" />
          <c:param name="groupId" value="#groupId" />
        </c:url>
        <button class="ui-btn ui-btn-inline ui-shadow ui-corner-all" type="button" id="deleteBtn">删除分组</button>
      </div>
    </form>

    <ul data-role="listview" data-inset="true">
      <li>分组列表</li>
      <li>分组名  分组ID  分组中粉丝数</li>
      <c:forEach items="${groupList}" var="group" varStatus="status">
        <c:url var="editActionUrl" value="/wx/manage/subscription/group_edit">
          <c:param name="subscriptionId" value="#subscriptionId" />
        </c:url>
        <li>
          <a data-ajax="false" href="${editActionUrl}">${empty group.name ? '未填写' : group.name}
              ${empty group.id ? 'id未填写' : group.id} ${empty group.count ? '未知人数' : group.count}
          </a>
        </li>
      </c:forEach>
    </ul>
  </div>
</div>

<script type="text/javascript">
  $(function() {
    loadSubscriptionList();// 加載公众账号列表

    $("#addBtn").click(function() {
      $("#addForm").attr("action", "${addAction}");
      $("#addForm").submit();
    });

    $("#infoBtn").click(function() {
      $("#addForm").attr("action", "${infoAction}");
      $("#addForm").submit();
    });

    $("#updateBtn").click(function() {
      $("#addForm").attr("action", "${groupUpdateAction}");
      $("#addForm").submit();
    });

    $("#moveUserBtn").click(function() {
      $("#addForm").attr("action", "${membersMoveAction}");
      $("#addForm").submit();
    });

    $("#deleteBtn").click(function() {
      $("#addForm").attr("action", "${deleteAction}");
      $("#addForm").submit();
    });

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
  });
</script>
</body>
</html>