package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userReposirory, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userReposirory = userReposirory;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public void save(User user) {
        userReposirory.save(passwordCoder(user));
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
        userReposirory.save(new User( "Misha", passwordEncoder.encode("admin"), roleSet2 ));
        userReposirory.save(new User( "Dima", passwordEncoder.encode("user"), roleSet ));
        userReposirory.save(new User("Kostya", passwordEncoder.encode("dimab"),  roleSet));
    }
}
