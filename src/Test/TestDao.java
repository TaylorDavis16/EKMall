package Test;

import dao.impl.CommentDaoImpl;
import dao.impl.OrderDaoImpl;
import dao.impl.ProductDaoImpl;
import dao.impl.UserDaoImpl;
import dao.interfaces.CommentDao;
import dao.interfaces.OrderDao;
import dao.interfaces.ProductDao;
import dao.interfaces.UserDao;
import domain.configs.OrderConfig;
import domain.Product;
import domain.configs.ProductConfig;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;
import utils.MailUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestDao {
    UserDao userDao = new UserDaoImpl();
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    OrderDao orderDao = new OrderDaoImpl();
    ProductDao productDao = new ProductDaoImpl();
    @Test
    public void testQuery(){

        String sql="SELECT * FROM merchandise";

        try {
            List<Product> products = template.query(sql, new BeanPropertyRowMapper<>(Product.class));
            for (Product product : products) {
                String[] strings = product.getInformation().split("#");
                System.out.println(product.getName()+"------------");
                for (String string: strings){
                    System.out.println(string);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testProductMap(){
        String sql="SELECT * FROM merchandise";
        List<Product> productList = template.query(sql, new BeanPropertyRowMapper<>(Product.class));
        Map<Integer,Product> productMap=new HashMap<>();
        productList.forEach((e)->productMap.put(e.getMID(),e));
        Set<Integer> set = productMap.keySet();
        set.forEach(e-> System.out.println(e+": "+productMap.get(e)));
    }
    
    @Test
    public void testRefund(){
        System.out.println(orderDao.refund(26));
    }
    
    
    @Test
    public void testSearchAll(){
        ProductConfig config = new ProductConfig();
//        config.setSortMethod(SearchConfig.S_POPULARITY);
//        config.setName("mini");
        List<Product> all = productDao.getProductsBySearchingAll(config);
        System.out.println(all);
        all.forEach(System.out::println);
    }
    
    
    @Test
    public void testStringASDouble(){
        productDao.getProductUnderDiscount("1").forEach(System.out::println);
    }
    
    @Test
    public void testGetOrderList(){
        orderDao.getOrderList(1).forEach(System.out::println);
    }

    @Test
    public void testGetSearchOrderList(){
        OrderConfig config =new OrderConfig();
        config.setName("key");
        config.setUid(1);
        config.setStartTime("2018-04-16");
        config.setRefundable(OrderConfig.REFUND_YES);
        config.setEndTime("2020-10-06");
        config.setSortMethod(OrderConfig.S_TIME_FROM_NOW_TO_PREVIOUS);
        orderDao.getSearchList(config).forEach(System.out::println);
    }
    
    @Test
    public void testReadDate(){
        String sql="select generatetime from orders where orderId=?";
        Date date = template.queryForObject(sql, Date.class, 1);
        System.out.println(date);
    }
    
    @Test
    public void testSendMail() throws Exception {
        boolean result = MailUtil.sendEmail("2018212996123@bupt.edu.cn", "Taylor", "user1", "lalala", "hahaha");
        System.out.println(result);
    }
    
    @Test
    public void testInsertOrderInBackground(){
        String sql = "INSERT INTO bkm.ordertable VALUES(?,?,?,?,?,?,null)";
        int update = template.update(sql, 123, new Date(), "支付宝", "已完成", 1000, 1001);
    }

    @Test
    public void testInsertOrderInTotal(){
        String sql = "INSERT INTO bkm.productinorder VALUES(?,?,?,?,?,?,null)";
        int update = template.update(sql, 123, new Date(), "支付宝", "已完成", 1000, 1001);
    }
    
    @Test
    public void testGetAllComments(){
        CommentDao commentDao = new CommentDaoImpl();
        commentDao.getAll(1).forEach(System.out::println);
    }
    
//    @Test
//    public void createMkdirs() throws IOException {
////        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
//        BufferedReader reader = new BufferedReader(new FileReader("D:\\所有资料\\其他科目\\小学期2\\2018212996-黎兴东\\product.txt"));
//        BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\所有资料\\其他科目\\小学期2\\2018212996-黎兴东\\productList2.txt"));
//        String value;
//        StringBuilder builder = new StringBuilder(300);
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        while ((value=reader.readLine())!=null){
//            String[] split = value.split("\t");
//            for (int i=0;i<split.length;i++){
//                split[i]=split[i].trim();
//                split[i]=split[i].replaceAll("，",",");
//                split[i]=split[i].replaceAll("：",":");
//                split[i]=split[i].replaceAll("\"","");
//            }
//            String format1 = format.format(new Date());
//            split[9]=format1;
//            template.update("insert into merchandise values(?,?,?,?,?,?,?,?,?,?,?,?,?)",split[0],split[1],split[2],split[3],split[4],split[5],split[6],split[7],split[8],split[9],split[10],split[11],split[12]);
//            builder.append(Arrays.toString(split)).append("\n");
////            builder.append(split[4]).append("\n");
//        }
//
//        writer.write(builder.toString());
//        reader.close();
//        writer.close();
//    }
    
    
}
