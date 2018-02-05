<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <title>我的信息</title>
  <link rel="stylesheet" href="https://weui.io/example.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.css"/>
  <link rel="stylesheet" href="https://cdn.bootcss.com/weui/0.4.3/style/weui.min.css"/>
</head>

<body ontouchstart style="background:#EFEEF3;">
<div class="weui_cells">
  <div class="weui_cell">
    <div class="weui_cell_bd weui_cell_primary">
      <p>手机号码</p>
    </div>
    <div class="weui_cell_ft" style="font-size:13px;">${phoneNum}</div>
  </div>
</div>

<div class="weui_cells_title" style="margin-top:20px;">我的业务</div>
<div class="weui_cells">
  <c:forEach items="${bizInfo }" var="biz" varStatus="status">
    <div class="weui_cell">
      <div class="weui_cell_bd weui_cell_primary">
        <p>${biz.bizType }</p>
      </div>
      <c:if test="${!biz.expired }">
        <div class="weui_cell_ft" style="font-size:13px; margin-right:10px;">${biz.dueDate } 到期</div>
        <c:if test="${online}">
          <a href="${biz.renewUrl }" class="weui_btn weui_btn_mini weui_btn_primary">续 费</a>
        </c:if>
      </c:if>
      <c:if test="${online && biz.expired }">
        <a href="${biz.renewUrl }" class="weui_btn weui_btn_mini weui_btn_primary">开 通</a>
      </c:if>
    </div>
  </c:forEach>
  <c:if test="${history }">
    <div class="weui_cell">
      <div class="weui_cell_bd weui_cell_primary">
        <p>历史记录</p>
      </div>
      <a href="${ctx }/biz/carekids/rdt/action_video_history_camera_list/${openId } "
         class="weui_btn weui_btn_mini weui_btn_primary">进 入</a>
    </div>
  </c:if>
</div>

<div class="weui_cells_title" style="margin-top:20px;">可观看区域</div>
<div class="weui_cells">
  <c:forEach items="${cameras}" var="camera" varStatus="status">
    <div class="weui_panel_bd">
      <div class="weui_media_box weui_media_small_appmsg">
        <div class="weui_cells weui_cells_form">
          <div class="weui_cell weui_cell_switch">
            <div class="weui_cell_hd weui_cell_primary">${empty camera.name ? '未命名' : camera.name}</div>
            <img id="img_all" src="http://static-10001988.cos.myqcloud.com/resource/images/icons/camera_open.png" alt=""
                 style="width:28px; display:block"/>
          </div>
        </div>
      </div>
    </div>
  </c:forEach>
</div>

<div class="weui_cells" style="margin-top:25px;">
  <div class="weui_cell">
    <div class="weui_cell_bd weui_cell_primary">
      <p>版本号</p>
    </div>
    <div class="weui_cell_ft" style="font-size:13px;">${version}</div>
  </div>
</div>

<div style="margin-top:40px;">
  <p style="color:#EFEEF3;">____</p>
</div>
</body>
<script type="text/javascript">
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

    function renew(bizId) {
        new submitForm('${ctx}/biz/setting/renew', {bizId: bizId}).post();
    }
</script>
</html>