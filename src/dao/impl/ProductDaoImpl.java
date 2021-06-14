package dao.impl;

import dao.interfaces.ProductDao;
import domain.Product;
import domain.configs.ProductConfig;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao, to handle product data access activities
 */
public class ProductDaoImpl implements ProductDao {

    private JdbcTemplate template=JDBCUtils.getTemplate();


    @Override
    public List<Product> getProductList() {
        String sql = "SELECT * FROM merchandise";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(Product.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int update(List<Object[]> list) {
        String sql = "UPDATE merchandise SET amount=amount-?,sales=sales+? where mID=?";
        try {
            int[] ints = template.batchUpdate(sql, list);
            int count = 0;
            for (int i : ints) {
                count += i;
            }
            return count;
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<String> getCategories() {
        String sql = "SELECT INSTINCT category FROM merchandise";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getIndexProduct() {
        String sql = "SELECT * FROM merchandise ORDER BY putUpTime DESC LIMIT ?";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(Product.class), 4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getIndexProduct2() {
        String sql = "SELECT * FROM merchandise ORDER BY sales DESC LIMIT ?";
        try {
            return template.query(sql, new BeanPropertyRowMapper<>(Product.class), 4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public Map<Integer, Product> getProductMap() {
        String sql = "SELECT * FROM merchandise";
        try {
            List<Product> productList = template.query(sql, new BeanPropertyRowMapper<>(Product.class));
            Map<Integer, Product> productMap = new HashMap<>();
            productList.forEach((e) -> productMap.put(e.getMID(), e));
            return productMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getProductsByName(String name) {
        String sql = "SELECT * FROM merchandise WHERE name LIKE \"%\"?\"%\"";
        try{
            return template.query(sql,new BeanPropertyRowMapper<>(Product.class),name);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getProductsBySearchingAll(ProductConfig config) {
        String sortMethod = config.getSortMethod();
        String category = config.getCategory();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM merchandise WHERE");
        if (ProductConfig.C_ALL.equals(category)){
            sqlBuilder.append(" 1 = ?");
            category = "1";
        }else {
            sqlBuilder.append(" category = ?");
        }
        sqlBuilder.append(" AND NAME LIKE \"%\"?\"%\" AND brand LIKE \"%\"?\"%\" AND discount<=?");
        
        searchSqlBuilder(sortMethod, sqlBuilder);
        try {
            return template.query(sqlBuilder.toString(), new BeanPropertyRowMapper<>(Product.class),
                    category,config.getName(), config.getBrand(), config.getDiscount());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> getProductUnderDiscount(String discount) {
        String sql = "SELECT * FROM merchandise WHERE discount<=?";
        return template.query(sql, new BeanPropertyRowMapper<>(Product.class), discount);
    }

    

    private void searchSqlBuilder(String sortMethod, StringBuilder sqlBuilder) {
        switch (sortMethod) {
            case ProductConfig.S_DEFAULT:
                break;
            case ProductConfig.S_PRICE_FROM_LOW_TO_HIGH:
                sqlBuilder.append(" order by price");
                break;
            case ProductConfig.S_PRICE_FROM_HIGH_TO_LOW:
                sqlBuilder.append(" order by price desc");
                break;
            case ProductConfig.S_POPULARITY:
                sqlBuilder.append(" order by sales desc");
                break;
            case ProductConfig.S_PutUpTime_Early:
                sqlBuilder.append(" order by putUpTime");
                break;
            case ProductConfig.S_PutUpTime_Latest:
                sqlBuilder.append(" order by putUpTime desc");
                break;
        }
    }

}


