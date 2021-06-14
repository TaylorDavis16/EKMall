package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.impl.CommentDaoImpl;
import dao.interfaces.CommentDao;
import domain.Comment;
import domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/addCommentServlet")
public class AddCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        Map<String,Object> data = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper =new ObjectMapper();
        if (user==null){
            data.put("status",false);
            data.put("message","You does not entitle to comment because you do not login!");
            mapper.writeValue(response.getOutputStream(),data);
            return;
        }
        String mid = request.getParameter("mid");
        CommentDao commentDao = new CommentDaoImpl();
        boolean validate;
        try{
            validate = commentDao.validate(user.getCusID(), Integer.parseInt(mid));
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Access illegally!");
            response.sendRedirect(request.getContextPath()+"/index.jsp");
            return;
        }
        
        if (validate){
            Comment comment = new Comment();
            Map<String,String[]> map=request.getParameterMap();
            try {
                BeanUtils.populate(comment,map);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            comment.setCid(user.getCusID());
            commentDao.comment(comment);
            data.put("status",true);
            data.put("message","Comment successfully!");
            session.removeAttribute("commentPageBean");//remove comment session so next time new comment can be found
        }else {
            data.put("status",false);
            data.put("message","You does not entitle to comment because you did not buy this product before!");
        }
        mapper.writeValue(response.getOutputStream(),data);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
