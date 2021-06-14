<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="domain.Product" %><%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/3
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp" %>
<c:if test="${requestScope.product==null}">
    <%
        response.sendRedirect(request.getContextPath() + "/goods.jsp");
    %>
</c:if>
<html>
<head>
    <script src="js/utils.js"></script>
    <title>Title</title>
    <style>
        .container {
            background-color: #e2e2e2;
        }

        #pics {
            /*border: black 2px solid;*/
            /*padding: 15px;*/
            background: url("images/banner5.jpg") center;
            /*display: flex;*/
            /*flex-direction: row;*/
            /*justify-content: space-between;*/
        }

        #productForm {
            margin-top: 10px;
        }
    </style>
    <script>
        window.onload = function () {
            $("#productForm").submit(function () {
                if ($("#number").val() <= 0) {
                    alert("Please enter a positive number");
                    return false;
                }
                return true;
            });
        }
        
        function changeActive() {
            $("#descLabel").removeClass('active');
            $('#reviewLabel').addClass("active");
            $('#desc').removeClass('in');
            $('#desc').removeClass('active');
            $('#review').addClass("in");
            $('#review').addClass("active");
        }
        function seeReview() {
            // changeActive();
            searchComment();
        }
        function searchComment() {
            var mid = getParameter("mID");
            $.ajax({
                url:"${pageContext.request.contextPath}/searchCommentServlet",
                type:"POST",
                data:{mid:mid,currentPage:$("#current").html()},
                success:function (data) {
                    console.log(data);
                    var listLength = data.typeList.length;
                    if (listLength===0){
                        return;
                    }
                    $("#current").html(data.currentPage);
                    var begin;
                    var end;
                    if (data.totalPage < 5) {
                        begin = 1;
                        end = data.totalPage;
                    } else {
                        begin = data.currentPage - 2;
                        end = data.currentPage + 2;
                        if (begin < 1) {
                            begin = 1;
                            end = begin + 4;
                        }
                        if (end > data.totalPage) {
                            end = data.totalPage;
                            begin = end - 4;
                        }
                    }
                    var index1 = (data.currentPage-1) * data.sizes ;
                    var index2 = index1 + data.sizes - 1 >= listLength ? listLength - 1 : index1 + data.sizes - 1;
                    var comments = '<tr class="success">\n' +
                        '                                <th><span class="label label-success" style="font-size: medium;">Customer</span></th>\n' +
                        '                                <th><span class="label label-success" style="font-size: medium;">Rate</span></th>\n' +
                        '                                <th><span class="label label-success" style="font-size: medium;">Comment</span></th>\n' +
                        '                                <th><span class="label label-success" style="font-size: medium;">Time</span></th>\n' +
                        '                            </tr>\n';
                    for (var i = index1; i <= index2; i++) {
                        var comment = data.typeList[i];
                        var id=comment.username+i;
                        comments += '<tr>\n' +
                            '                                <td>'+comment.username+'</td>\n' +
                            '                                <td>'+comment.rate+' Star</td>\n' +
                            '                                <td onclick="alert(this.innerHTML)" id="'+id+'">'+comment.words+'</td>\n' +
                            '                                <td>'+comment.leaveTime+'</td>\n' +
                            '                            </tr>\n'
                    }
                    document.getElementById('comments').innerHTML=comments;
                    var prePage = data.currentPage - 1 === 0 ? 1 : data.currentPage - 1;
                    var pages='';
                    if (data.currentPage-1===0){
                        pages+='<li id="prePage">\n';
                    }else{
                        pages+='<li onclick="gotoPage('+prePage+')" id="prePage">\n';
                    }
                    pages += '            <a href="javascript:void(0)" aria-label="Previous">\n' +
                        '               <span aria-hidden="true">&laquo;</span>\n' +
                        '            </a>\n' +
                        '        </li>';
                    for (i = begin; i <= end; i++) {
                        if (i===data.currentPage){
                            pages += '<li class="active"><a href="javascript:void(0)">' + i + '</a></li>\n';
                        }else {
                            pages += '<li onclick="gotoPage('+i+')"><a href="javascript:void(0)">' + i + '</a></li>\n';
                        }
                    }
                    var nextPage = data.currentPage + 1 > data.totalPage ? data.totalPage : data.currentPage + 1;
                    if (data.currentPage+1>data.totalPage){
                        pages += '              <li id="nextPage">\n'
                    }
                    else {
                        pages += '              <li onclick="gotoPage('+nextPage+')" id="nextPage">\n';
                    }
                    pages += '                        <a href="javascript:void(0)" aria-label="Next">\n' +
                        '                            <span aria-hidden="true">&raquo;</span>\n' +
                        '                        </a>\n' +
                        '                    </li>\n';
                    $("#pageBar").html(pages);
                    if (data.currentPage===1){
                        $("#prePage").addClass("disabled");
                    }
                    if (data.currentPage+1>data.totalPage){
                        $("#nextPage").addClass("disabled");
                    }

                },
                dataType:"json"
            });
        }
        var stars=5;
        function rate(num) {
            stars = num;
            var status = $("#rate" + num).prop('checked');
            for (var i = num - 1; i > 0; i--) {
                $("#rate" + i).prop('checked', status);
            }
            for (var i = num + 1; i <= 5; i++) {
                var rate = $("#rate" + i);
                if (rate.prop('checked')) {
                    rate.prop('checked', status);
                }
            }
        }
        function gotoPage(page) {
            $("#current").html(page);
            searchComment();
        }
        
        function comment() {
            var words = $("#comment").val();
            if (words.length<10){
                alert('Please enter at least 10 words!');
                return;
            }
            var mid = getParameter("mID");
            $.ajax({
                url:"${pageContext.request.contextPath}/addCommentServlet",
                type: "POST",
                data: {words:words,rate:stars,mid:mid},
                success:function (data) {
                    if (data.status){
                        searchComment();
                    }
                    alert(data.message);
                },
                error:function(){
                    location.href="${pageContext.request.contextPath}/index.jsp";
                },
                dataType: "json"
            })
        }
    </script>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="panel panel-warning">
            <div class="panel-heading">
                <h1 style="display: inline-block">${requestScope.product.name}</h1>
                <h1 style="display: inline-block"><span class="label label-success">${requestScope.product.brand}</span></h1>
            </div>
        </div>

    </div>

    <div class="row" id="pics" style="text-align: center">
        <c:forEach begin="1" end="${requestScope.product.resourceNum}" var="j">
            <div class="col-sm-6 col-md-4 col-xs-12">
                <div class="thumbnail" style="background: #e2e2e2">
                    <img src="${requestScope.product.location}/p${j}.jpg" alt="...">
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="row">
        <form action="${pageContext.request.contextPath}/addCartServlet" method="post" id="productForm">
            <div class="col-xs-12 col-md-12 col-lg-12" style="display: flex; flex-direction: column;text-align: center">
                <input type="hidden" id="mid" value="${requestScope.product.MID}" name="mid">
                <div class="form-group">
                    <label for="number">
                        <input type="number" id="number" class="form-control" placeholder="Please enter number"
                               name="number">
                    </label>
                </div>
                <div class="form-group"><input type="submit" class="btn btn-primary btn-lg" value="Add to cart"></div>
            </div>

        </form>
    </div>


    <%--    分页组件--%>
    <div class="row" style="text-align: center">

        <div class="panel panel-info">
            <div class="panel-heading">
                <ul id="myTab" class="nav nav-pills nav-justified" style="font-size: medium">
                    <li class="active" id="descLabel"><a href="#desc" data-toggle="tab">Description</a></li>
                    <li id="reviewLabel"><a href="#review" data-toggle="tab" onclick="seeReview()">Reviews</a></li>
                    <li><a href="#info" data-toggle="tab">Information</a></li>
                </ul>
            </div>
        </div>

    </div>


    <div id="myTabContent" class="tab-content">
        <div class="tab-pane fade in active" id="desc">
            <h3 style="text-align: center;">Description</h3>
            <ul class="list-group">
                <li class="list-group-item active">
                    ${requestScope.product.name}
                </li>
                <li class="list-group-item">
                    <span class="label label-success" style="padding:5px;font-size: medium">Category:</span>&nbsp;
                    ${requestScope.product.category}
                </li>
                <li class="list-group-item">
                    <span class="label label-success" style="padding:5px;font-size: medium">Brand:</span>&nbsp;
                    ${requestScope.product.brand}
                </li>
                <li class="list-group-item">
                    <span class="label label-success" style="padding:5px;font-size: medium">Price:</span>&nbsp;
                    ${requestScope.product.price}
                </li>
                <li class="list-group-item">
                    <span class="label label-success" style="padding:5px;font-size: medium">Discount:</span>&nbsp;
                    ${requestScope.product.discount}
                </li>
                <li class="list-group-item">
                    <span class="label label-success" style="padding:5px;font-size: medium">Amount:</span>&nbsp;
                    ${requestScope.product.amount}
                </li>
                <li class="list-group-item">
                    <span class="label label-success" style="padding:5px;font-size: medium">PutUpTime:</span>&nbsp;
                    ${requestScope.product.putUpTime}
                </li>
            </ul>
        </div>

        <div class="tab-pane fade" id="review">
            <h3 style="text-align: center;">Reviews</h3>
            <div class="panel panel-primary">
                <div class="panel-heading">${requestScope.product.name}</div>
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table table-hover table-cell" id="comments">
                            <tr class="success">
                                <th><span class="label label-success" style="font-size: medium;">Customer</span></th>
                                <th><span class="label label-success" style="font-size: medium;">Comment</span></th>
                                <th><span class="label label-success" style="font-size: medium;">Time</span></th>
                            </tr>
                        </table>
                    </div>
                        <div class="input-group" style="margin-bottom: 10px">
                            <span class="input-group-addon">
                                <input type="checkbox" aria-label="..." id="rate1" onclick="rate(1)">
                            </span>
                            <span class="input-group-addon">
                                <input type="checkbox" aria-label="..." id="rate2" onclick="rate(2)">
                            </span><span class="input-group-addon">
                                <input type="checkbox" aria-label="..." id="rate3" onclick="rate(3)">
                            </span>
                            <span class="input-group-addon">
                                <input type="checkbox" aria-label="..." id="rate4" onclick="rate(4)">
                            </span>
                            <span class="input-group-addon">
                                <input type="checkbox" aria-label="..." id="rate5" onclick="rate(5)">
                            </span>

                            <input type="text" class="form-control"
                                   placeholder="Rate us and leave a comment here to let others know!"
                                   aria-describedby="basic-addon2" id="comment">
                            <span class="input-group-btn" id="submit" onclick="comment()">
                                <a class="btn btn-danger">Submit</a>
                            </span>
                        </div>
                </div>
                <span class="hidden" id="current">1</span>
                <div class="row" style="text-align: center">
                    <nav aria-label="Page navigation">
                        <ul class="pagination pagination-lg" id="pageBar">
                            
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

        <div class="tab-pane fade" id="info">
            <h3 style="text-align: center;">Information</h3>
            <%
                String[] split = ((Product) request.getAttribute("product")).getInformation().split("#");
                List<String[]> list = new ArrayList<>();
                for (String s : split) {
                    list.add(s.split(":"));
                }
                pageContext.setAttribute("split", list);
            %>
            <ul class="list-group">
                <li class="list-group-item active">${requestScope.product.name}</li>
                <c:forEach items="${pageContext.getAttribute(\"split\")}" var="item">
                    <li class="list-group-item">
                        <span class="label label-success" style="padding:5px;font-size: medium">${item[0]}:</span>&nbsp;
                            ${item[1]}
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

</div>

</body>
</html>

<%@include file="footer.jsp" %>
