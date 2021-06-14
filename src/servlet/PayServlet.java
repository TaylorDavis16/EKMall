package servlet;

import domain.Cart;
import domain.Order;
import domain.Product;
import domain.User;
import service.impl.CartServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ProductServiceImpl;
import service.impl.UserServiceImpl;
import service.interfaces.CartService;
import service.interfaces.OrderService;
import service.interfaces.ProductService;
import service.interfaces.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * This servlet is used to handle user's topup request
 */
@WebServlet("/payServlet")
@SuppressWarnings("all")
public class PayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        //If address is not set
        if (user.getAddress()==null){
            request.setAttribute("isRefresh",new Object());
            session.setAttribute("pay_msg",
                    "Failed! Please set your address first!");
            request.getRequestDispatcher("/shoppingCart.jsp").forward(request,response);
            return;
        }
        String[] mids = request.getParameterValues("mid");
        ProductService productService = new ProductServiceImpl();
        Map<Integer, Product> productMap = productService.getProductMap();
        session.setAttribute("productMap",productMap);
        Map<Integer,Cart> cartMap = (Map<Integer, Cart>) session.getAttribute("cartMap");
        double total=0;
        List<Object[]> list=new ArrayList<>();
        List<Cart> carts=new ArrayList<>();
        List<Object[]> productAlter = new ArrayList<>();
        boolean enough=true;
        boolean productEnough=true;
        int items = 0;
        for (String mid : mids) {
            int intMid = Integer.parseInt(mid);
            Cart cart = cartMap.get(intMid);
            Product product = productMap.get(intMid);
            int number = cart.getNumber();
            int amount = product.getAmount();
            if (number> amount){
                session.setAttribute("pay_msg","Product "+product.getName()+" is already not enough! There is only "+amount+" left!");
                //商品不足未作改变，无需刷新
                request.setAttribute("isRefresh",new Object());
                productEnough=false;
                break;
            }
            items+=number;
            double payment = product.getPrice()*number;
            total+=payment;
            if (total>user.getBalance()){
                session.setAttribute("pay_msg","Your balance is not enough! Please try to top up!");
                //余额不足未作改变，无需刷新
                request.setAttribute("isRefresh",new Object());
                enough=false;
                break;
            }
            Object[] objects=new Object[11];
            objects[0]=null;
            objects[1]=number;
            objects[2]=payment;
            objects[3]=user.getCusID();
            objects[4]=product.getMerchant_merID();
            objects[5]=mid;
            objects[6]=new Date();
            objects[7]=1;
            objects[8]=user.getAddress();
            objects[9]="Not yet shipped";
            objects[10]="Normal";
            list.add(objects);
            productAlter.add(new Object[]{number,number,mid});
            carts.add(new Cart(user.getCusID(),intMid,number));
        }
        if(productEnough){
            if (enough){
                OrderService orderService=new OrderServiceImpl();
                int i=orderService.writeRecords(list);
                CartService cartService=new CartServiceImpl();
                int j=cartService.batchDelete(carts);
                UserService userService=new UserServiceImpl();
                int k = userService.topUp(user.getCusID(), -total);
                user.setBalance(user.getBalance()-total);
                //商品数目减少的操作
                int l = productService.update(productAlter);
                System.out.println("l="+mids.length);
                session.setAttribute("loginUser",user);
                if (i!=mids.length || j!=mids.length || k!=1 || l!=mids.length){
                    session.setAttribute("pay_msg","Success, but it seems something went wrong!");
                    List<Order> orderList = orderService.getOrderList(user.getCusID());
                    session.setAttribute("orderList",orderList);
                }
                else{
                    session.setAttribute("pay_msg",
                            "Success!"+"You just bought "+items+" items, and which took "+total+"!");
                }
            }
        }
        request.getRequestDispatcher("/shoppingCart.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
