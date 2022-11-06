package ru.kata.spring.boot_security.demo.DAO;





import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> printUser();
    void add(User user);
    void deleteById(Long id);
    void edit(User user);
    User getById(Long id);
    UserDetails getByName(String username);
}
