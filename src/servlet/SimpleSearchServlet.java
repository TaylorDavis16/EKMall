package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Product;
import domain.configs.PageBean;
import domain.configs.ProductConfig;
import service.impl.ProductServiceImpl;
import service.interfaces.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/simpleSearchServlet")
public class SimpleSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        ProductService service = new ProductServiceImpl();
        String name = request.getParameter("name");
        HttpSession session = request.getSession();
        Object keyword = session.getAttribute("keyword");
        String currentPage = request.getParameter("currentPage");
        PageBean<Product, ProductConfig> searchPageBean = new PageBean<>();
        if (keyword==null || !keyword.equals(name)){//Keyword changed!
            System.out.println("Keyword changed! "+name);
            session.setAttribute("keyword",name);
            List<Product> searchProducts = service.getProductsByName(name);
            fillPageBean(searchPageBean, searchProducts);
        }else {//No change!
            System.out.println("No change!");
            searchPageBean = (PageBean<Product, ProductConfig>) session.getAttribute("searchPageBean");
            searchPageBean.setCurrentPage(Integer.parseInt(currentPage));
        }
        session.setAttribute("searchPageBean",searchPageBean);
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),searchPageBean);
    }

    static void fillPageBean(PageBean searchPageBean, List searchProducts) {
        searchPageBean.setTypeList(searchProducts);
        int totalCount = searchProducts.size();
        searchPageBean.setTotalCount(totalCount);
        int sizes = searchPageBean.getSizes();
        int totalPage = totalCount % sizes == 0 ? totalCount / sizes : totalCount / sizes + 1;
        searchPageBean.setTotalPage(totalPage);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
