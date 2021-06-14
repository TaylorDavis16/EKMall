<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/5
  Time: 0:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<script>
    window.onload=function () {
        document.getElementById("searchForm").onsubmit=function () {
            var amount = document.getElementById("amount").value;
            if (amount<=0){
                alert("Please enter a positive number");
                return false;
            }
            return true;
        }
    }
</script>
<body>
    <c:if test="${sessionScope.loginUser==null}">
        <%
            request.setAttribute("login_msg","Please login first!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        %>
    </c:if>
    <div class="container">
        <%@include file="propaganda.jsp"%>
        <div class="panel panel-primary">
            <div class="panel-heading">Your remain balance is:</div>
            <div class="panel-body">${sessionScope.loginUser.balance}</div>
        </div>
        <form method="post" id="searchForm" action="${pageContext.request.contextPath}/topUpServlet">
            <input type="hidden" name="cid" value="${sessionScope.loginUser.cusID}">
            <div class="form-inline">
                <label for="amount"><input type="number" name="amount" id="amount"></label>
                <input class="btn btn btn-primary" type="submit" value="Top Up">
            </div>
        </form>
        <c:if test="${requestScope.topUp_msg!=null}">
            <div class="alert alert-warning alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>${requestScope.topUp_msg}</strong>
            </div>
        </c:if>
    </div>
</body>
</html>
<%@include file="footer.jsp"%>
