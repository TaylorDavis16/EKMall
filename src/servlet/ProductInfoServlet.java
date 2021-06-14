package servlet;

import domain.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
/**
 * This servlet is used to load a specific product from product in list and save it as a request attribute
 */
@WebServlet("/productInfoServlet")
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int mID;
        try {
            mID = Integer.parseInt(request.getParameter("mID"));
        }catch (Exception e){
            e.printStackTrace();
            request.getRequestDispatcher("/error.jsp").forward(request,response);
            return;
        }
        List<Product> productList = (List<Product>) request.getSession().getAttribute("productList");
        if (mID != 0 && productList != null) {
            for (Product product : productList) {
                if (product.getMID() == mID) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("/product.jsp").forward(request, response);
                    return;
                }
            }
        }
        System.out.println("Cannot find the product! Check your cache!");
        response.sendRedirect(request.getContextPath()+"/index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
