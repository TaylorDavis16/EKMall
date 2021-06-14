package dao.interfaces;

import domain.Cart;

import java.util.List;
import java.util.Map;
/**
 * Dao, to handle cart data access activities
 */
public interface CartDao {
    int add(Cart cart);
    
    int delete(Cart cart);
    
    int batchDelete(List<Cart> carts);
    
    int addable(Cart cart);
    
    int deletable(Cart cart);

    List<Cart> getCartList(int cid);

    Map<Integer,Cart> getCartMap(int cid);
}
