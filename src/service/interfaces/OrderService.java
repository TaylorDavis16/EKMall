package service.interfaces;

import domain.Order;
import domain.configs.OrderConfig;

import java.util.List;
/**
 * Services for order
 */
public interface OrderService {
    List<Order> getOrderList(int cid);
    
    int writeRecord(Order order);

    int writeRecords(List<Object[]> orderList);
    
    double refund(int orderId);

    List<Order> getSearchList(OrderConfig config);
}
