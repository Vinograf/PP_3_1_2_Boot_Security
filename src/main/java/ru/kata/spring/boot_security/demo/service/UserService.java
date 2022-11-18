package ru.kata.spring.boot_security.demo.service;


import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {


    User passwordCoder(User user);

    User findByEmail(String email);

    List<User> findAll();

    User getById(long id);

    void save(User user);

    void deleteById(long id);


    void updateUser(Long id, User user);


//    void addDefaultUser();


}
