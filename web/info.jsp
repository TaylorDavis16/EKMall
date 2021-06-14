<%@ page import="java.util.List" %>
<%@ page import="domain.Order" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %><%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/3
  Time: 9:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp" %>
<c:if test="${sessionScope.loginUser==null}">
    <%
        request.setAttribute("login_msg", "Please sign in first!");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    %>
</c:if>

<c:if test="${sessionScope.orderList==null}">
    <%
        response.sendRedirect(request.getContextPath() + "/loadOrdersServlet");
    %>
</c:if>
<html>
<head>
    <title>Title</title>
    <style>

        /*tr > td {*/
        /*    font-size: 15px;*/
        /*}*/

        li a:hover {
            cursor: pointer;
        }

        #labels {
            padding: 5px;
            font-size: medium;
        }


    </style>
    <script type="text/javascript">
        window.onload = function () {
            <c:if test="${requestScope.gotoOrder!=null}">
            $("#page1").removeClass("active");
            $("#page2").addClass("active");
            $("#info").removeClass("in");
            $("#info").removeClass("active");
            $("#order").addClass("in");
            $("#order").addClass("active");
            </c:if>
            $("#infoForm").submit(function () {
                if ($("#username").val() === "") {
                    alert("Please enter username!");
                    return false;
                }
                if ($("#password").val() === "") {
                    alert("Please enter password!");
                    return false;
                }
                if ($("#password2").val() !== $("#password").val()) {
                    alert("The passwords you enter are different!");
                    return false;
                }
                if ($("#telephone").val() === "") {
                    alert("Please enter phone number!");
                    return false;
                }
                if ($("#email").val() === "") {
                    alert("Please enter your email!");
                    return false;
                }
                return true;
            })
            $("#searchForm").submit(function () {

                $("#refundable").val($("#s1").html())
                $("#status").val($("#s2").html())
                $("#sortMethod").val($("#s3").html())
                return true
            })
            $("#cancel").click(function () {
                var checkBoxs = getCheckBoxs();
                var selected = false;
                for (var i = 0; i < checkBoxs.length; i++) {
                    if (checkBoxs[i].checked) {
                        selected = true;
                        break;
                    }
                }
                if (!selected) {
                    alert("You do not select any orders to cancel!");
                } else {
                    var form = document.getElementById("orderForm");
                    form.action = "${pageContext.request.contextPath}/cancelOrderServlet";
                    form.submit();
                }
            })
            changeLabel("yes", "s1", "Yes");
            changeLabel("no", "s1", "No");
            changeLabel("refundableAll", "s1", "All");
            changeLabel("normal", "s2", "Normal");
            changeLabel("refunded", "s2", "Refunded");
            changeLabel("statusAll", "s2", "All");
            changeLabelAndSubmit("sortTime1", "s3", "Now");
            changeLabelAndSubmit("sortTime2", "s3", "Before");
            changeLabelAndSubmit("sortPay1", "s3", "Low to high");
            changeLabelAndSubmit("sortPay2", "s3", "High to low");
            changeLabelAndSubmit("sortReset", "s3", "Default");

            $("#resetAll").click(function () {
                var searchForm = document.getElementById("searchForm");
                searchForm.action = "${pageContext.request.contextPath}/clearOrderConfigServlet";
                searchForm.submit();
            })

            $("#select").click(function () {
                var cbs = getCheckBoxs();
                var selectVal = $("#selectValue").val()
                for (var i = 0; i < cbs.length; i++) {
                    //反选
                    cbs[i].checked = selectVal != 0;
                }
                $("#selectValue").val(selectVal == 0 ? 1 : 0)
            })
        }


        function getCheckBoxs() {
            return document.getElementsByName("mid");
        }

        function changeLabel(tagId, labelId, value) {
            $("#" + tagId).click(function () {
                $("#" + labelId).html(value);
            })
        }

        function changeLabelAndSubmit(tagId, labelId, value) {
            $("#" + tagId).click(function () {
                $("#" + labelId).html(value);
                $('#searchForm').submit();
            })
        }

    </script>
