<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/2
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" language="java" %>
<%@include file="navigationBar.jsp"%>

<html>
<head>
    <title>About</title>
    <style>
        img{
            border-radius: 5%;
        }
    </style>
</head>
<body>
<div class="banner_bottom_agile_info">
    <div class="container-fluid">
        <div class="agile_ab_w3ls_info">
            <div class="col-md-6 ab_pic_w3ls">
                <img src="images/qin-pink.jpg" alt=" " class="img-responsive" style="margin-top: 20px"/>
            </div>
            
            <div class="list-group col-md-6 col-xs-12" style="margin-top: 20px">
                <a href="${pageContext.request.contextPath}/index.jsp" class="list-group-item active">
                    <h4 class="list-group-item-heading"><h3>About Our EK <span class="label label-success" style="font-size: x-large"> Mall</span></h3></h4>
                    <div>
                        <p class="list-group-item-text">
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Someone once said that music has two purposes.
                            One is to delight people's senses with pure harmony, and the other is to inspire people's enthusiasm.
                            We have all been cured by music, and we know that music seems to be the heater of the soul.
                            For true healing, both mind and body must be on the road.
                            Someone once said that music has two purposes.
                            One is to delight people's senses with pure harmony, and the other is to inspire people's enthusiasm.
                            We have all been cured by music, and we know that music seems to be the heater of the soul.
                            For true healing, both mind and body must be on the road.
                        </p>
                    </div>
                </a>
                
            </div>

            <div class="list-group col-md-6 col-xs-12">
                <a href="${pageContext.request.contextPath}/goods.jsp" class="list-group-item">
                    <h4 class="list-group-item-heading">The sound of the keyboard is actually the same as that of fine wine. </h4>
                    <p class="list-group-item-text" style="background: #00acee">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Good sound has layers,
                        and the taste of each section is different.
                        Our company is committed to creating the best music for you. May you find your music here.
                    </p>
                </a>
            </div>

            <div class="list-group col-md-6 col-xs-12">
                <a href="${pageContext.request.contextPath}/goods.jsp" class="list-group-item">
                    <h4 class="list-group-item-heading">The sound of the keyboard is actually the same as that of fine wine. </h4>
                    <p class="list-group-item-text">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;For true healing, both mind and body must be on the road.
                        One is to delight people's senses with pure harmony, and the other is to inspire people's enthusiasm.
                        We have all been cured by music, and we know that music seems to be the heater of the soul.
                        For true healing, both mind and body must be on the road.
                    </p>
                </a>
            </div>
            
            
        </div>
        <div class="img-thumbnail" style="background: #a6e1ec">
            <div class="col-md-6 wthree_banner_bottom_grid_three_left1 grid">
                <figure class="effect-roxy">
                    <img src="images/qin-black.jpg" alt=" " class="img-responsive" />
                    <figcaption>
                        <h3><span class="label label-primary" style="font-size: x-large">Fall</span> Ahead</h3>
                        <div class="panel panel-info">
                            <div class="panel-heading" style="font-size: large;font-weight: bold">Fight for the best!</div>
                        </div>
                        
                    </figcaption>
                </figure>
            </div>
            <div class="col-md-6 wthree_banner_bottom_grid_three_left1 grid">
                <figure class="effect-roxy">
                    <img src="images/erji.jpg" alt=" " class="img-responsive" />
                    <figcaption>
                        <h3><span class="label label-success" style="font-size: x-large">Never</span> look back</h3>
                        <div class="panel panel-warning">
                            <div class="panel-heading" style="font-size: large;font-weight: bold">Prepare for the worst!</div>
                        </div>
                    </figcaption>
                </figure>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%@include file="propaganda.jsp"%>
<%@include file="footer.jsp"%>
