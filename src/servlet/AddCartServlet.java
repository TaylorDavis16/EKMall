package servlet;

import domain.Cart;
import domain.User;
import service.interfaces.CartService;
import service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A servlet to handle event of merchandise adding
 */
@WebServlet("/addCartServlet")
public class AddCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Object user = session.getAttribute("loginUser");
        session.removeAttribute("add_msg");
        //检查是否登录
        if (user==null){
            request.setAttribute("login_msg","Please login first!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
        
        else {
            int cid = ((User)user).getCusID();
            String numberStr = request.getParameter("number");
            if ("".equals(numberStr)){//不输入为"",则默认为1
                numberStr="1";
            }
            int number = Integer.parseInt(numberStr);
            if (number<=0){
                session.setAttribute("add_msg","Failed! Please enter a positive number");
                request.getRequestDispatcher("/shoppingCart.jsp").forward(request,response);
                return;
            }
            int mid = Integer.parseInt(request.getParameter("mid"));
            CartService service=new CartServiceImpl();
            Cart cart=new Cart(cid,mid,number);
            //Add directly into database
            int add = service.add(cart);
            //Info for adding operation, if add==0, then fail
            session.setAttribute("add_msg",mid+": Add "+number+(add==0 ? " ×": " √"));
            request.getRequestDispatcher("/shoppingCart.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
