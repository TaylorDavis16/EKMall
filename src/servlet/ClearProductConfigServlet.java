package servlet;

import domain.Product;
import domain.configs.ProductConfig;
import domain.User;
import service.interfaces.ProductService;
import service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/clearProductConfigServlet")
public class ClearProductConfigServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        session.removeAttribute("goodsPageBean");
        response.sendRedirect(request.getContextPath()+"/goods.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

//    private int resultSize(int size) {
//        return (size % 4 == 0 ? size / 4 : size / 4 + 1) - 1;
//    }
}
