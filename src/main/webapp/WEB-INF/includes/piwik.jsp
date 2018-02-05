<%@page import="org.springframework.core.env.Environment" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/includes/common-tags.jsp" %>
<!-- Tencent Ta -->
<%
  Environment environment = (Environment) request.getAttribute("environment");
  String taSiteId = environment.getProperty("system.wa.ta.sid");
  request.setAttribute("taSiteId", taSiteId);
%>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=${taSiteId}" charset="UTF-8"></script>
<!-- End Tencent Ta Code -->

<!-- Piwik -->
<%
  String piwikIp = environment
      .getProperty("system.wa.piwik.wan.server.host");
  request.setAttribute("piwikIp", piwikIp);
  String siteId = environment
      .getProperty("system.wa.piwik.siteid");
  request.setAttribute("siteId", siteId);
%>
<script type="text/javascript">
    var _paq = _paq || [];
    _paq.push(['trackEvent', '页面', '页面访问', '页面总浏览量', 1]);
    _paq.push(['trackPageView']);
    _paq.push(['enableLinkTracking']);
    (function () {
        var u = (("https:" == document.location.protocol) ? "https" : "http")
            + "://${piwikIp}/";
        _paq.push(['setDocumentTitle', document.title]);
        _paq.push(['setTrackerUrl', u + 'piwik.php']);
        _paq.push(['setSiteId', '${empty request.piwikSiteId ?siteId:request.piwikSiteId}']);
        _paq.push(['setCustomVariable', 1, '用户', '${openId}', "visit"]);
        var d = document, g = d.createElement('script'), s = d
            .getElementsByTagName('script')[0];
        g.type = 'text/javascript';
        g.defer = true;
        g.async = true;
        g.src = u + 'piwik.js';
        s.parentNode.insertBefore(g, s);
    })();
</script>
<noscript>
  <p>
    <img src="http://${piwikIp}/piwik.php?idsite=${siteId}"
         style="border: 0;" alt=""/>
  </p>
</noscript>
<!-- End Piwik Code -->