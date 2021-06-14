package servlet;

import service.impl.UserServiceImpl;
import service.interfaces.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/resetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object access = session.getAttribute("secondAccess");//In the session
        if (access==null){//First time
            access="anything";
        }
        String secondAccess = request.getParameter("secondAccess");//from page, first time is null
        String email = request.getParameter("email");
        UserService userService = new UserServiceImpl();
        String result="{\"resetMsg\":\"Please access from login.jsp!\"}";
        if (access.equals(secondAccess)){//The second time come here will take a success json
            int i = userService.resetPassword(email);
            result = "{\"resetMsg\":"+(i==1 ? "\"Your password is already been reset to 2000416! " +
                    "Please login and reset ASAP!\"": "\"Something went wrong in the server!\"")+"}";
        }
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(result);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
