<%--
  Created by IntelliJ IDEA.
  User: Taylor Davis
  Date: 2020/5/25
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--errorPage="error.jsp"--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <!--//tags -->
    <%--      <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">--%>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-3.5.1.min.js"></script>
    <%--      <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>
    <script src="js/bootstrap.min.js"></script>
    <title>EK Mall welcome you</title>
</head>
<style>
    body {
        background: url("${pageContext.request.contextPath}/images/background/b6.jpg") center;
        /*padding: 25px;*/
    }
</style>
<body>


<nav class="navbar navbar-default">

    <div class="container-fluid" style="font-weight: bold;font-size: medium">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">EK Mall</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">

                <c:if test="${sessionScope.loginUser==null}">
                    <li><a href="login.jsp">Sign in<span class="sr-only">(current)</span></a></li>
                </c:if>

                <c:if test="${sessionScope.loginUser!=null}">
                    <li><a href="info.jsp">${sessionScope.loginUser.username}</a></li>
                </c:if>

                <li><a href="signUp.jsp">Sign up</a></li>

                <li><a href="goods.jsp">Goods</a></li>
            </ul>

            <form class="navbar-form navbar-left" method="get" action="search.jsp">
                <div class="form-group">
                    <label>
                        <input type="text" class="form-control" placeholder="Search" name="name">
                    </label>
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>

            <ul class="nav navbar-nav navbar-right">
                <li><a href="about.jsp">About</a></li>
                <li>
                    <button class="btn btn-danger" type="button" style="margin-top: 8px">
                        <a href="shoppingCart.jsp" style="color: white;text-decoration: none">
                            Shopping Cart
                        </a>
                        <span class="badge">
                <c:if test="${sessionScope.loginUser==null}">0</c:if>
                <c:if test="${sessionScope.loginUser!=null}">${sessionScope.cartList.size()}</c:if>
              </span>
                    </button>
                </li>
                <c:if test="${sessionScope.loginUser!=null}">
                    <li><a href="info.jsp">My info</a></li>
                    <li><a href="logout.jsp">Log out</a></li>
                </c:if>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


</body>

</html>
