package dao.impl;

import dao.interfaces.OrderDao;
import domain.Order;
import domain.configs.OrderConfig;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Dao, to handle order data access activities
 */
public class OrderDaoImpl implements OrderDao {
    private JdbcTemplate template=JDBCUtils.getTemplate();

    @Override
    public List<Order> getOrderList(int cid) {
        String sql = "SELECT NAME, brand, o.* FROM orders o INNER JOIN merchandise mer WHERE o.customer_cusID=? AND mer.MID = o.merchandise_mID";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(Order.class), cid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int writeRecord(Order order) {
        String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO bkm.ordertable VALUES(?,?,?,?,?,?,null)";
        try {
            long millis = System.currentTimeMillis();
            Random random = new Random();
            template.update(sql2, millis+random.nextInt(999999), new Date(), millis %2==0?"微信":"支付宝",
                    "已完成", order.getPayment(), order.getCustomer_cusID()+1105);
            return template.update(sql, order.getOrderId(), order.getNumber(), order.getPayment(), order.getCustomer_cusID(),
                    order.getMerchant_merID(), order.getMerchandise_mID(), order.getGenerateTime(),
                    order.getRefundable(), order.getAddress(), order.getShippingStatus(), order.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int writeRecords(List<Object[]> orderList) {
        String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        String sql2 = "INSERT INTO bkm.ordertable VALUES(?,?,?,?,?,?,null)";
        String sql3 = "INSERT INTO bkm.productinorder VALUES(?,?,?,?,?,null)";
        try {
            
            int[] ints = template.batchUpdate(sql, orderList);
            int count = 0;
            for (int i : ints) {
                count += i;
            }
            Random random = new Random();
            int a = random.nextInt(1000);
            int b = random.nextInt(1000);
            double total= orderList.stream().mapToDouble(e -> (Double) e[2]).sum();
            int cusId = (int) orderList.get(0)[3];
            long millis = System.currentTimeMillis();
            template.update(sql2, millis+a*b+a, new Date(), millis %2==0?"微信":"支付宝",
                    "Finish", total, cusId+1105);
            orderList.forEach(e-> template.update(sql3,(Double)e[2]/(int) e[1],e[1],millis+a*b+a,Integer.parseInt((String) e[5])+28,"Finish"));
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public double refund(int orderId) {
        String sql = "UPDATE orders SET refundable=?,shippingStatus=?,status=? where orderId=?";
        try {
            double refund = template.queryForObject("SELECT payment FROM orders WHERE orderId=?", Double.class, orderId);
            template.update(sql, 0, "Refunded", "Refunded", orderId);
            return refund;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Order> getSearchList(OrderConfig config) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT NAME, brand, o.* FROM orders o INNER JOIN (SELECT MID,NAME,brand FROM merchandise mer");
        sqlBuilder.append(" WHERE mer.name LIKE \"%\"?\"%\" AND mer.brand LIKE \"%\"?\"%\") p");
        sqlBuilder.append(" WHERE o.customer_cusID=? AND p.MID = o.merchandise_mID");
        String refundable;
        String startTime;
        String endTime;
        String status;

        if (!OrderConfig.REFUND_DEFAULT.equals(config.getRefundable())) {
            sqlBuilder.append(" AND o.refundable=?");
            if (OrderConfig.REFUND_YES.equals(config.getRefundable())) {//refundable is yes
                refundable = "1";
            } else {//refundable is no
                refundable = "0";
            }
        } else {
            sqlBuilder.append(" AND 1 = ?");//refundable is default
            refundable = "1";
        }

        if (!("".equals(config.getStartTime()) && "".equals(config.getEndTime()))) {
            if ("".equals(config.getStartTime())) {//no started time, has ended time
                sqlBuilder.append(" AND 1 = ? AND generatetime < ?");
                startTime = "1";
                endTime = config.getEndTime();
            } else if ("".equals(config.getEndTime())) {
                sqlBuilder.append(" AND generatetime >= ? AND 1 = ?");//has started time, no ended time
                startTime = config.getStartTime();
                endTime = "1";
            } else {
                sqlBuilder.append(" AND generatetime >= ? AND generatetime < ?");//has both started time and ended time
                startTime = config.getStartTime();
                endTime = config.getEndTime();
            }
        } else {
            sqlBuilder.append(" AND 1 = ? AND 1 = ?");//without both started time and ended time
            startTime = "1";
            endTime = "1";
        }

        if (OrderConfig.ST_ALL.equals(config.getStatus())) {//status is all
            sqlBuilder.append(" AND 1 = ?");
            status = "1";
        } else {
            sqlBuilder.append(" AND status = ?");//status is refunded or normal
            status = config.getStatus();
        }

        searchSqlBuilder(config.getSortMethod(), sqlBuilder);

        try {
            return template.query(sqlBuilder.toString(), new BeanPropertyRowMapper<>(Order.class), config.getName(), config.getBrand(),
                    config.getUid(), refundable, startTime, endTime, status);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void searchSqlBuilder(String sortMethod, StringBuilder sqlBuilder) {
        switch (sortMethod) {
            case OrderConfig.S_DEFAULT:
                break;
            case OrderConfig.S_TIME_FROM_NOW_TO_PREVIOUS:
                sqlBuilder.append(" order by generatetime desc");
                break;
            case OrderConfig.S_TIME_FROM_PREVIOUS_TO_NOW:
                sqlBuilder.append(" order by generatetime");
                break;
            case OrderConfig.S_PAYMENT_FROM_LOW_TO_HIGH:
                sqlBuilder.append(" order by payment");
                break;
            case OrderConfig.S_PAYMENT_FROM_HIGH_TO_LOW:
                sqlBuilder.append(" order by payment desc");
                break;
        }
    }

}
