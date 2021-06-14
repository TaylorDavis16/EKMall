<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/10/11
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Test</title>
</head>
<body>
    <c:forEach begin="0" end="1" var="j" varStatus="s">
        ${j} ${s.index} ${s.count} |
    </c:forEach>
</body>
</html>
