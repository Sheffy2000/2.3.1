package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {
    public List<User> showUsers();

    public void deleteUser(int id);

    public User getUserById(int id);

    public void addUser(User user);
}
