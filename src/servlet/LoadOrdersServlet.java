package servlet;
import domain.Order;
import domain.configs.OrderConfig;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.interfaces.OrderService;
import service.impl.OrderServiceImpl;

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
 * This servlet is used to load orders from database and put them into list
 */
@WebServlet("/loadOrdersServlet")
public class LoadOrdersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user==null){
            request.setAttribute("login_msg","Please log in first!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        OrderConfig newConfig = new OrderConfig();
        newConfig.setUid(user.getCusID());
        Map<String, String[]> orderConfigMap = request.getParameterMap();
        try {
            BeanUtils.populate(newConfig, orderConfigMap);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Object preConfig = session.getAttribute("orderConfig");
//        System.out.println("newConfig:" + newConfig);
//        System.out.println("preConfig:" + preConfig);
        if (!newConfig.equals(preConfig)){//user change its search parameters, or user log in the first time
            OrderService service=new OrderServiceImpl();
            List<Order> orderList = service.getSearchList(newConfig);
            session.setAttribute("orderList",orderList);
            session.setAttribute("orderConfig",newConfig);
        }
        if (preConfig!=null){//not the first time, user are searching(orderList is reset anyway)
            request.setAttribute("gotoOrder",new Object());
        }
        request.getRequestDispatcher("/info.jsp").forward(request,response);
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
