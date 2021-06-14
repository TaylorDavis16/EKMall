package servlet;

import domain.Order;
import domain.configs.OrderConfig;
import domain.User;
import service.interfaces.OrderService;
import service.impl.OrderServiceImpl;
import service.interfaces.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * A servlet for handle event of orders being canceled
 */
@WebServlet("/cancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user==null){
            request.setAttribute("login_msg","Please log in first!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        String[] mids = request.getParameterValues("mid");
        OrderService orderService=new OrderServiceImpl();
        List<Order> orderList = orderService.getSearchList((OrderConfig) session.getAttribute("orderConfig"));
        Set<String> refundableSet = new HashSet<>();
        for (Order order : orderList) {
            if (order.getRefundable()==1){
                refundableSet.add(order.getOrderId()+"");
            }
        }
        double refund, refunds=0;
        //first round: check refundable
        for (String mid : mids) {
            if (!refundableSet.contains(mid)){
                request.setAttribute("cancel_Msg","You cannot cancel orders that have been refunded!");
                request.setAttribute("gotoOrder",new Object());
                request.getRequestDispatcher("/info.jsp").forward(request,response);
                return;
            }
        }
        //second round: refund (update order,product,user balance)
        for (String mid : mids) {
            int id = Integer.parseInt(mid);
            refund = orderService.refund(id);//order
            refunds += refund;
        }
        UserService userService=new UserServiceImpl();
        userService.topUp(user.getCusID(),refunds);
        user.setBalance(user.getBalance()+refunds);
        
        session.setAttribute("loginUser",user);

        //refresh orderList
        orderList = orderService.getSearchList((OrderConfig) session.getAttribute("orderConfig"));
        session.setAttribute("orderList",orderList);
        request.setAttribute("cancel_Msg","Cancel successfully!");
        request.setAttribute("gotoOrder",new Object());
        request.getRequestDispatcher("/info.jsp").forward(request,response);
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
