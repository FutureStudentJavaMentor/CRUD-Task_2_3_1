package web.service;

import org.springframework.stereotype.Component;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Component
public class UserServiceImp implements UserService {

    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void saveNewUser(User user) {
      userDao.saveNewUser(user);
    }

    @Override
    public List<User> getUsersList() {
        return userDao.getUsersList();
    }

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public User updateUser(User user,long id) {
      return  userDao.updateUser( user,id);
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }
}
