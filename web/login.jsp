<%--
  Created by IntelliJ IDEA.
  User: Taylor Davis
  Date: 2020/7/2
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp"%>
<c:if test="${sessionScope.loginUser!=null}">
    <%
        request.setAttribute("change_msg","You already login!");
        request.getRequestDispatcher("/info.jsp").forward(request,response);
    %>
</c:if>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>User login</title>
    <style>
        .container{
            /*width: 400px;*/
            /*height: 450px;*/
            /*border: 1px red solid;*/
            /*margin-top: 100px;*/
            /*padding: 20px;*/
            background-color: #e2e2e2;
        }
    </style>
    <script type="text/javascript">
        function refreshCode() {
            var codeImg=document.getElementById("codeImg");
            codeImg.src="${pageContext.servletContext.contextPath}/checkCodeServlet?"+new Date().getTime();
        }

        window.onload=function(){
            $("#searchForm").submit(function () {
                if ($("#username").val()===""){
                    alert("Please enter username!");
                    return false;
                }
                if ($("#password").val()===""){
                    alert("Please enter password!");
                    return false;
                }
                if ($("#code").val()===""){
                    alert("Please enter check code!");
                    return false;
                }
                return true;
            });
        }
        
        function findPassword() {
            var email = window.prompt("Please enter your registered email:","");
            var reg = /^([a-zA-Z]|[0-9])(\w|-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
            if (!reg.test(email)){
                generateMessage("You have to enter a valid email address to find your password!");
                return;
            }
            generateMessage("The server is about to send a email to your email "+email+", please wait!");
            $.ajax({
                url:"${pageContext.servletContext.contextPath}/findEmailServlet",
                type:"POST",
                data:{email:email},
                success:function (data) {
                    console.log(data);
                    generateMessage(data.resetMsg);
                    if (data.status){
                        var checkCode;
                        do {
                            checkCode = window.prompt("Success! Please enter the checkcode just sent to your email!","");
                        }while (checkCode!==data.checkCode && window.confirm("Check code incorrect! Do you want do enter again?"));
                        if (checkCode===data.checkCode){
                            $.ajax({
                                url:"${pageContext.servletContext.contextPath}/resetPasswordServlet",
                                type:"POST",
                                data:{secondAccess:data.secondAccess,email:email},
                                success:function (data) {
                                    console.log(data);
                                    generateMessage(data.resetMsg);
                                },
                                dataType: "json"
                            });
                        }else {
                            generateMessage("The check code you just entered is incorrect!");
                        }
                    }
                },
                dataType:"json"
            });
        }

        function generateMessage(message) {

            var $message = $("#message");
            if ($message!==null){
                $message.empty();
                $message.addClass("alert");
                $message.addClass("alert-warning");
                $message.append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">"+
                    "<span aria-hidden=\"true\">&times;</span></button><strong>" +
                    message+
                    "</strong>");
            }

        }
        
    </script>
</head>
<body>
<div class="container">
    <h3 style="text-align: center;">User Login</h3>
    <span style="float: right;">
        <a href="http://www.dongdong16.com/manageEKMall/login.jsp" target="_blank" class="label label-info" style="font-weight: bold;font-size: small">
            I am merchant
        </a>
    </span>
    <form action="${pageContext.request.contextPath}/loginServlet" id="searchForm" method="post">
        <!--属性组，在div中不水平展示-->
        <div class="form-group">
            <label for="username">Username：</label>
            <input type="text" name="username" class="form-control" id="username" placeholder="Please enter user name">
        </div>

        <div class="form-group">
            <label for="password">Password：</label>
            <input type="password" name="password" class="form-control" id="password" placeholder="Please enter the password">
        </div>
        <!--属性展示，在div中水平展示-->
        <div class="form-inline">
            <label for="code">Check Code：</label>
            <input type="text" name="code" class="form-control" id="code" placeholder="Enter Check code">
            <a href="javascript:refreshCode();">
                <img src="${pageContext.request.contextPath}/checkCodeServlet" id="codeImg" title="Click on the picture to switch" alt="failed">
            </a>
            <a class="btn btn-danger" style="text-decoration: none;float: right" id="findPassword"
               href="javaScript:findPassword()">Forget password?</a>
        </div>
        <hr/>
        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="submit" value="Login">
        </div>
    </form>
    
    <c:if test="${requestScope.login_msg!=null}">
        <div class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <strong>${requestScope.login_msg}</strong>
        </div>
    </c:if>
    <div class="alert-dismissible" role="alert" id="message">

    </div>
</div>
</body>
</html>
<%@include file="propaganda.jsp"%>
<%@include file="footer.jsp"%>