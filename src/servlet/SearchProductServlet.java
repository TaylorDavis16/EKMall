package servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Product;
import domain.configs.PageBean;
import domain.configs.ProductConfig;
import org.apache.commons.beanutils.BeanUtils;
import service.interfaces.ProductService;
import service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * This servlet is used to load all product from database and put them into list, for handling search request.
 */
@WebServlet("/searchProductServlet")
public class SearchProductServlet extends HttpServlet {
    private ProductService productService = new ProductServiceImpl();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        //Obtain the new config
        Map<String, String[]> configMap = request.getParameterMap();
        ProductConfig newConfig = new ProductConfig();
        try {
            BeanUtils.populate(newConfig, configMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //Obtain the previous pageBean of goods.jsp
        PageBean<Product, ProductConfig> goodsPageBean = (PageBean<Product, ProductConfig>) session.getAttribute("goodsPageBean");
        //The first time access goods.jsp directly
        if (goodsPageBean == null) {
            System.out.println("a");
            goodsPageBean = newPageBean(null,newConfig);
        } else if (!newConfig.equals(goodsPageBean.getConfig())) {//User change searching parameters
            System.out.println("b");
            goodsPageBean = newPageBean(goodsPageBean,newConfig);
        }
        //no matter what, if config change, we can overlook what page it is, because it will go to page 1 anyway.
        //else user just change page, that is all, in this case we have consider pages
        //And only currentPage will be changed
        else {
            String currentPage = request.getParameter("currentPage");
            goodsPageBean.setCurrentPage(Integer.parseInt(currentPage));
        }
        session.setAttribute("goodsPageBean", goodsPageBean);
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),goodsPageBean);
    }
    
    private PageBean<Product,ProductConfig> newPageBean(PageBean<Product,ProductConfig> goodsPageBean,ProductConfig config){
        if (goodsPageBean==null){
            goodsPageBean = new PageBean<>();
        }
        List<Product> certainProductList = productService.getProductsBySearching(config);
        goodsPageBean.setConfig(config);
        goodsPageBean.setCurrentPage(1);
        SimpleSearchServlet.fillPageBean(goodsPageBean, certainProductList);
        return goodsPageBean;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
