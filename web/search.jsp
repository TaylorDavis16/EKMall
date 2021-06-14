<%--
  Created by IntelliJ IDEA.
  User: LITHI
  Date: 2020/7/2
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="navigationBar.jsp"%>

<html>
<head>
    <script src="js/utils.js"></script>
    <title>Search</title>
    <script>
        window.onload=function () {
            searchProduct();
        }
        function searchProduct() {
            var name = getParameter("name");
            if (name===""){
                name=null;
                $("#reflect").html('<span style="color:red;">Please enter a keyword!</span>');
            }
            if (name!==null){
                $.ajax({
                    url:"${pageContext.request.contextPath}/simpleSearchServlet",
                    type: "POST",
                    data:{name:name,currentPage:$("#current").html()},
                    success: function (data) {
                        var listLength = data.typeList.length;
                        if (listLength===0){
                            return;
                        }
                        $("#reflect").html('Product\'s name about <span style="color:red;">'+name+'</span> are under here!');
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
                                '                            <form action="${pageContext.request.contextPath}/addCartServlet" method="post"\n' +
                                '                                  id="productForm"\n' + 'class="form-inline"' +
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
                        $("#exist").html(products);
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
                        window.scrollTo(0,0);
                    },
                    dataType:"json"
                });
            }
        }

        
        function gotoPage(page){
            $("#current").html(page);
            searchProduct();
        }
    </script>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-xs-12 col-md-12 col-sm-12" style="text-align: center">
            
            <div class="panel panel-info">
                <div class="panel-heading" style="font-size: large;font-weight: bold" id="reflect">
                    The product you search is not exist!
                </div>
            </div>
            
        </div>
    </div>

    <div class="row" id="exist">
        
    </div>

    <span class="hidden" id="current">1</span>

    <div class="row" style="text-align: center">
        <nav aria-label="Page navigation">
            <ul class="pagination pagination-lg" id="pageBar">
                
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
<%@include file="propaganda.jsp"%>
<%@include file="footer.jsp"%>
