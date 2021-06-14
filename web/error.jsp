<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/2
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"  language="java" isErrorPage="true" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h3>Error</h3>
    <a href="navigationBar.jsp">Return to home</a>
    <%
        if (exception!=null){
            String message = exception.getMessage();
            out.print("<h3>Cause by "+message+"</h3>");
            out.print("The server is busy!!!!!");
        }else out.print("<h2>It is not cause by an exception, but something went wrong!</h2>");
        
    %>
</body>
</html>
