package dao.impl;

import dao.interfaces.UserDao;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;
import utils.Md5Util;

import java.util.List;


/**
 * Dao, to handle user data access activities
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate template=JDBCUtils.getTemplate();

    @Override
    public List<User> findAll() {
        String sql="select * from staff";
        return template.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int signUp(User user) {
        String sql="insert into customer values(null,?,?,?,?,?,?)";
        try {
            Integer nameExist = template.queryForObject("select count(*) from customer where username=?", Integer.class, user.getUsername());
            Integer emailExist = template.queryForObject("select count(*) from customer where email=?", Integer.class, user.getEmail());
            if (nameExist!=null && nameExist>0){
                return 0;
            }
            if (emailExist!=null && emailExist>0){
                return -1;
            }
            return template.update(sql,user.getUsername(),user.getPassword(),user.getTelephone(),user.getEmail(),null,0);
        }catch (Exception e){
            e.printStackTrace();
            return -2;
        }
    }

    @Override
    public int delete(User user) {
        String sql="delete from customer where id=?";
        try {
            return template.update(sql,user.getCusID());
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        
    }

    @Override
    public User login(User user) {
        String sql="select * from customer where username=? and password=?";
        try{
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUsername(), user.getPassword());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int update(User user) {
        String sql="update customer set username=?,password=?,telephone=?,email=?,address=? where cusID=?";
        try {
            return template.update(sql,user.getUsername(),user.getPassword(),user.getTelephone(),
                    user.getEmail(),user.getAddress(),user.getCusID());
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    


    

    @Override
    public int topUp(int cid, double amount) {
        String sql="UPDATE customer SET balance=balance+? WHERE cusID=?";
        try {
            return template.update(sql,amount,cid);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        
    }

    @Override
    public String findIfEmailExist(String email) {
        String sql = "select count(*) from customer where email=?";
        String sql2 = "select username from customer where email=?";
        try {
            Integer integer = template.queryForObject(sql, Integer.class, email);
            if (integer != null && integer == 1) {//if user existed!
                return template.queryForObject(sql2, String.class, email);
            }
            return null;//not existed
        }catch (Exception e){
            e.printStackTrace();
            return null;//not existed
        }
    }

    @Override
    public int resetPassword(String email) {
        String sql="UPDATE customer SET password=? WHERE email=?";
        try {
            return template.update(sql, Md5Util.encodeByMd5("2000416"),email);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
