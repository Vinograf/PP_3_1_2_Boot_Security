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
    private final UserRepository userRepository;
    private final RoleServiceImp roleService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    @Override
    public User passwordCoder(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return user;
    }

    @Autowired
    public UserServiceImp(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder,  RoleServiceImp roleService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }




    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Transactional
    @Override
    public User getById(long id) {
        return userRepository.getById(id);
    }
    @Transactional
    @Override
    public void save(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }
    @Transactional
    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateUser(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
    }


//    @Override
//    @PostConstruct
//    public void addDefaultUser() {
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(roleRepository.findById(1L).get());
//        Set<Role> roleSet2 = new HashSet<>();
//        roleSet2.add(roleRepository.findById(1L).get());
//        roleSet2.add(roleRepository.findById(2L).get());
//        userRepository.save(new User(bCryptPasswordEncoder.encode("admin") , "Dima", "Vinogradov", (byte) 31, "gay@mail.ru", roleSet2));
//        userRepository.save(new User( bCryptPasswordEncoder.encode("user"), "Dima123", "Vinogradov123", (byte) 31, "notgay@mail.ru", roleSet));
//
//    }
}
