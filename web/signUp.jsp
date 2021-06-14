<%--
  Created by IntelliJ IDEA.
  User: Taylor Davis
  Date: 2020/7/2
  Time: 21:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
    <style>
        .container {
            /*width: 500px;*/
            /*height: 450px;*/
            /*border: 1px red solid;*/
            /*margin-top: 100px;*/
            /*padding: 20px;*/
            background-color: #e2e2e2;
        }

    </style>
    <script type="text/javascript">
        var interval;
        function disableSending() {
            var $timer = $("#timer");
            $timer.removeClass("hidden");
            var $sendEmail = $("#sendEmail");
            $sendEmail.addClass("disabled");
            var $second = $("#second");
            var second = 29;
            interval = setInterval(function () {
                $second.html(second);
                second--;
                if (second===-1){
                    $timer.addClass("hidden");
                    $sendEmail.removeClass("disabled");
                }
            },1000);
        }
        function sendCheckCode() {
            clearInterval(interval);
            $("#second").html(30);
            var emailVal = $("#email").val();
            if (!checkEmail() || !checkUsername()) {
                return;
            }
            $.ajax({
                url: "${pageContext.servletContext.contextPath}/checkCodeServlet",
                type: "POST",
                data: {username: $('#username').val(), email: emailVal},
                success: function (data) {
                    generateMessage(data.signUp_Msg);
                    $("#checkCode").val(data.checkCode);//set value
                },
                error: function (data) {
                    generateMessage(data.signUp_Msg);
                },
                dataType: "json"
            });
            disableSending();
        }

        function submit() {
            if (checkUsername() && checkPassword() && checkPhone() && checkEmail()) {
                if ($("#code").val() === "") {
                    generateMessage("Please enter check code!");
                    return;
                }
                if ($("#checkCode").val() !== $("#code").val()) {
                    generateMessage("Check code incorrect!");
                    return;
                }
                $.ajax({
                    url: "${pageContext.servletContext.contextPath}/signUpServlet",
                    type: "POST",
                    data: {
                        username: $("#username").val(),
                        password: $("#password").val(),
                        telephone: $("#telephone").val(),
                        email: $("#email").val()
                    },
                    success: function (data) {
                        if (data.result===1) {
                            generateMessage("Welcome you to join us, " +
                                $("#username").val() +
                                "! Let's <a href='${pageContext.servletContext.contextPath}/login.jsp'>login</a>");
                        } else if(data.result===0){
                            generateMessage("Sorry, " +
                                $("#username").val() +
                                " is already existed! Please change another one!");
                        }else if (data.result===-1){
                            generateMessage("Sorry, " +
                                $("#email").val() +
                                " is already being used! Please change another one!");
                        }else {
                            generateMessage("Sorry, the database seems encounter some problem! Please try again");
                        }
                    },
                    dataType: "json"
                });
                //    成功提交，删除checkcode
                $("#checkCode").val("");
            }
        }

        function checkUsername() {
            if ($("#username").val() === "") {
                generateMessage("Please enter username!");
                return false;
            }
            return true;
        }

        function checkPassword() {
            if ($("#password").val() === "") {
                generateMessage("Please enter password!");
                return false;
            }
            if ($("#password2").val() !== $("#password").val()) {
                generateMessage("The passwords you enter are different!");
                return false;
            }
            return true;
        }

        function checkPhone() {
            if ($("#telephone").val() === "") {
                generateMessage("Please enter phone number!");
                return false;
            }
            return true;
        }

        function checkEmail() {
            if ($("#email").val() === "") {
                generateMessage("Please enter your email!");
                return false;
            }
            return true;
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
<div class="container" id="container">
    <h3 style="text-align: center;">User Register</h3>
    <form action="${pageContext.request.contextPath}/signUpServlet" id="infoForm" method="post">
        <!--属性组，在div中不水平展示-->
        <div class="form-group">
            <label for="username">Username：</label>
            <input type="text" name="username" class="form-control" id="username" placeholder="Please enter username">
        </div>

        <div class="form-group">
            <label for="password">Password：</label>
            <input type="password" name="password" class="form-control" id="password"
                   placeholder="Please enter password">
        </div>
        <div class="form-group">
            <label for="password2">Password：</label>
            <input type="password" name="password2" class="form-control" id="password2"
                   placeholder="Please enter password again">
        </div>
        <div class="form-group">
            <label for="telephone">Phone：</label>
            <input type="text" name="telephone" class="form-control" id="telephone"
                   placeholder="Please enter phone number">
        </div>
        <div class="form-group">
            <label for="email">Emali：</label>
            <input type="email" name="email" class="form-control" id="email" placeholder="Please enter email">
        </div>
        <!--属性展示，在div中水平展示-->
        <div class="form-inline" >
            <label for="code">Check code：</label>
            <input type="text" name="code" class="form-control" id="code" placeholder="Enter check code">
            <div class="form-group" style="text-align: center">
                <input type="hidden" name="code" class="form-control" id="checkCode">

                <a class="btn btn-danger" style="text-decoration: none;" id="sendEmail"
                   href="javaScript:sendCheckCode()">Send CheckCode</a>

                <button class="btn btn-primary hidden" type="button" id="timer">
                    Resend in <span class="badge" id="second">30</span>
                </button>
            </div>
            
        </div>
        <hr/>
        <div class="form-group" style="text-align: center;">
            <a class="btn btn-primary" style="text-decoration: none;"
               href="javaScript:submit()">Register</a>
        </div>
        
    </form>

    <div class="alert-dismissible" role="alert" id="message">

    </div>

</div>
</body>
</html>
<%@include file="propaganda.jsp" %>
<%@include file="footer.jsp" %>