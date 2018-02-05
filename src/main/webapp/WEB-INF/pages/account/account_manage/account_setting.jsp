<%@page import="cn.smart.cloud.utils.PropertiesResource"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/includes/common-tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/WEB-INF/includes/common-res.jsp"%>
<title>用户信息</title>
</head>
<body>
    <c:set var="defaultUserIconUrl"
        value="http://static-10001988.file.myqcloud.com/resource/images/common/default_user_icon.png" />
    <s:url var="shareCameraActionUrl" value="/account/account_manage/share_cameras.action" />
    <s:url var="shareAccountActionUrl" value="/account/account_manage/share_account.action" />
    <div data-role="page">
        <ul data-role="listview">
            <li><img src="${defaultUserIconUrl}">
                <h2>${request.toAccount.aliasName}</h2>
                <p>已分配：${toAccount.accountCreationQuota}账户，${fn:length(toAccount.accountCameras)}摄像头</p></li>
            <c:if test="${accountBalance > 0}">
                <form name="shareAccountForm" id="shareAccountForm">
                    <li>
                        <div class="ui-content jqm-content" data-role="collapsible" data-iconpos="right" data-inset="true">
                            <h2>添加账户</h2>
                            <input name="openId" type="hidden" value="${openId}" form="shareAccountForm">
                            <input name="accountId" type="hidden" value="${toAccount.id}" form="shareAccountForm">
                            <label for="shareQuota">输入账户个数</label> <input type="range" name="shareQuota" id="shareQuota"
                                form="shareAccountForm" value="1" min="1" max="${accountBalance}">
                            <input
                                type="submit" form="shareAccountForm" class="ui-btn" value="确认添加">
                        </div>
                    </li>
                </form>
            </c:if>
            <c:if test="${fn:length(request.accountCameras) > 0 and fn:length(request.accountCameras) > fn:length(request.toAccountCameraIds)}">
                <form name="shareCameraForm" id="shareCameraForm">
                    <li>
                        <div class="ui-content jqm-content" data-role="collapsible" data-iconpos="right" data-inset="true">
                            <h2>添加摄像头</h2>
                            <input name="openId" type="hidden" value="${openId}" form="shareCameraForm">
                            <input name="accountId" type="hidden" value="${toAccount.id}" form="shareCameraForm">
                            <s:iterator value="#request.accountCameras" id="accountCamera" status="status">
                                <s:if test="#accountCamera.camera.id not in #request.toAccountCameraIds">
                                    <label> <input type="checkbox" form="shareCameraForm" name="cameraIds"
                                            value="${accountCamera.camera.id}">
                                        ${request.accountCamera.camera.name}
                                    </label>
                                </s:if>
                            </s:iterator>
                            <input type="submit" form="shareCameraForm" class="ui-btn" value="确认添加">
                        </div>
                    </li>
                </form>
            </c:if>
            <li>他可用的摄像头：</li>
            <s:iterator value="#request.toAccount.accountCameras" id="accountCamera" status="status">
                <li>${empty request.accountCamera.camera.name ? '摄像头'.concat(status.count) : request.accountCamera.camera.name}
                </li>
            </s:iterator>
        </ul>
    </div>

	<%@ include file="/WEB-INF/includes/piwik.jsp"%>

	<script type="text/javascript">
                    $(function() {
                        $("#shareCameraForm").submit(
	                        function() {
	                        	showLoader();
	                            $.post("${shareCameraActionUrl}", $("#shareCameraForm").serialize(),
	                                    function(data) {
	                            			hiderLoader();
	                                        location.reload();
	                                    });
	                            return false;
	                        });
                        $("#shareAccountForm").submit(
	                        function() {
	                        	showLoader();
	                            $.post("${shareAccountActionUrl}", $("#shareAccountForm").serialize(),
	                                    function(data) {
	                            			hiderLoader();
	                                        location.reload();
	                                    });
	                            return false;
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
            			}
                    });
                </script>
</body>
</html>