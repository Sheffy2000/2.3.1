package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
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
        em.remove (em.getReference (User.class, id));
    }

    @Override
    public User getUserById(int id) {
        User user = em.find (User.class, id);
        return user;
    }

    @Override
    public void addUser(User user) {
        em.merge (user);
    }
}
