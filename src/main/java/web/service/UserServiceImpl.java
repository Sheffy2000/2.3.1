package web.service;

import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> showUsers() {
        return userDao.showUsers ();
    }

    @Transactional
    @Override
    public void addUser(User user) {
        userDao.addUser (user);
    }

    @Transactional
    @Override
    public User getUserById(int id) {
        return userDao.getUserById (id);
    }

    @Transactional
    @Override
    public void deleteUser(int id) {
        userDao.deleteUser (id);
    }
}
