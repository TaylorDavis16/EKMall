package service.impl;

import dao.impl.CartDaoImpl;
import domain.Cart;
import service.interfaces.CartService;

import java.util.List;
import java.util.Map;
/**
 * Services for cart
 */
public class CartServiceImpl implements CartService {
    private CartDaoImpl dao=new CartDaoImpl();
    @Override
    public int add(Cart cart) {
        return dao.add(cart);
    }

    @Override
    public int delete(Cart cart) {
        return dao.delete(cart);
    }

    @Override
    public int batchDelete(List<Cart> carts) {
        return dao.batchDelete(carts);
    }

    @Override
    public List<Cart> getCartList(int cid) {
        return dao.getCartList(cid);
    }

    @Override
    public Map<Integer, Cart> getCartMap(int cid) {
        return dao.getCartMap(cid);
    }


}
