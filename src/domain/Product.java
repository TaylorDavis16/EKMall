package domain;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private int MID;
    private String name;
    private String category;
    private String brand;
    private String information;
    private double price;
    private int merchant_merID;
    private double discount;
    private int amount;
    private Date putUpTime;
    private String location;
    private int resourceNum;
    private int sales;

    public int getMID() {
        return MID;
    }

    public void setMID(int MID) {
        this.MID = MID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMerchant_merID() {
        return merchant_merID;
    }

    public void setMerchant_merID(int merchant_merID) {
        this.merchant_merID = merchant_merID;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getPutUpTime() {
        return putUpTime;
    }

    public void setPutUpTime(Date putUpTime) {
        this.putUpTime = putUpTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getResourceNum() {
        return resourceNum;
    }

    public void setResourceNum(int resourceNum) {
        this.resourceNum = resourceNum;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    @Override
    public String toString() {
        return "Product{" +
                "MID=" + MID +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", information='" + information + '\'' +
                ", price=" + price +
                ", merchant_merID=" + merchant_merID +
                ", discount=" + discount +
                ", amount=" + amount +
                ", putUpTime=" + putUpTime +
                ", location='" + location + '\'' +
                ", resourceNum=" + resourceNum +
                ", sales=" + sales +
                '}';
    }
}
