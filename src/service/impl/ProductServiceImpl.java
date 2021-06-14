package service.impl;

import dao.impl.ProductDaoImpl;
import domain.Product;
import domain.configs.ProductConfig;
import service.interfaces.ProductService;

import java.util.List;
import java.util.Map;
/**
 * Services for product
 */
public class ProductServiceImpl implements ProductService {
    private ProductDaoImpl dao=new ProductDaoImpl();
    @Override
    public List<Product> getProductList() {
        return dao.getProductList();
    }

    @Override
    public int update(List<Object[]> list) {
        return dao.update(list);
    }


    @Override
    public List<String> getCategories() {
        return dao.getCategories();
    }

    @Override
    public List<Product> getIndexProduct1() {
        return dao.getIndexProduct();
    }

    @Override
    public List<Product> getIndexProduct2() {
        return dao.getIndexProduct2();
    }

    @Override
    public Map<Integer, Product> getProductMap() {
        return dao.getProductMap();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return dao.getProductsByName(name);
    }

    @Override
    public List<Product> getProductsBySearching(ProductConfig config) {
        return dao.getProductsBySearchingAll(config);
    }
}
