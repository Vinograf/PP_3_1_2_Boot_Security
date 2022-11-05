package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private UserServiceImp userService;

    private RoleServiceImp roleService;

    private BCryptPasswordEncoder passwordEncoder;

    public AdminController(UserServiceImp userService, RoleServiceImp roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        List<User> user = userService.printUser();
        model.addAttribute("user", user);


        return "allUsers";
    }

    @GetMapping(value = "add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add";
    }

    @PostMapping(value = "add")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(required = false) String roleAdmin) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleById(2L));
        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
            roles.add(roleService.getRoleById(1L));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.add(user);

        return "redirect:/admin/allUsers";
    }


    @GetMapping(value = "/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping(value = "/edit/{id}")
    public String userUpdate(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.edit(user);
        return "redirect:/admin/allUsers";
    }


    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/allUsers";
    }
}