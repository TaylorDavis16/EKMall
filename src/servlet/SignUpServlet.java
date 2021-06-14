package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.interfaces.UserService;
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
/**
 * This servlet is used to handle request from users when they are trying to register
 */
@WebServlet("/signUpServlet")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //封装User对象
        User user=new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (user.getUsername()==null){//直接访问servlet
            response.sendRedirect(request.getContextPath()+"/signUp.jsp");
            return;
        }
        //Using Md5 to encode password!
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        UserService service = new UserServiceImpl();
        int i = service.signUp(user);
        System.out.println("Status: "+i);
        //Needed to be improved
        String result = "{\"result\":"+i+"}";
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result);
    }
}
