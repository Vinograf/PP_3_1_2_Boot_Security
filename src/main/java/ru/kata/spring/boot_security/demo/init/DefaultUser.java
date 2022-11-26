package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class DefaultUser {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DefaultUser(UserService userRepository, RoleService roleRepository) {
        this.userService = userRepository;
        this.roleService = roleRepository;
    }

    @PostConstruct
    public void initialize() {
        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        roleService.save(user);
        roleService.save(admin);

        User defaultAdmin = new User("admin@mail.ru", "admin");
        User defaultUser = new User("user@mail.ru","admin");

        defaultAdmin.setRole(roleService.findByName("ROLE_ADMIN"));
        defaultAdmin.setRole(roleService.findByName("ROLE_USER"));
        defaultUser.setRole(roleService.findByName("ROLE_USER"));

        defaultAdmin.setName("Admin");
        defaultAdmin.setSurname("Admin");

        defaultUser.setName("User");
        defaultUser.setSurname("User");

        userService.saveUser(defaultAdmin);
        userService.saveUser(defaultUser);


    }
}
