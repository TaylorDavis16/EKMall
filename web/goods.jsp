<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/2
  Time: 11:34
  To change this template use File | Settings | File Templates.
  这是一个搜素展示页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp" %>
<html>
<head>
    <style>
        li a:hover {
            cursor: pointer;
        }
    </style>
    <script type="text/javascript">
        window.onload = function () {
            searchProduct();

            $("#search").click(function () {//Search button
                // var discount = document.getElementById("discount").value;
                if (isNumber($("#discount").val())) {
                    searchProduct();
                }
            });
            changeLabel("children", "category", "Children");
            changeLabel("adult", "category", "Adult");
            changeLabel("accessory", "category", "Accessory");
            changeLabel("resetCg", "category", "All");
            changeLabel("lToh", "sortMethod", "Low to high");
            changeLabel("hTol", "sortMethod", "High to low");
            changeLabel("popular", "sortMethod", "Popularity");
            changeLabel("time1", "sortMethod", "Time: Early");
            changeLabel("time2", "sortMethod", "Time: Latest");
            changeLabel("resetSm", "sortMethod", "Default");

            $(".productForm").submit(function () {
                alert("aaaa");
                if (isNumber($("#number").val())) {
                    alert("Please enter a positive number");
                    return false;
                }
                return true;
            });
            $("#resetAll").click(function () {
                var searchForm = document.getElementById("searchForm");
                searchForm.action = "${pageContext.request.contextPath}/clearProductConfigServlet";
                searchForm.submit();
            })
        }

        function changeLabel(tagId, labelId, value) {
            $("#" + tagId).click(function () {
                $("#" + labelId).html(value)
            })
        }

        function isNumber(val) {
            var regPos = /^\d+(\.\d+)?$/; //非负浮点数
            if (regPos.test(val)) {
                return true;
            } else {
                alert("Please enter a positive value:(range 0~1.0)");
                return false;
            }
        }

        function searchProduct() {
            $.ajax({
                url: "${pageContext.request.contextPath}/searchProductServlet",
                type: "POST",
                data: {
                    name: $("#name").val(),
                    brand: $("#brand").val(),
                    discount: $("#discount").val(),
                    category: $("#category").html(),
                    sortMethod: $("#sortMethod").html(),
                    currentPage: $("#currentPage").val()//the next time the page will go to 
                },
                success: function (data) {
                    console.log(data);
                    $("#name").val(data.config.name);
                    $("#brand").val(data.config.brand);
                    $("#discount").val(data.config.discount);
                    $("#category").html(data.config.category);
                    $("#sortMethod").html(data.config.sortMethod);
                    $("#currentPage").val(data.currentPage);
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
                    var index2 = index1 + data.sizes - 1 >= data.typeList.length ? data.typeList.length - 1 : index1 + data.sizes - 1;
                    var products = '';
                    console.log("index1="+index1+", index2="+index2)
                    for (var i = index1; i <= index2; i++) {
                        var product = data.typeList[i];
                        products += '<div class="col-sm-4 col-md-3 col-xs-6">\n' +
                            '                    <div class="thumbnail" style="background: #e2e2e2">\n' +
                            '                        <a href="${pageContext.request.contextPath}/productInfoServlet?mID=' + product.mid + '">\n' +
                            '                            <img src="${pageContext.request.contextPath}/' + product.location + '/p1.jpg" alt="..."\n' +
                            '                                 class="img-circle" style="width: 100%;height: 100%">\n' +
                            '                        </a>\n' +
                            '                        <div class="caption">\n' +
                            '                            <a href="${pageContext.request.contextPath}/productInfoServlet?mID=' + product.mid + '"\n' +
                            '                               style="text-decoration: none">\n' +
                            '                                <h3 style="height: 92px;">' + product.name + '</h3>\n' +
                            '                            </a>\n' +
                            '                            <div style="font-weight: bold;"><span style="color: #ff6126">' + product.category + ':</span><br>Brand: ' + product.brand + '</div>\n' +
                            '                            <form action="${pageContext.request.contextPath}/addCartServlet" method="post"\n' + 'class="productForm form-inline"' +
                            '                                  style="margin-top: 3px">\n' +
                            '                                <input type="hidden" id="mid" value="' + product.mid + '" name="mid">\n' +
                            '                                <div class="form-group">\n' +
                            '                                    <label for="number">\n' +
                            '                                        <input type="number" id="number" class="form-control" placeholder="Number"\n' +
                            '                                               name="number">\n' +
                            '                                    </label>\n' +
                            '                            <a href="${pageContext.request.contextPath}/productInfoServlet?mID=' + product.mid + '"\n' +
                            '                               class="btn btn-success" role="button">View</a>\n' +
                            '                                    <input type="submit" class="btn btn-primary" value="+">\n' +
                            '                                    <div class="alert alert-success" role="alert">\n' +
                            '                                        Price:&nbsp;' + product.price + '&nbsp;Sold:' + product.sales + '\n' +
                            '                                    </div>\n' +
                            '                                </div>\n' +
                            '                            </form>\n' +
                            '                        </div>\n' +
                            '                    </div>\n' +
                            '                </div>\n';
                    }
                    $("#productRow").html(products);
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
                            pages += '<li class="active" id="current"><a href="javascript:void(0)">' + i + '</a></li>\n';
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
                    window.scrollTo(0,0);
                },
                dataType: "json"
            });
        }
        
        function gotoPage(page){
            $("#currentPage").val(page);
            searchProduct();
        }
        

    </script>
    <title>Goods</title>
