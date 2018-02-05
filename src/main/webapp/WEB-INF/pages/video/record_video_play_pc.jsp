<%--
  Created by IntelliJ IDEA.
  User: jjj
  Date: 15-8-18
  Time: 下午6:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title><s:text name="title_record_video_play_page"/></title>
  <script src="http://test.kids.smart-f.cn/wecarekids/swfobject.js"></script>
</head>
<body>
<div id="player"></div>
<script>
    var flashvars = {
        // M3U8 url, or any other url which compatible with SMP player (flv, mp4, f4m)
        // escaped it for urls with ampersands
        src: escapre("http://update.smart-f.cn/1439775852969KCAY.m3u8"),
        //src: escape("http://update.smart-f.cn/1439775852969KCAY.m3u8"),
        //src: escape("http://update.smart-f.cn/diaosi.m3u8"),
        //src: escape("http://static-10001988.file.myqcloud.com/test/IMG_0230.MOV"),
        // url to OSMF HLS Plugin
        plugin_m3u8: "http://test.kids.smart-f.cn/wecarekids/HLSProviderOSMF.swf",
    };
    var params = {
        // self-explained parameters
        allowFullScreen: true,
        allowScriptAccess: "always",
        bgcolor: "#000000"
    };
    var attrs = {
        name: "player"
    };

    swfobject.embedSWF(
        // url to SMP player
        "http://test.kids.smart-f.cn/wecarekids/StrobeMediaPlayback.swf",
        // div id where player will be place
        "player",
        // width, height
        "800", "485",
        // minimum flash player version required
        "10.2",
        // other parameters
        null, flashvars, params, attrs
    );
</script>
</body>
</html>
