<%--
  Created by IntelliJ IDEA.
  User: huawei
  Date: 2020/7/4
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" language="java" %>

<html>
<body>

<div class="container" style="background-color: #b5b5b5">
    <div class="col-md-3">
        <h2><a href="index.jsp"><span>EK</span> Mall </a></h2>
        <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            Someone once said that music has two purposes. One is to delight people's senses with pure harmony,
            and the other is to inspire people's enthusiasm.
            We have all been cured by music, and we know that music seems to be the heater of the soul.
        </p>
    </div>
    <div class="col-md-9">
        <div>
            <div class="col-md-4 sign-gd">
                <h4><a href="goods.jsp"><span>Navigation</span></a></h4>
                <ul style="font-size: large">
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="goods.jsp">Adult</a></li>
                    <li><a href="goods.jsp">Children</a></li>
                    <li><a href="goods.jsp">Accessories</a></li>
                    <li><a href="about.jsp">About</a></li>
                    <%--<li><a href="contact.html">Contact</a></li>--%>
                </ul>
            </div>

            <div class="col-md-5">
                <a href="about.jsp"><h4>Store <span>Information</span></h4></a>
                <div>
                    <div>
                        <div>
                            <i aria-hidden="true"></i>
                        </div>
                        <div>
                            <h6 style="font-weight: bold;font-size: large">Phone Number</h6>
                            <p>+1 234 567 8901</p>
                        </div>

                    </div>
                    <div>
                        <div>
                            <i aria-hidden="true"></i>
                        </div>
                        <div>
                            <h6 style="font-weight: bold;font-size: large">Email Address</h6>
                            <p>Email :<a href="mailto:example@email.com"> 631999273@qq.com</a></p>
                        </div>

                    </div>
                    <div>
                        <div>
                            <i aria-hidden="true"></i>
                        </div>
                        <div>
                            <h6 style="font-weight: bold;font-size: large">Location</h6>
                            <p>BUPT, Changping, Beijing, China.</p>
                        </div>

                    </div>
                </div>
            </div>
            <div class="col-md-3 col-xs-12 sign-gd flickr-post">
                <a href="about.jsp"><h4>Flicker <span>Posts</span></h4></a>
                <a href="login.jsp" class="thumbnail">
                    <img src="images/me3.png" alt="...">
                </a>
            </div>
        </div>
    </div>
</div>

<div class="container-fluid" style="text-align: center">
    <p class="copy-right" style="background-color: #faf2cc;width: 100%;">
        &copy 2020 EK Mall. All rights reserved
        <a style="color: #7A7A7D;" href="https://beian.miit.gov.cn/#/Integrated/recordQuery" target="_blank"> <img
                style="width:18px;vertical-align: text-bottom;margin-left: 8px;" src="images/index.png"
                alt=""> 京ICP备2020040249号 </a>
    </p>
</div>


</body>
</html>
