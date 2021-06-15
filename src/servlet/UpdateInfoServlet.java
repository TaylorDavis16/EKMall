package servlet;

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
 * This servlet is used to handle the request when user update their information and top up money
 */
@WebServlet("/updateInfoServlet")
public class UpdateInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        User user=new User();
        try {
            BeanUtils.populate(user,request.getParameterMap());
            user.setPassword(Md5Util.encodeByMd5(user.getPassword()));//将新密码加密Using Md5 to encode password!
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("session:"+loginUser);
        System.out.println(user);
        if (user.equals(loginUser)){//Update information only when things changed!
            request.setAttribute("change_msg","Nothing has changed!");
            request.getRequestDispatcher("/info.jsp").forward(request,response);
            return;
        }
        System.out.println(user);
        UserService service = new UserServiceImpl();
        int update = service.update(user);
        if (update==1){//因为balance没法直接改，需要单独设置
            user.setBalance(loginUser.getBalance());
            session.setAttribute("loginUser",user);
            request.setAttribute("change_msg","Info update successfully!");
        }
        else {
            request.setAttribute("change_msg","Username "+user.getUsername()+" is already existed!");
        }
        request.getRequestDispatcher("/info.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
