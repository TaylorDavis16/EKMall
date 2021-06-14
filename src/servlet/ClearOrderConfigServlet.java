package servlet;

import domain.*;
import domain.configs.OrderConfig;
import service.interfaces.OrderService;
import service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/clearOrderConfigServlet")
public class ClearOrderConfigServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user==null){
            request.setAttribute("login_msg","Please log in first!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        OrderConfig config = new OrderConfig();
        config.setUid(user.getCusID());
        session.setAttribute("orderConfig",config);
        OrderService service = new OrderServiceImpl();
        //reset the config and List
        List<Order> orderList = service.getSearchList(config);
        session.setAttribute("orderList",orderList);
        request.setAttribute("gotoOrder",new Object());
        response.sendRedirect(request.getContextPath()+"/info.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
