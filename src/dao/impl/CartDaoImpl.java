package dao.impl;

import dao.interfaces.CartDao;
import domain.Cart;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao, to handle cart data access activities
 */
public class CartDaoImpl implements CartDao {
    private JdbcTemplate template=JDBCUtils.getTemplate();
    /**
     * Update or delete
     */
    private final int case1=1;
    /**
     * Insert or update
     */
    private final int case2=2;
    /**
     * Exception occur 
     */
    private final int exception=0;
    @Override
    public int add(Cart cart) {
        try {
            switch (addable(cart)){
                case case1:
                    return template.update("UPDATE cart SET number=number+? WHERE cid=? AND mid=?",
                            cart.getNumber(), cart.getCid(), cart.getMid());
                case case2:
                    return template.update("INSERT INTO cart VALUES(?,?,?)",cart.getCid(),cart.getMid(),cart.getNumber());
                default:
                    return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return exception;
        }
        
    }

    @Override
    public int delete(Cart cart) {
        try {
            switch (deletable(cart)){
                case case1:
                    return template.update("DELETE FROM cart WHERE cid=? AND mid=?", cart.getCid(), cart.getMid());
                case case2:
                    return template.update("UPDATE cart SET number=number-? WHERE cid=? AND mid=?",
                            cart.getNumber(), cart.getCid(), cart.getMid());
                default:
                    return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
            return exception;
        }
    }

    @Override
    public int batchDelete(List<Cart> carts) {
        String sql="DELETE FROM cart WHERE cid=? AND MID=?";
        int count=0;
        try {
            for (Cart cart : carts) {
                count+=template.update(sql,cart.getCid(),cart.getMid());
            }
            return count;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        
    }

    @Override
    public int addable(Cart cart) {
        String sql="SELECT COUNT(*) FROM cart WHERE cid=? AND mid=?";
        try {
            int i = template.queryForObject(sql, Integer.class, cart.getCid(), cart.getMid());
            System.out.println("addable: "+i);
            if (i!=0){
                return case1;
            }
            else return case2;
        }catch (Exception e){
            e.printStackTrace();
            return exception;
        }
    }

    @Override
    public int deletable(Cart cart) {
        String sql="SELECT number FROM cart WHERE cid=? AND mid=?";
        try {
            int i = template.queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class), cart.getCid(), cart.getMid());
            if (i==cart.getNumber()){
                return case1;
            }
            else return case2;
        }catch (Exception e){
            e.printStackTrace();
            return exception;
        }
    }

    @Override
    public List<Cart> getCartList(int cid) {
        String sql="SELECT * FROM cart WHERE cid=?";
        try {
            return template.query(sql,new BeanPropertyRowMapper<>(Cart.class), cid);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map<Integer, Cart> getCartMap(int cid) {
        String sql="SELECT * FROM cart WHERE cid=?";
        try {
            List<Cart> cartList = template.query(sql, new BeanPropertyRowMapper<>(Cart.class),cid);
            Map<Integer,Cart> cartMap=new HashMap<>();
            cartList.forEach((e)->cartMap.put(e.getMid(),e));
            return cartMap;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
