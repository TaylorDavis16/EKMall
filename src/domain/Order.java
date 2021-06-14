package domain;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private Integer orderId;
    private String name;
    private String brand;
    
    private int number;
    private double payment;
    
    //顾客id
    private int customer_cusID;
    //店主id
    private int merchant_merID;
    //商品id
    private int merchandise_mID;
    private Date generateTime;
    private int refundable;
    private String address;
    private String shippingStatus;
    private String status;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public int getCustomer_cusID() {
        return customer_cusID;
    }

    public void setCustomer_cusID(int customer_cusID) {
        this.customer_cusID = customer_cusID;
    }

    public int getMerchant_merID() {
        return merchant_merID;
    }

    public void setMerchant_merID(int merchant_merID) {
        this.merchant_merID = merchant_merID;
    }

    public int getMerchandise_mID() {
        return merchandise_mID;
    }

    public void setMerchandise_mID(int merchandise_mID) {
        this.merchandise_mID = merchandise_mID;
    }

    public Date getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(Date generateTime) {
        this.generateTime = generateTime;
    }

    public int getRefundable() {
        return refundable;
    }

    public void setRefundable(int refundable) {
        this.refundable = refundable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", number=" + number +
                ", payment=" + payment +
                ", customer_cusID=" + customer_cusID +
                ", merchant_merID=" + merchant_merID +
                ", merchandise_mID=" + merchandise_mID +
                ", generateTime=" + generateTime +
                ", refundable=" + refundable +
                ", address='" + address + '\'' +
                ", shippingStatus='" + shippingStatus + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
