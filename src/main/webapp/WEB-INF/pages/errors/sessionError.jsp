<%@ page contentType="text/html;charset=UTF-8" %>
<script>

    <%
      String error = request.getParameter("error");
      if ("expire".equals(error)) {
    %>
    alert("您未登陆或使用的帐户超时；请重新登录")
    <%
      } else if ("logout".equals(error)) {
    %>
    alert("您所使用的账户已经从系统注销，请重新登录");
    <%
      }
    %>

    top.location.href = "${pageContext.request.contextPath}/app/login.jsp";
</script>