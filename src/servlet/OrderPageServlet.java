package servlet;

import domain.Order;
import domain.configs.OrderConfig;
import domain.configs.PageBean;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.OrderServiceImpl;
import service.interfaces.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
//still not use for the change page still not realize
@WebServlet("/orderPageServlet")
public class OrderPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        PageBean<Order,OrderConfig> newOrderPage = new PageBean<>();
        try {
            BeanUtils.populate(newOrderPage,request.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        OrderConfig orderConfig = (OrderConfig) session.getAttribute("orderConfig");
        newOrderPage.setConfig(orderConfig);
        request.setAttribute("orderPage",newOrderPage);
        session.setAttribute("lastOrderPage",newOrderPage);
        request.getRequestDispatcher("/loadOrdersServlet").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
