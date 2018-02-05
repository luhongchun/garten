<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <script src="http://static-10001988.file.myqcloud.com/resource/js/jquery-2.1.1.min.js"></script>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/example.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.css"/>
  <link rel="stylesheet" href="http://static-10001988.cos.myqcloud.com/resource/style/weui.min.css"/>
  <title>视频审核推送</title>
</head>

<body>
<c:url var="pushUrl" value="/biz/video/closeup/notify_video_review"/>
<c:url var="updateClassesUrl" value="/garten/class/getAllByGartenId"/>
<c:url var="getReviewerListUrl" value="/garten/user/get_reviewer_list"/>

<div class="weui_msg">
  <div class="weui_icon_area">
    <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/user.svg" width="70px"></img>
    <p style="font-size:18px; color:#4C4C4C;">视频审核推送</p>
  </div>
</div>

<form name="dataForm" id="dataForm" style="margin-top:50px;">
  <%-- <input type="hidden" name="reviewerId" value="${reviewerId}"> --%>
  <div class="weui_cells weui_cells_form">
    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/class.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <label for="select" class="select" style="margin-top:-1px; margin-bottom:-1px;">
          <select name="garten" id="garten" style="font-size:16px; color:#000000;" onchange="gartenChange()">
            <option value="" disabled selected>选择幼儿园</option>
            <c:forEach items="${gartens}" var="g">
              <option value="${g.id}">${g.name}</option>
            </c:forEach>
          </select>
        </label>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/class.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <label for="select" class="select" style="margin-top:-1px; margin-bottom:-1px;">
          <select name="gartenClass" id="gartenClass" style="font-size:16px; color:#000000;" onchange="classChange()">
            <option value="" disabled selected>选择班级</option>
            <c:forEach items="${classes}" var="cls">
              <option value="${cls.id}">${cls.name}</option>
            </c:forEach>
          </select>
        </label>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/class.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <label for="select" class="select" style="margin-top:-1px; margin-bottom:-1px;">
          <select name="reviewerId" id="reviewerId" style="font-size:16px; color:#000000;">
            <option value="" disabled selected>审核人</option>
            <c:forEach items="${reviewerList}" var="r">
              <option value="${r.id}">${r.aliasName}</option>
            </c:forEach>
          </select>
        </label>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/class.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <label for="select" class="select" style="margin-top:-1px; margin-bottom:-1px;">
          <select name="videoType" id="videoType" style="font-size:16px; color:#000000;">
            <option value="" disabled selected>选择视频类型</option>
            <c:forEach items="${vtList}" var="vt">
              <option value="${vt}">${vt}</option>
            </c:forEach>
          </select>
        </label>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/class.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <label for="select" class="select" style="margin-top:-1px; margin-bottom:-1px;">
          <select name="date" id="date" style="font-size:16px; color:#000000;">
            <option value="" disabled selected>选择日期</option>
            <c:forEach items="${dateList}" var="d">
              <option value="${d}">${d}</option>
            </c:forEach>
          </select>
        </label>
      </div>
    </div>

    <div class="weui_cell">
      <div class="weui_cell_hd">
        <img src="http://static-10001988.cossh.myqcloud.com/resource/images/icons/baby.svg" alt=""
             style="width:24px;margin-right:5px;display:block">
      </div>
      <div class="weui_cell_bd weui_cell_primary">
        <input id="videoIds" name="videoIds" class="weui_input" style="font-size:16px; margin-left:9px;"
               placeholder="添加视频，id间以逗号隔开"/>
      </div>
    </div>

  </div>
</form>

<div class="bd" style="margin-top:50px;margin-left:10px;margin-right:10px;">
  <a id="pushButton" href="javascript:;" class="weui_btn weui_btn_primary">提 交 审 核</a>
</div>

<!--BEGIN promptDialog-->
<div class="weui_dialog_confirm" id="promptDialog" style="display:none;">
  <div class="weui_mask"></div>
  <div class="weui_dialog">
    <div id="dlgTitle" class="weui_dialog_hd"><strong class="weui_dialog_title">提示</strong></div>
    <div class="weui_dialog_bd"><p id="dlgText" align="center"></p></div>
    <div class="weui_dialog_ft">
      <a id="centerBtn" href="javascript:;" class="weui_btn_dialog primary">确定</a>
    </div>
  </div>
</div>
<!--END promptDialog-->

<script type="text/javascript">
    //提示框确认按钮
    var pDialog = document.getElementById("promptDialog");
    pDialog.addEventListener("click", function () {
        pDialog.style.display = "none";
    });

    var pushBtn = document.getElementById("pushButton");
    pushBtn.addEventListener("click", function () {
        pushBtn.setAttribute("class", "weui_btn weui_btn_disabled weui_btn_primary");
        $.post("${pushUrl}", $("#dataForm").serialize(), function (data) {
            if ("success" == data.msg) {
                showPrompt("提交成功", data.msg, "确认");
            } else {
                showPrompt("提交失败", data.msg, "确认");
                pushBtn.setAttribute("class", "weui_btn weui_btn_primary");
            }
        });
    });

    function showPrompt(title, text, btn) {
        var warnDlg = document.getElementById('promptDialog');
        var dlgTitle = document.getElementById('dlgTitle');
        var dlgText = document.getElementById('dlgText');
        var centerBtn = document.getElementById('centerBtn');
        dlgTitle.innerHTML = title;
        dlgText.innerHTML = text;
        centerBtn.innerHTML = btn;
        warnDlg.style.display = "block";
    }

    function gartenChange() {
        var gartenS = document.getElementById("garten");
        var gartenId = gartenS.options[gartenS.selectedIndex].value;
        $.post("${updateClassesUrl}" + "/" + gartenId, function (data) {
            if (data != null) {
                $("#gartenClass").empty();
                $.each(data, function (i, n) {
                    $("#gartenClass").append("<option value='" + n.id + "'>" + n.name + "</option>");
                });
            }
        });
    }

    function classChange() {
        var gartenClassS = document.getElementById("gartenClass");
        var gartenClassId = gartenClassS.options[gartenClassS.selectedIndex].value;
        $.post("${getReviewerListUrl}" + "/" + gartenClassId, function (data) {
            if (data != null) {
                $("#reviewerId").empty();
                $.each(data, function (i, n) {
                    $("#reviewerId").append("<option value='" + n.id + "'>" + n.aliasName + "</option>");
                });
            }
        });
    }
</script>
</body>
</html>
