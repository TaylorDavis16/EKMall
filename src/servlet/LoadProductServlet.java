package servlet;

import domain.Product;
import service.interfaces.ProductService;
import service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * This servlet is used to load products from database and put them into list
 */
@WebServlet("/loadProductServlet")
public class LoadProductServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //需要使用spring来保存productList对象
        ProductService service=new ProductServiceImpl();
        //用于index展示的list
        List<Product> indexList1 = service.getIndexProduct1();
        List<Product> indexList2 = service.getIndexProduct2();
        //For product.jsp
        List<Product> productList = service.getProductList();
        Map<Integer,Product> productMap=service.getProductMap();
        if (productList!=null && indexList1!=null && productMap!=null && indexList2!=null){
            HttpSession session = request.getSession();
            session.setAttribute("indexList1",indexList1);
            session.setAttribute("indexList2",indexList2);
            session.setAttribute("productList",productList);
            session.setAttribute("productMap",productMap);
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
        else {
            request.getRequestDispatcher("/error.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
