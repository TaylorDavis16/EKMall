package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.impl.CommentDaoImpl;
import dao.interfaces.CommentDao;
import domain.Comment;
import domain.configs.PageBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/searchCommentServlet")
public class SearchCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String strMid = request.getParameter("mid");
        int mid;
        try {
            mid = Integer.parseInt(strMid);
        }catch (Exception e){
            e.printStackTrace();
            request.getRequestDispatcher("/error.jsp").forward(request,response);
            return;
        }
        HttpSession session = request.getSession();
        PageBean<Comment,Integer> commentPageBean = (PageBean<Comment, Integer>) session.getAttribute("commentPageBean");
        if (commentPageBean==null || mid!=commentPageBean.getConfig()){
            commentPageBean = new PageBean<>();
            commentPageBean.setConfig(mid);//set product id
            CommentDao commentDao = new CommentDaoImpl();
            List<Comment> all = commentDao.getAll(mid);
            commentPageBean.setSizes(5);
            SimpleSearchServlet.fillPageBean(commentPageBean,all);
        }else {
            System.out.println("Product is not change!");
            commentPageBean.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
        }
        session.setAttribute("commentPageBean",commentPageBean);
        ObjectMapper mapper =new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),commentPageBean);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
