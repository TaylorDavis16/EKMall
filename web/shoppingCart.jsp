<%@ page import="domain.User" %>
<%@ page import="service.interfaces.CartService" %>
<%@ page import="service.impl.CartServiceImpl" %>
<%@ page import="domain.Cart" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/2
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp" %>
<c:if test="${sessionScope.loginUser==null}">
    <%
        request.setAttribute("login_msg", "Please login first!");
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    %>
</c:if>

<c:if test="${requestScope.isRefresh==null}">
    <%
        response.sendRedirect(request.getContextPath() + "/loadCartServlet");
    %>
</c:if>
<html>
<head>
    <title>Title</title>
    <style>
        tr > td {
            font-size: large;
        }

        #labels {
            padding: 5px;
            font-size: medium;
        }
    </style>
</head>
<script>
    window.onload = function () {
        $("#pay").click(function () {
            var checkBoxs = getCheckBoxs();
            var selected = false;
            for (var i = 0; i < checkBoxs.length; i++) {
                if (checkBoxs[i].checked) {
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                alert("You do not select anything to pay!");
            } else {
                if (confirm("Are you sure to pay?")){
                    var form = document.getElementById("cartForm");
                    form.action = "${pageContext.request.contextPath}/payServlet";
                    form.submit();
                }
            }
        }) 
        // document.getElementById("save").onclick=function () {
        //     alert("2");
        // }
        $("#delete").click(function () {
            var checkBoxs = getCheckBoxs();
            var selected = false;
            for (var i = 0; i < checkBoxs.length; i++) {
                if (checkBoxs[i].checked) {
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                alert("You do not select anything to delete!");
            } else {
                if (confirm("Are you sure to delete?")){
                    var form = document.getElementById("cartForm");
                    form.action = "${pageContext.request.contextPath}/deleteCartServlet";
                    form.submit();
                }
            }
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


</script>
<body>

<div class="container-fluid">

    <div class="panel panel-primary">
        <!-- Default panel contents -->
        <div class="panel-heading">
            <c:if test="${sessionScope.add_msg!=null}">
                <div class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${sessionScope.add_msg}</strong>
                </div>
            </c:if>
            <c:if test="${sessionScope.pay_msg!=null}">
                <div class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${sessionScope.pay_msg}</strong>
                </div>
            </c:if>

            <c:if test="${sessionScope.delete_msg!=null}">
                <div class="alert alert-warning alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${sessionScope.delete_msg}</strong>
                </div>
            </c:if>
            Hi,${sessionScope.loginUser.username}! This is your shopping cart!
            <span style="float: right">
                    <a class="label label-default" href="${pageContext.request.contextPath}/topUp.jsp" id="labels">Top up</a>
                </span>
        </div>
        <div class="panel-body">
            <form method="post" id="cartForm" action="goods.jsp">
                <!-- Table -->
                <div class="table-responsive">
                    <table class="table table-hover">

                        <tr class="success">
                            <th><span class="label label-success" id="labels">Product Name</span></th>
                            <th><span class="label label-success" id="labels">Category</span></th>
                            <th><span class="label label-success" id="labels">Brand</span></th>
                            <th><span class="label label-success" id="labels">Price(each)</span></th>
                            <th><span class="label label-success" id="labels">Discount</span></th>
                            <th><span class="label label-success" id="labels">Number</span></th>
                            <th>
                                    <span>
                                        <a id="select" class="label label-danger"
                                           style="text-decoration: none;padding: 5px; font-size: medium">Select</a>
                                    </span>
                            </th>
                        </tr>
                        <input type="hidden" value="1" id="selectValue">

                        <c:forEach items="${sessionScope.cartList}" var="j">
                            <tr>
                                <td>
                                    <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${j.mid}" style="text-decoration: none;">
                                            ${sessionScope.productMap.get(j.mid).name}
                                    </a>
                                </td>
                                <td>${sessionScope.productMap.get(j.mid).category}</td>
                                <td>${sessionScope.productMap.get(j.mid).brand}</td>
                                <td>${sessionScope.productMap.get(j.mid).price}</td>
                                <td>${sessionScope.productMap.get(j.mid).discount}</td>
                                <td>${j.number}</td>
                                <td>
                                    <div class="checkbox">
                                        <label>
                                            <input class="form-control" type="checkbox" name="mid" value="${j.mid}">
                                        </label>
                                    </div>

                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>

                <div class="form-inline" align="center">
                    <%--                     将Pay交给sumbit；delete和save交给js帮助，这两比较简单--%>
                    <a id="pay" class="btn btn-primary" style="text-decoration: none">Pay</a>
                    <%--                        <a id="save" class="btn btn-success" style="text-decoration: none">Save</a>--%>
                    <a id="delete" class="btn btn-danger" style="text-decoration: none">Delete</a>
                </div>
            </form>
        </div>
            
    </div>

</div>

</body>
</html>
<%@include file="propaganda.jsp" %>
<%@include file="footer.jsp" %>
