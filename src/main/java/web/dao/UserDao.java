package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {


    void saveNewUser(User user);

    List<User> getUsersList();

    User findById(long id);

    User updateUser(User user,long id);

    void deleteUser(long id);



}
