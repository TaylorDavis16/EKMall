package service.interfaces;

import domain.User;

/**
 * Services for user
 */
public interface UserService {



    /**
     * Service that help user to login
     * @param user the user who trying to log in
     * @return the user object if log in successfully
     */
    User login(User user);

    /**
     * Service for user to register
     * @param user the user trying to register
     */
    int signUp(User user);

    /**
     * Service for administrator to delete user
     * @param user this user
     */
    int deleteUser(User user);
    
    int update(User user);
    
    int topUp(int cid, double amount);

    String findIfEmailExist(String email);

    int resetPassword(String email);

}
