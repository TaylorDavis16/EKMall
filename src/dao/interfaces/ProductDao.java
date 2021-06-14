package dao.interfaces;

import domain.Product;
import domain.configs.ProductConfig;

import java.util.List;
import java.util.Map;
/**
 * Dao, to handle product data access activities
 */
public interface ProductDao {
    
    List<Product> getProductList();
    
    int update(List<Object[]> list);
    
    List<String> getCategories();
    
    //New arrival
    List<Product> getIndexProduct();

    //Sell the best
    List<Product> getIndexProduct2();

    Map<Integer,Product> getProductMap();

    List<Product> getProductsBySearchingAll(ProductConfig config);

    List<Product> getProductUnderDiscount(String discount);

    List<Product> getProductsByName(String name);
    
    
}
