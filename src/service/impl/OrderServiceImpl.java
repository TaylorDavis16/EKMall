package service.impl;

import dao.impl.OrderDaoImpl;
import domain.Order;
import domain.configs.OrderConfig;
import domain.configs.PageBean;
import service.interfaces.OrderService;

import java.util.List;
/**
 * Services for order
 */
public class OrderServiceImpl implements OrderService {
    private OrderDaoImpl dao=new OrderDaoImpl();
    @Override
    public List<Order> getOrderList(int cid) {
        return dao.getOrderList(cid);
    }

    @Override
    public int writeRecord(Order order) {
        return dao.writeRecord(order);
    }

    @Override
    public int writeRecords(List<Object[]> orderList) {
        return dao.writeRecords(orderList);
    }

    @Override
    public double refund(int orderId) {
        return dao.refund(orderId);
    }

    @Override
    public List<Order> getSearchList(OrderConfig config) {
        return dao.getSearchList(config);
    }
    
}
