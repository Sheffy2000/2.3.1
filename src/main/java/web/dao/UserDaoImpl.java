package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> showUsers() {
        List<User> allUsers = em.createQuery ("from User", User.class).getResultList ();
        return allUsers;
    }

    @Override
    public void deleteUser(int id) {
        User user = em.find (User.class, id);
        if (user == null) {
            throw new EntityNotFoundException ("Такого пользователя нет");
        }
        em.remove (user);
    }

    @Override
    public User getUserById(int id) {
        User user = em.find (User.class, id);
        if (user == null) {
            throw new EntityNotFoundException ("Такого пользователя нет");
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        em.persist (user);
    }

    @Override
    public void updateUser(User user) {
        em.merge (user);
    }
}