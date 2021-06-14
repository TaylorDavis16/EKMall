package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.impl.UserServiceImpl;
import service.interfaces.UserService;
import utils.MailUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@WebServlet("/findEmailServlet")
public class FindEmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String referer = request.getHeader("referer");
        if (!referer.contains("login.jsp")){//not from login.jsp
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else {
            HttpSession session = request.getSession();
            String email = request.getParameter("email");
            UserService userService = new UserServiceImpl();
            String username = userService.findIfEmailExist(email);
            Map<String,Object> map = new HashMap<String,Object>();
            if (username!=null) {
                Random random = new Random();
                String character= "Aa0BbCc1DdEe2FfGg3HhIi4JjKk5LlMmNnOoPpQqRrSs6TtUu7VvWw8XxYy9Zz";
                StringBuilder checkCode =new StringBuilder();
                for (int i=1;i<=8;i++) {
                    char c = character.charAt(random.nextInt(character.length()));
                    checkCode.append(c);
                }
                String text = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hi, " + username +
                        " : the check code used for reset your password is <a>" + checkCode +
                        "</a>! Please enter to reset your password!<br><hr/>"+
                        "Do not reply this email!<br>---------------------------------------Your dear dongdong";
                boolean result = MailUtil.sendEmail(email,"EKMall",username, "Reset Password",text);
                map.put("resetMsg",result ? "Check code is already been sent! Please enter to reset your password ASAP!":
                        "The email address is not exist! Please check! How you sign up successfully before? It is a mystery!");
                map.put("checkCode",checkCode.toString());
                String millis = System.currentTimeMillis()+"";
                map.put("secondAccess",millis);
                map.put("status",true);
                session.setAttribute("secondAccess",millis);
            }else {
                map.put("resetMsg","The email address is not exist!");
                map.put("status",false);
            }
            response.setContentType("application/json;charset=utf-8");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(),map);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
