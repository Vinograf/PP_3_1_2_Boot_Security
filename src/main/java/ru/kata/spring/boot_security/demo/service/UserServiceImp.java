package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service

public class UserServiceImp implements UserService {
    private final UserRepository userReposirory;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userReposirory, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userReposirory = userReposirory;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;    }
    @Transactional
    @Override
    public User passwordCoder(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return user;
    }



    @Transactional
    @Override
    public List<User> findAll() {
        return userReposirory.findAll();
    }
    @Transactional
    @Override
    public User getById(long id) {
        return userReposirory.getById(id);
    }
    @Transactional
    @Override
    public boolean save(User user) {
        if (findByUsername(user.getUsername()) != null && user.getId() == 0) {
            return false;
        }

        if (user.getId() != 0) {
            User userFromDb = userReposirory.getById(user.getId());
            if (!userFromDb.getPassword().equals(user.getPassword())) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            }
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userReposirory.saveAndFlush(user);
        return true;
    }
    @Transactional
    @Override
    public void deleteById(long id) {
        userReposirory.deleteById(id);
    }
    @Transactional
    @Override
    public User findByUsername(String username) {
        return userReposirory.findByUsername(username);
    }

    @Transactional
    @Override
    @PostConstruct
    public void addDefaultUser() {
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).get());
        Set<Role> roleSet2 = new HashSet<>();
        roleSet2.add(roleRepository.findById(1L).get());
        roleSet2.add(roleRepository.findById(2L).get());
        userReposirory.save(new User( "Misha", bCryptPasswordEncoder.encode("admin"), roleSet2 ));
        userReposirory.save(new User( "Dima", bCryptPasswordEncoder.encode("user"), roleSet ));
        userReposirory.save(new User("Kostya", bCryptPasswordEncoder.encode("dimab"),  roleSet));
    }
}
