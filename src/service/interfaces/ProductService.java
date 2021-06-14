package service.interfaces;

import domain.Product;
import domain.configs.PageBean;
import domain.configs.ProductConfig;

import java.util.List;
import java.util.Map;
/**
 * Services for product
 */
public interface ProductService {
    List<Product> getProductList();

    int update(List<Object[]> list);

    List<String> getCategories();

    public List<Product> getIndexProduct1();

    public List<Product> getIndexProduct2();

    Map<Integer, Product> getProductMap();

    List<Product> getProductsByName(String name);

    List<Product> getProductsBySearching(ProductConfig config);
}
