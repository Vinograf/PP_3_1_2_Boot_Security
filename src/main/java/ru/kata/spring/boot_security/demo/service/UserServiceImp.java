package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.model.User;


import java.util.List;
@Service

public class UserServiceImp implements UserService {

    private final UserDao userDao;
    @Autowired

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }
    @Transactional
    @Override
    public List<User> printUser() {
        return userDao.printUser();
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }
    @Transactional
    @Override
    public void edit(User user) {
        userDao.edit(user);
    }
    @Transactional
    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.getByName(username);
    }
}
