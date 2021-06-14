package service.impl;

import dao.impl.UserDaoImpl;
import domain.User;
import service.interfaces.UserService;

/**
 * Services for user
 */
public class UserServiceImpl implements UserService {
    private UserDaoImpl dao=new UserDaoImpl();

    @Override
    public User login(User user) {
        return user==null ? null : dao.login(user);
    }

    @Override
    public int signUp(User user) {
        return dao.signUp(user);
    }

    @Override
    public int deleteUser(User user) {
        return 0;
    }

    @Override
    public int update(User user) {
        return dao.update(user);
    }

    @Override
    public int topUp(int cid, double amount) {
        return dao.topUp(cid,amount);
    }

    @Override
    public String findIfEmailExist(String email) {
        return dao.findIfEmailExist(email);
    }

    @Override
    public int resetPassword(String email) {
        return dao.resetPassword(email);
    }

}
