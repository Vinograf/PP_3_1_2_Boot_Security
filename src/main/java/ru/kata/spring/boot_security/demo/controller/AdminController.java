package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

import ru.kata.spring.boot_security.demo.service.RoleServiceImp;

import ru.kata.spring.boot_security.demo.service.UserServiceImp;


import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;

    private final RoleServiceImp roleService;

    public AdminController(UserServiceImp userServiceImpl, RoleServiceImp roleService) {
        this.userServiceImp = userServiceImpl;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUser(Principal principal, Model model) {
        model.addAttribute("users", userServiceImp.findAll());
        model.addAttribute("logUser", userServiceImp.findByEmail(principal.getName()));
        model.addAttribute("roles", roleService.findAllRole());
        return "admin/list";
    }

    @GetMapping("/add")
    public String getUserFormCreation(Principal principal, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("logUser", userServiceImp.findByEmail(principal.getName()));
        model.addAttribute("roles", roleService.findAllRole());
        return "admin/add";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user) {
        userServiceImp.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userServiceImp.deleteById(id);
        return "redirect:/admin";
    }

    @PutMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userServiceImp.updateUser(id, user);
        return "redirect:/admin";
    }
}