package dao.interfaces;

import domain.Order;
import domain.configs.OrderConfig;

import java.util.List;
/**
 * Dao, to handle order data access activities
 */
public interface OrderDao {
    List<Order> getOrderList(int cid);

    int writeRecord(Order order);

    int writeRecords(List<Object[]> orderList);

    double refund(int orderId);

    List<Order> getSearchList(OrderConfig config);
}
