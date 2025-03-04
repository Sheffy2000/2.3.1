package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    public List<User> showUsers();

    public void addUser(User user);

    public User getUserById(int id);

    public void deleteUser(int id);
}