</head>

<body>
<div class="container-fluid">
    <div class="panel panel-primary">

        <%--Searching form--%>
        <div class="panel-heading">
            <form class="form-inline" method="post" id="searchForm"
                  action="${pageContext.request.contextPath}/searchProductServlet">
                <div class="row" style="text-align: center">
                    <div class="col-sm-4 col-md-4 col-xs-12">
                        <div class="form-group">
                            <label for="name">Name:
                                <input type="text" name="name" class="form-control" id="name">
                            </label>
                        </div>
                    </div>

                    <div class="col-sm-4 col-md-4 col-xs-12">
                        <div class="form-group">
                            <label for="brand">Brand:
                                <input type="text" name="brand" class="form-control" id="brand">
                            </label>
                        </div>
                    </div>

                    <div class="col-sm-4 col-md-4 col-xs-12">
                        <div class="form-group">
                            <label for="discount">Discount Under:
                                <input type="text" name="discount" class="form-control" id="discount" value="1.0">
                            </label>
                        </div>
                    </div>

                </div>

                <div class="row" style="text-align: center">
                    <div class="col-sm-3 col-md-3 col-xs-6">
                        <div class="btn-group">
                            <button id="cg" type="button" class="btn btn-success dropdown-toggle open"
                                    data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="true">
                                Category <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a id="children">Children</a></li>
                                <li><a id="adult">Adult</a></li>
                                <li><a id="accessory">Accessory</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a id="resetCg">Reset</a></li>
                            </ul>
                        </div>

                        <p style="font-size: x-large;">
                            <span class="label label-default"
                                  id="category">All</span>
                        </p>
                    </div>

                    <div class="col-sm-3 col-md-3 col-xs-6">
                        <div class="btn-group">
                            <button type="button" class="btn btn-success dropdown-toggle open"
                                    data-toggle="dropdown"
                                    aria-haspopup="true" aria-expanded="true">
                                Sort <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a id="lToh">Price: low to high</a></li>
                                <li><a id="hTol">Price: high to low</a></li>
                                <li><a id="popular">Popularity</a></li>
                                <li><a id="time1">Put up time: Early</a></li>
                                <li><a id="time2">Put up time: Latest</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a id="resetSm">Reset</a></li>
                            </ul>
                        </div>
                        <p style="font-size: x-large;">
                                <span class="label label-default"
                                      id="sortMethod">Default</span>
                        </p>
                    </div>

                    <div class="col-sm-3 col-md-3 col-xs-6">

                        <div class="form-group">
                            <a class="btn btn-danger" style="text-decoration: none;" id="search">Search</a>
                        </div>

                    </div>

                    <div class="col-sm-3 col-md-3 col-xs-6">
                        <div class="form-group">
                            <a class="btn btn-danger" style="text-decoration: none;" id="resetAll">Reset</a>
                        </div>
                    </div>
                </div>
                <input class="hidden" value="1" id="currentPage">
            </form>
        </div>

        <%--Show products if row size = -1 then it means there is no result--%>
        <div class="row" id="productRow">

        </div>
        <div class="row" style="text-align: center">
            <nav aria-label="Page navigation">
                <ul class="pagination pagination-lg" id="pageBar">
                    
                </ul>
            </nav>
        </div>
    </div>
    
</div>
</body>
</html>
<%@include file="footer.jsp" %>