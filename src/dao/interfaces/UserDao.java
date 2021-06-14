package dao.interfaces;

import domain.User;

import java.util.List;
import java.util.Map;
/**
 * Dao, to handle user data access activities
 */
public interface UserDao {
    List<User> findAll();

    

    //Register
    int signUp(User user);

    int delete(User user);

    //Login
    User login(User user);

    int update(User user);
    
    
    
    int topUp(int cid, double amount);

    String findIfEmailExist(String email);

    int resetPassword(String email);
}
