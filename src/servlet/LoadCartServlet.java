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
import java.util.List;
import java.util.Map;
/**
 * A servlet for loading an individual carts, and save them in list and map
 */
@WebServlet("/loadCartServlet")
public class LoadCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user==null){
            request.setAttribute("login_msg","Please login first!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        CartService service=new CartServiceImpl();
        List<Cart> cartList = service.getCartList(user.getCusID());
        Map<Integer, Cart> cartMap = service.getCartMap(user.getCusID());
        session.setAttribute("cartMap",cartMap);
        session.setAttribute("cartList",cartList);
        //Refresh cartList and cartmap, only valid in one transfer
        request.setAttribute("isRefresh",new Object());
        request.getRequestDispatcher("/shoppingCart.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
