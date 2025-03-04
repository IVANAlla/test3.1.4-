package web6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web6.dao.RoleDao;
import web6.dao.UserDao;
import web6.model.Role;
import web6.model.User;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleDao roleDao;
    @Autowired
    @Lazy
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleDao roleDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleDao = roleDao;
    }


    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public User getById(long id) {
        User user = null;
        Optional<User> optional = userDao.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void updateUser(User user) {
        if (!user.getPassword().equals(userDao.getById(user.getUserId()).getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDao.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userDao.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getUserByLogin(String login) {
        return userDao.findByUsername(login);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userDao.findByUsername(login);
    }
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    @Override
    @PostConstruct
    public void addDefaultUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleDao.findById(1L).orElse(null));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleDao.findById(1L).orElse(null));
        roles2.add(roleDao.findById(2L).orElse(null));
        User user1 = new User("Steve","Jobs",(byte) 25, "user@mail.com", "user","12345",roles1);
        User user2 = new User("Garry","Potter",(byte) 30, "admin@mail.com", "admin","admin",roles2);
        saveUser(user1);
        saveUser(user2);
    }
}
