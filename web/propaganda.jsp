<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/3
  Time: 10:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        #aboutPhoto {
            background: url("images/background/b3.jpg") center;
        }
    </style>
</head>
<body>

<div class="container" style="align-items: center">
    <div class="jumbotron" id="aboutPhoto">

        <h1 style="color: #17c3a2; font-weight: bold">Nice to see you!!!!!</h1>
        <p style="color: black; font-weight: bold">We are EK Mall, has been dedicated for manufacturing and selling
            keyboard and its accessories for 20 years!</p>
        <p><a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/about.jsp" role="button">Learn
            more</a></p>

    </div>
</div>


</body>
</html>
