package service.interfaces;

import domain.Cart;

import java.util.List;
import java.util.Map;
/**
 * Services for cart
 */
public interface CartService {
    int add(Cart cart);
    
    int delete(Cart cart);
    
    int batchDelete(List<Cart> carts);
    
    List<Cart> getCartList(int cid);
    
    Map<Integer,Cart> getCartMap(int cid);
}
