<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/2
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp"%>
<html>
<head>
    <title>Til</title>
    <%
        session.removeAttribute("loginUser");
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    %>
</head>
<body>
    
</body>
</html>
