<%@ page import="cn.smart.cloud.biz.opadmin.entity.weixin.WxSubscriptionMode" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
				 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>修改公共账号</title>
<style type="text/css">
.ui_li_long_data {
    text-align: left;
    word-wrap: break-word;
    word-break: normal;
    white-space: normal;
    font-weight: 700;
    display: block;
    max-width: 510px;
    border-width: 1px;
    border-style: solid;
    border-color: #ddd;
    padding: 0 .48em;
    font-size: 11px;
}

.ui_li_short_data {
    text-align: center;
    word-wrap: break-word;
    word-break: normal;
    white-space: normal;
    font-weight: 700;
    border-width: 1px;
    border-style: solid;
    border-color: #ddd;
    padding: 0 .48em;
    font-size: 11px;
}
</style>
</head>
<body>
	<div data-role="page">
		<div data-role="content">
			<c:url var="formEditAction"
				value="/wx/manage/subscription/edit_commit" />
			<c:url var="formDelAction"
				value="/wx/manage/subscription/del_commit" />
			<c:url var="rebuildUserAction"
				value="/wx/manage/subscription/rebuild_user" />
			<c:url var="checkAppIdAction"
				value="/wx/manage/subscription/check_appId" />
			<form method="post" name="form_data" id="form_data">
				<input type="hidden" name="subscriptionId" id="subscriptionId" value="${subscription.id}">
				<input type="hidden" name="TokenName" value="${session.TokenName}">
				<ul data-role="listview" data-inset="true">
					<li>服务器配置</li>
					<li>URL<br><span class="ui_li_long_data ui-corner-all">${subscription.authURL}</span></li>
					<li>Token<br><span class="ui_li_short_data ui-corner-all">${subscription.token}</span></li>
				</ul>
				<div>
					<h4>账号名称</h4>
				</div>
				<div>
					<input type="text" id="name" name="name"
						value="${subscription.name}" placeholder="请输入账号名称"
						required="required">
				</div>
				<div>
					<h4>账号类型</h4>
				</div>
				<div>
					<select id="type" name="type">
						<option value="0"
							${request.subscription.type == 0 ? 'selected="selected"' : ''}>正式号</option>
						<option value="1"
							${request.subscription.type == 1 ? 'selected="selected"' : ''}>演示号</option>
					</select>
				</div>
				<div>
					<h4>微信ID(多客服消息推送使用)</h4>
				</div>
				<div>
					<input type="text" id="wxId" name="wxId"
						value="${subscription.wxId}" placeholder="请输入微信ID"
						required="required">
				</div>
				<div>
					<h4>模式</h4>
				</div>
				<div>
					<c:set var="devMode" value="<%=WxSubscriptionMode.DEV_MODE%>" />
					<select id="mode" name="mode">
						<option value="0"
							${request.subscription.mode == devMode ? 'selected="selected"' : ''}>开发模式</option>
						<option value="1"
							${request.subscription.mode != devMode ? 'selected="selected"' : ''}>编辑模式</option>
					</select>
				</div>
				<div>
					<h4>欢迎消息Title</h4>
				</div>
				<div>
					<input type="text" id="welcomeTitle" name="welcomeTitle" value="${request.subscription.welcomeTitle}"
						placeholder="请输入欢迎消息Title" required="required">
				</div>
				<div>
					<h4>欢迎消息图片地址</h4>
				</div>
				<div>
					<input type="text" id="welcomePicUrl" name="welcomePicUrl" value="${request.subscription.welcomePicUrl}"
						   placeholder="请输入欢迎消息的图片地址" required="required">
				</div>
				<div>
					<h4>欢迎消息描述</h4>
				</div>
				<div>
					<input type="text" id="welcomeDesc" name="welcomeDesc" value="${request.subscription.welcomeDesc}"
						   placeholder="请输入欢迎消息描述" required="required">
				</div>
				<div>
					<h4>普通回复消息</h4>
				</div>
				<div>
					<input type="text" id="normalMessage" name="normalMessage"  value="${request.subscription.normalMessage}"
						placeholder="普通回复消息" required="required">
				</div>
				<div>
					<h4>是否需要用戶注册</h4>
				</div>
				<div>
					<select name="registerCheck" id="registerCheck" data-role="slider">
						<option value="0"
						${request.subscription.registerCheck == false ? 'selected="selected"' : ''}></option>
						<option value="1"
						${request.subscription.registerCheck == true ? 'selected="selected"' : ''}></option>
					</select>
				</div>
				<div>
					<h4>AppId</h4>
				</div>
				<div>
					<input type="text" id="appId" name="appId"
						value="${request.subscription.appId}" placeholder="请输入AppId"
						required="required">
				</div>
				<div>
					<h4>AppSecret</h4>
				</div>
				<div>
					<input type="text" id="appSecret" name="appSecret"
						value="${request.subscription.secretId}"
						placeholder="请输入AppSecret" required="required">
				</div>
				<div>
					<h4>微官网地址</h4>
				</div>
				<div>
					<input type="text" id="officialWebsite" name="officialWebsite"
						value="${request.subscription.officialWebsite}"
						placeholder="请输入微官网地址">
				</div>
				<div>
					<h4>piwikId</h4>
				</div>
				<div>
					<input type="text" id="piwikSiteId" name="piwikSiteId"
						   value="${request.subscription.piwikId}"
						   placeholder="请输入piwikSiteId">
				</div>
				<div>
					<input type="button" id="delBtn" value="删除"> <input
						type="button" id="editBtn" value="修改">
					<button id="rebuildUserBtn" class="show-page-loading-msg" 
					data-textonly="false" data-textvisible="true" data-msgtext="正在重建,请稍候..." data-inline="true">重建关注者账户</button>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#delBtn").click(function() {
				$("#form_data").attr("action", "${formDelAction}");
				$("#form_data").submit();
			});
			$("#editBtn").click(function() {
				$("#form_data").attr("action", "${formEditAction}");
				$("#form_data").submit();
			});
			$("#rebuildUserBtn").click(function(){
				showLoader();
				$.ajax({
					type : "GET",
					url : '${rebuildUserAction}',
					data : 'subscriptionId=' + $("#subscriptionId").val(),
					success : function(msg) {
						alert(msg.msg);
						hiderLoader();
					},
					failure : function(msg) {
						alert("服务器异常！");
						hiderLoader();
					}
				});
			});
			$("#form_data").submit(function() {
				$(":button", this).attr("disabled", true);
			});
			
			function showLoader() {
				var $this = $(this),
			        theme = $this.jqmData( "theme" ) || $.mobile.loader.prototype.options.theme,
			        msgText = $this.jqmData( "msgtext" ) || $.mobile.loader.prototype.options.text,
			        textVisible = $this.jqmData( "textvisible" ) || $.mobile.loader.prototype.options.textVisible,
			        textonly = !!$this.jqmData( "textonly" );
			        html = $this.jqmData( "html" ) || "";
			    	$.mobile.loading( "show", {
			            text: msgText,
			            textVisible: textVisible,
			            theme: theme,
			            textonly: textonly,
			            html: html
			    });
			}
			
			function hiderLoader() {
				$.mobile.loading( "hide" );
			};
			
			$("#appId").blur(function(){
				showLoader();
				$.ajax({
					type : "GET",
					url : '${checkAppIdAction}',
					data : 'appId=' + $("#appId").val() + "&subscriptionId=" + $("#subscriptionId").val(),
					success : function(msg) {
						if(msg.code == 1) {
							alert("已经存在该AppId,请检查");
						}
						hiderLoader();
					},
					failure : function(msg) {
						alert("服务器异常！");
						hiderLoader();
					}
				});
			});
		});
	</script>
</body>
</html>