<%--
  Created by IntelliJ IDEA.
  User: Taylor Davis
  Date: 2020/5/25
  Time: 20:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" errorPage="error.jsp" language="java" %>
<%@include file="navigationBar.jsp"%>
<c:if test="${empty sessionScope.productList || empty sessionScope.indexList1 || empty sessionScope.productMap || empty sessionScope.indexList2}">
    <%
        response.sendRedirect(request.getContextPath()+"/loadProductServlet");
    %>
</c:if>
<html>
    <head>
        <title>Welcome to EK Mall</title>
    </head>
    <script>
        window.onload=function () {
            $("#productForm").submit(function () {
                if ($("#number").val() <= 0) {
                    alert("Please enter a positive number");
                    return false;
                }
                return true;
            });
        }
    </script>
    
    
    
  <body>
  <div class="container-fluid">
      <!-- Wrapper for slides -->
      <div class="page-header">
          <h1>
              <a href="goods.jsp" style="text-decoration: none;font-weight: bold">
                  The Biggest Sale</a>
              <small style="font-weight: bold">Special for today</small>
          </h1>
      </div>
      
      <div id="myCarousel" class="carousel slide" data-ride="carousel" style="border-radius: 50%">
          <!-- Indicators -->
          <ol class="carousel-indicators">
              <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
              <li data-target="#myCarousel" data-slide-to="1"></li>
              <li data-target="#myCarousel" data-slide-to="2"></li>
          </ol>

          
          <div class="carousel-inner"  role="listbox">
              <div class="item active">
                  <a href="goods.jsp">
                      <img class="img-responsive" src="images/banner1.jpg"  alt="..." >
                  </a>
              </div>
              <c:forEach begin="2" end="3" var="i">
                  <div class="item">
                      <a href="goods.jsp">
                          <img class="img-responsive" src="images/banner${i}.jpg" alt="..." >
                      </a>
                  </div>
              </c:forEach>
          </div>

          <!-- Controls -->
          <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
              <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
              <span class="sr-only">Previous</span>
          </a>
          <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
              <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
              <span class="sr-only">Next</span>
          </a>
      </div>

      <div class="alert alert-success" role="alert" style="margin-top: 10px;">
          <span class="label label-success" style="font-size: medium;">The New Arrivals</span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span style="color: black;font-size: medium;font-weight: bold">
                  Here are some new arrival Keyboards!!!!
          </span>
      </div>
      <div class="row">
          <c:forEach items="${sessionScope.indexList1}" var="i">
          <div class="col-sm-3 col-md-3 img-responsive" style="display: inline;">
              <div class="thumbnail" style="background: #e2e2e2">
                  <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${i.MID}">
                      <img src="${i.location}/p1.jpg" alt="..." style="width: 100%;">
                  </a>
                  <div class="caption">
                      <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${i.MID}" style="text-decoration: none">
                          <h3 style="height: 92px;">${i.name}</h3>
                      </a>
                      
                      <div style="font-weight: bold;"><span style="color: #ff6126">${i.category}</span><br>Brand: ${i.brand}</div>
                      
                      <form action="${pageContext.request.contextPath}/addCartServlet" method="post" id="productForm">
                          <input type="hidden" id="mid" value="${i.MID}" name="mid">
                          <div class="form-group">
                              <label for="number">
                                  <input type="number" id="number" class="form-control" placeholder="Number" name="number">
                              </label>
                              <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${i.MID}" class="btn btn-success" role="button">View</a>
                              <input type="submit" class="btn btn-primary" value="+">
                              <div class="alert alert-success" role="alert">
                                  Price:&nbsp;${i.price}&nbsp;Sold: ${i.sales}
                              </div>
                          </div>
                      </form>
                  </div>
              </div>
          </div>
          </c:forEach>
      </div>

      <div class="alert alert-warning" role="alert" style="margin-top: 10px;">
          <span class="label label-warning" style="font-size: medium;">Sell the best</span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <span style="color: black;font-size: medium;font-weight: bold">
                  Here are some most popular keyboards!!!!
          </span>
      </div>
      <div class="row">
          <c:forEach items="${sessionScope.indexList2}" var="i">
              <div class="col-sm-3 col-md-3 img-responsive" style="display: inline;">
                  <div class="thumbnail" style="background: #e2e2e2">
                      <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${i.MID}">
                          <img src="${i.location}/p1.jpg" alt="..." style="width: 100%;">
                      </a>
                      <div class="caption">
                          <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${i.MID}" style="text-decoration: none">
                              <h3 style="height: 92px;">${i.name}</h3>
                          </a>

                          <div style="font-weight: bold;"><span style="color: #ff6126">${i.category}</span><br>Brand: ${i.brand}</div>

                          <form action="${pageContext.request.contextPath}/addCartServlet" method="post" id="productForm">
                              <input type="hidden" id="mid" value="${i.MID}" name="mid">
                              <div class="form-group">
                                  <label for="number">
                                      <input type="number" id="number" class="form-control" placeholder="Number" name="number">
                                  </label>
                                  <a href="${pageContext.request.contextPath}/productInfoServlet?mID=${i.MID}" class="btn btn-success" role="button">View</a>
                                  <input type="submit" class="btn btn-primary" value="+">
                                  <div class="alert alert-success" role="alert">
                                      Price:&nbsp;${i.price}&nbsp;Sold: ${i.sales}
                                  </div>
                              </div>
                          </form>
                      </div>
                  </div>
              </div>
          </c:forEach>
      </div>
      
  </div>
  </body>
    
</html>
<%@include file="propaganda.jsp"%>
<%@include file="footer.jsp"%>
