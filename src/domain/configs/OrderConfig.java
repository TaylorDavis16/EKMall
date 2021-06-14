package domain.configs;

import java.io.Serializable;
import java.util.Objects;

public class OrderConfig implements Serializable {
    public static final String ST_REFUNDED = "Refunded";
    public static final String ST_NORMAL = "Normal";
    public static final String ST_ALL = "All";
    public static final String REFUND_YES = "Yes";
    public static final String REFUND_NO = "No";
    public static final String REFUND_DEFAULT = "All";
    public static final String S_DEFAULT = "Default";
    public static final String S_PAYMENT_FROM_LOW_TO_HIGH = "Low to high";
    public static final String S_PAYMENT_FROM_HIGH_TO_LOW = "High to low";
    public static final String S_TIME_FROM_NOW_TO_PREVIOUS = "Now";
    public static final String S_TIME_FROM_PREVIOUS_TO_NOW = "Before";
    
    private int uid;
    private String name;
    private String brand;
    private String startTime;
    private String endTime;
    private String status;
    private String refundable;
    private String sortMethod;
    
    {
        this.uid=0;
        this.name="";
        this.brand="";
        this.startTime="";
        this.endTime="";
        this.status=OrderConfig.ST_ALL;
        this.refundable=OrderConfig.REFUND_DEFAULT;
        this.sortMethod=OrderConfig.S_DEFAULT;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefundable() {
        return refundable;
    }

    public void setRefundable(String refundable) {
        this.refundable = refundable;
    }

    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public String toString() {
        return "OrderConfig{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                ", refundable='" + refundable + '\'' +
                ", sortMethod='" + sortMethod + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderConfig config = (OrderConfig) o;
        return uid == config.uid &&
                Objects.equals(name, config.name) &&
                Objects.equals(brand, config.brand) &&
                Objects.equals(startTime, config.startTime) &&
                Objects.equals(endTime, config.endTime) &&
                Objects.equals(status, config.status) &&
                Objects.equals(refundable, config.refundable) &&
                Objects.equals(sortMethod, config.sortMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, name, brand, startTime, endTime, status, refundable, sortMethod);
    }
}