</head>
<body>
<div class="container-fluid">

    <ul id="myTab" class="nav nav-pills">
        <li class="active" id="page1"><a href="#info" data-toggle="tab">Profile</a></li>
        <li id="page2"><a href="#order" data-toggle="tab">Orders</a></li>
    </ul>

    <div id="myTabContent" class="tab-content">

        <div class="tab-pane fade in active" id="info">

            <div class="panel panel-info">
                <div class="panel-heading">
                    <h3 class="panel-title" style="text-align: center;">My information</h3>
                </div>
                <div class="panel-body">
                    <form action="${pageContext.request.contextPath}/updateInfoServlet" id="infoForm" method="post"
                          class="form-group">

                        <label for="cusID">
                            <input type="hidden" name="cusID" value="${sessionScope.loginUser.cusID}" id="cusID">
                        </label>
                        <!--属性组，在div中不水平展示-->
                        <div class="form-group">
                            <label for="username">Username：</label>
                            <input type="text" name="username" class="form-control" id="username"
                                   value="${sessionScope.loginUser.username}">
                        </div>
                        <div class="form-group">
                            <label for="password">Password：</label>
                            <input type="password" name="password" class="form-control" id="password"
                                   value="${sessionScope.loginUser.password}">
                        </div>
                        <div class="form-group">
                            <label for="password2">Password：</label>
                            <input type="password" name="password2" class="form-control" id="password2"
                                   value="${sessionScope.loginUser.password}">
                        </div>
                        <div class="form-group">
                            <label for="telephone">Phone：</label>
                            <input type="text" name="telephone" class="form-control" id="telephone"
                                   value="${sessionScope.loginUser.telephone}">
                        </div>
                        <div class="form-group">
                            <label for="email">Emali：</label>
                            <input type="email" name="email" class="form-control" id="email"
                                   value="${sessionScope.loginUser.email}">
                        </div>
                        <div class="form-group">
                            <label for="address">Address：</label>
                            <input type="text" name="address" class="form-control" id="address"
                                   value="${sessionScope.loginUser.address}">
                        </div>
                        <div class="form-group">
                            <span class="label label-danger" style="padding: 7px; font-size: medium">balance</span>
                            &nbsp;${sessionScope.loginUser.balance}
                            <span style="margin-left: 20px">
                        <a class="btn btn-default" href="${pageContext.request.contextPath}/topUp.jsp">Top up</a>
                    </span>
                        </div>
                        <hr/>
                        <div class="form-group" style="text-align: center;">
                            <input class="btn btn btn-primary" type="submit" value="Change">
                        </div>
                    </form>

                    <c:if test="${requestScope.change_msg!=null}">
                        <div class="alert alert-warning alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <strong>${requestScope.change_msg}</strong>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>


        <div class="tab-pane fade" id="order">
            <div class="panel panel-info">
                <!-- Default panel contents -->
                <div class="panel-heading">
                    <h3 class="panel-title" style="text-align: center;">My orders</h3>
                    <c:if test="${requestScope.cancel_Msg!=null}">
                        <div class="alert alert-warning alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <strong>${requestScope.cancel_Msg}</strong>
                        </div>
                    </c:if>
                    <div style="height: 30px;">
                        <span style="font-size: medium">Hi,${sessionScope.loginUser.username}! This is your orders!</span>
                        <span style="float: right">
                        <a class="label label-info" id="labels" href="${pageContext.request.contextPath}/topUp.jsp">Top up</a>
                        </span>
                    </div>


                    <form class="form-inline" method="post" id="searchForm"
                          action="${pageContext.request.contextPath}/loadOrdersServlet">
                        <div class="row" style="text-align: center">
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="form-group">
                                    <label for="name">Name:</label>
                                    <input type="text" name="name" class="form-control" id="name"
                                           value="${sessionScope.orderConfig.name}">
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="form-group">
                                    <label for="brand">Brand:</label>
                                    <input type="text" name="brand" class="form-control" id="brand"
                                           value="${sessionScope.orderConfig.brand}">
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="form-group">
                                    <label for="startTime">Start time:
                                        <input type="date" name="startTime" class="form-control" id="startTime"
                                               value="${sessionScope.orderConfig.startTime}">
                                    </label>
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="form-group">
                                    <label for="endTime">End time:
                                        <input type="date" name="endTime" class="form-control" id="endTime"
                                               value="${sessionScope.orderConfig.endTime}">
                                    </label>
                                </div>
                            </div>

                            <label for="refundable">
                                <input type="hidden" name="refundable" value="${sessionScope.orderConfig.refundable}"
                                       id="refundable">
                            </label>

                            <label for="status">
                                <input type="hidden" name="status" value="${sessionScope.orderConfig.status}"
                                       id="status">
                            </label>

                            <label for="sortMethod">
                                <input type="hidden" name="sortMethod" value="${sessionScope.orderConfig.sortMethod}"
                                       id="sortMethod">
                            </label>
                        </div>

                        <div class="row" style="text-align: center">
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="btn-group">
                                    <button id="rf" type="button" class="btn btn-success dropdown-toggle open"
                                            data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="true">
                                        Refundable <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a id="yes">Yes</a></li>
                                        <li><a id="no">No</a></li>
                                        <li role="separator" class="divider"></li>
                                        <li><a id="refundableAll">All</a></li>
                                    </ul>
                                </div>
                                <p style="font-size: x-large;">
                                    <span class="label label-default"
                                          id="s1">${sessionScope.orderConfig.refundable}</span>
                                </p>
                            </div>
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-success dropdown-toggle open"
                                            data-toggle="dropdown"
                                            aria-haspopup="true" aria-expanded="true">
                                        Status <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a id="normal">Normal</a></li>
                                        <li><a id="refunded">Refunded</a></li>
                                        <li role="separator" class="divider"></li>
                                        <li><a id="statusAll">All</a></li>
                                    </ul>
                                </div>
                                <p style="font-size: x-large;">
                                    <span class="label label-default"
                                          id="s2">${sessionScope.orderConfig.status}</span>
                                </p>
                            </div>
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="form-group">
                                    <input class="btn btn btn-danger" type="submit" value="Search">
                                </div>
                            </div>
                            <div class="col-sm-3 col-md-3 col-xs-6">
                                <div class="form-group">
                                    <a class="btn btn-danger" style="text-decoration: none;" id="resetAll">Reset</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>


                <div class="panel-body">
                    <form method="post" id="orderForm" action="goods.jsp">
                        <!-- Table -->
                        <div class="table-responsive">
                            <table class="table table-hover table-cell">
                                <tr class="success">
                                    <th><span class="label label-success" id="labels">Order id</span></th>
                                    <th><span class="label label-success" id="labels">Name</span></th>
                                    <th><span class="label label-success" id="labels">Brand</span></th>
                                    <th><span class="label label-success" id="labels">Number</span></th>
                                    <th><span class="label label-success" id="labels">Payment</span></th>
                                    <th><span class="label label-success" id="labels">Merchant id</span></th>
                                    <th><span class="label label-success" id="labels">Time</span></th>
                                    <th><span class="label label-success" id="labels">Refundable</span></th>
                                    <th><span class="label label-success" id="labels">Address</span></th>
                                    <th><span class="label label-success" id="labels">ShippingStatus</span></th>
                                    <th><span class="label label-success" id="labels">Status</span></th>
                                    <th>
                                        <span>
                                            <a id="select" class="label label-danger"
                                               style="text-decoration: none;padding: 5px; font-size: medium">Select</a>
                                        </span>
                                    </th>
                                </tr>
                                <input type="hidden" value="1" id="selectValue">

                                <c:forEach items="${sessionScope.orderList}" var="j">
                                    <tr>
                                        <td>${j.orderId}</td>
                                        <td>
                                            <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${j.merchandise_mID}" style="text-decoration: none;">
                                                    ${j.name}
                                            </a>
                                        </td>
                                        <td>${j.brand}</td>
                                        <td>${j.number}</td>
                                        <td>${j.payment}</td>
                                        <td>${j.merchant_merID}</td>
                                        <td>${j.generateTime}</td>
                                        <td>${j.refundable==0 ? "No" : "Yes"}</td>
                                        <td>${j.address}</td>
                                        <td>${j.shippingStatus}</td>
                                        <td>${j.status}</td>
                                        <td>
                                            <div class="checkbox">
                                                <label for="mid${j.orderId}">
                                                    <input class="form-control" type="checkbox" name="mid"
                                                           id="mid${j.orderId}" value="${j.orderId}">
                                                </label>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>


                        <div class="form-inline" style="text-align: center">
                            <%--&lt;%&ndash;                     将Pay交给sumbit；delete和save交给js帮助，这两比较简单&ndash;%&gt;--%>
                            <%--<a id="sort" class="btn btn-primary" style="text-decoration: none">SortByTime</a>--%>
                            <%--&lt;%&ndash;                        <a id="save" class="btn btn-success" style="text-decoration: none">Save</a>&ndash;%&gt;--%>
                            <a id="cancel" class="btn btn-danger" style="text-decoration: none">Cancel</a>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    Sort by <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><a id="sortTime1">Time: Latest</a></li>
                                    <li><a id="sortTime2">Time: Early</a></li>
                                    <li><a id="sortPay1">Payment: Low to high</a></li>
                                    <li><a id="sortPay2">Payment: High to low</a></li>
                                    <li role="separator" class="divider"></li>
                                    <li><a id="sortReset">Default</a></li>
                                </ul>
                            </div>
                            <div class="btn-group">
                                <p style="font-size: x-large;">
                                <span class="label label-default"
                                      id="s3">${sessionScope.orderConfig.sortMethod}</span>
                                </p>
                            </div>
                                
                        </div>
                        
                    </form>

                    
                </div><%--panel body--%>

                <div class="row" style="text-align: center">
                    <nav aria-label="Page navigation">
                        <ul class="pagination pagination-lg">
                            <li>
                                <a href="#" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <li class="active"><a href="#">1</a></li>
                            
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#">5</a></li>
                            <li>
                                <a href="#" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div><%--panel--%>
            

        </div>

    </div>

</div>

</body>
</html>
<%@include file="footer.jsp" %>