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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * A servlet for handle event of deleting merchandises in cart
 */
@WebServlet("/deleteCartServlet")
public class DeleteCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user==null){
            request.setAttribute("login_msg","Please log in first!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        List<Cart> carts=new ArrayList<>();
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cartMap");
        String[] mids = request.getParameterValues("mid");
        for (String mid : mids) {
            int intMid=Integer.parseInt(mid);
            carts.add(new Cart(user.getCusID(),intMid,cartMap.get(intMid).getNumber()));
        }
        CartService cartService=new CartServiceImpl();
        int i=cartService.batchDelete(carts);
        if (i!=mids.length){
            session.setAttribute("delete_msg","Delete operation went wrong!");
        }
        else {
            session.setAttribute("delete_msg","Delete operation success!");
        }
        request.getRequestDispatcher("/shoppingCart.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
