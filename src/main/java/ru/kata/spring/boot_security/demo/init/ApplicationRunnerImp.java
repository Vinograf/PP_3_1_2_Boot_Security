package ru.kata.spring.boot_security.demo.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Component
public class ApplicationRunnerImp implements ApplicationRunner {
    private UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleService roleService;

    public ApplicationRunnerImp(UserService userService, BCryptPasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<User> users = userService.printUser();

        if (users.isEmpty()) {
            roleService.addRole(new Role("ROLE_ADMIN"));
            roleService.addRole(new Role("ROLE_USER"));
            Role admin = roleService.getRoleById(1L);
            Role user = roleService.getRoleById(2L);
            Set<Role> adminRole = new HashSet<>();
            Set<Role> userRole = new HashSet<>();
            adminRole.add(admin);
            userRole.add(user);
            userService.add(new User( "Misha", passwordEncoder.encode("admin"), adminRole ));
            userService.add(new User( "Dima", passwordEncoder.encode("user"), userRole ));
            userService.add(new User("Kostya", passwordEncoder.encode("dimab"),  userRole));
            userService.add(new User("vasyap", passwordEncoder.encode("vasyap"), userRole));
            userService.add(new User("kostyap", passwordEncoder.encode("kostyag"), userRole));

        }
    }
}
