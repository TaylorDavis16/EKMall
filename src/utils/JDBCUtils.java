package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * This class is used to get data source from connection pool
 */
public class JDBCUtils {
    private static DataSource dataSource;
    private static JdbcTemplate template;
    static {
        try {
            Properties properties=new Properties();
            InputStream is= JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            if (is==null)
                throw new RuntimeException("Cannot initialize config file! Check your file address!");
            properties.load(is);
            //Initialize dataSource object
            dataSource= DruidDataSourceFactory.createDataSource(properties);
            template= new JdbcTemplate(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource(){
        return dataSource;
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static JdbcTemplate getTemplate() {
        return template;
    }
}
