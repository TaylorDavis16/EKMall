package servlet;

import domain.Cart;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.interfaces.CartService;
import service.impl.CartServiceImpl;
import service.impl.UserServiceImpl;
import utils.Md5Util;

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
 * This servlet is used to handle request from users when they are trying to log in
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUser")!=null){
            request.setAttribute("change_msg","You already login!");
            request.getRequestDispatcher("/info.jsp").forward(request,response);
            return;
        }
        String code=request.getParameter("code");
        
        String codeFromServer = (String) session.getAttribute("code");
        session.removeAttribute("code");

        if (!code.equalsIgnoreCase(codeFromServer)){
            request.setAttribute("login_msg","Check code incorrect!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }

        Map<String,String[]> map=request.getParameterMap();
        //封装User对象
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //Using Md5 to encode password!
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        UserServiceImpl service=new UserServiceImpl();
        User loginUser = service.login(user);
        System.out.println(loginUser);
        if (loginUser!=null){
            //Success, save user into session
            session.setAttribute("loginUser",loginUser);
            CartService cartService=new CartServiceImpl();
            List<Cart> cartList = cartService.getCartList(loginUser.getCusID());
            session.setAttribute("cartList",cartList);
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }
        else {
            request.setAttribute("login_msg","Username or password wrong！");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
