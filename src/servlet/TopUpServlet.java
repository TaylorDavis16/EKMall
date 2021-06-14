package servlet;

import domain.User;
import service.interfaces.UserService;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * This servlet is used to handle request from users when they are trying to top up balance
 */
@WebServlet("/topUpServlet")
public class TopUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String cid = request.getParameter("cid");
        String amount = request.getParameter("amount");
        UserService userService=new UserServiceImpl();
        int parseAmount = Integer.parseInt(amount);
        int topUp = userService.topUp(Integer.parseInt(cid), parseAmount);
        if (topUp==1){
            request.setAttribute("topUp_msg","Top up "+amount+" successfully!");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("loginUser");
            user.setBalance(user.getBalance()+parseAmount);
            session.setAttribute("loginUser",user);
        }else {
            request.setAttribute("topUp_msg","Fail to top up! The server is busy!");
        }
        request.getRequestDispatcher("/topUp.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
