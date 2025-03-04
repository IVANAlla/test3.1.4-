package web6.service;

import web6.model.User;
import java.util.List;

public interface UserService {

    User getById(long id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(long id);

    List<User> getAllUsers();

    User getUserByLogin(String login);

    User getUserById(long id);

    void addDefaultUser();
    User findByUsername(String username);
}
