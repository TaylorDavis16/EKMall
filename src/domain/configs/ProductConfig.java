package domain.configs;

import java.io.Serializable;
import java.util.Objects;

public class ProductConfig implements Serializable {
    public static final String S_DEFAULT = "Default";
    public static final String S_PRICE_FROM_LOW_TO_HIGH = "Low to high";
    public static final String S_PRICE_FROM_HIGH_TO_LOW = "High to low";
    public static final String S_POPULARITY = "Popularity";
    public static final String S_PutUpTime_Early = "Time: Early";
    public static final String S_PutUpTime_Latest = "Time: Latest";
    public static final String C_ALL = "All";
    public static final String C_CHILDREN = "Children";
    public static final String C_ACCESSORY = "Accessory";
    public static final String C_ADULT = "Adult";
    private String name;
    private String brand;
    private String discount;
    private String category;
    private String sortMethod;
    
    {
        this.name="";
        this.brand="";
        this.discount="1.0";
        this.category=ProductConfig.C_ALL;
        this.sortMethod=ProductConfig.S_DEFAULT;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSortMethod() {
        return sortMethod;
    }

    public void setSortMethod(String sortMethod) {
        this.sortMethod = sortMethod;
    }

    @Override
    public String toString() {
        return "ProductConfig{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", discount='" + discount + '\'' +
                ", category='" + category + '\'' +
                ", sortMethod='" + sortMethod + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductConfig config = (ProductConfig) o;
        return Objects.equals(name, config.name) &&
                Objects.equals(brand, config.brand) &&
                Objects.equals(discount, config.discount) &&
                Objects.equals(category, config.category) &&
                Objects.equals(sortMethod, config.sortMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, discount, category, sortMethod);
    }
}
