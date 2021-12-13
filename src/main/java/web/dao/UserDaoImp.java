package web.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public void saveNewUser(User user) {
        entityManager.persist(user);
    }


    @Override
    public List<User> getUsersList() {
        return entityManager.createQuery("select u from  User u", User.class).getResultList();

    }


    @Transactional
    @Override
    public User findById(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public User updateUser(User user, long id) {
        User  toBeUpdated = findById(id);
        if(user.getId()==id){
            toBeUpdated.setName(user.getName());
            toBeUpdated.setLastName(user.getLastName());
            toBeUpdated.setAge(user.getAge());
        }
       return entityManager.merge(toBeUpdated);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        entityManager.remove(entityManager.merge(findById(id)));
    }


}
