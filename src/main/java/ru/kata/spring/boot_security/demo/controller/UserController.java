package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserServiceImp userServiceImp;

    public UserController(UserServiceImp userServiceImpl) {
        this.userServiceImp = userServiceImpl;
    }

    @GetMapping()
    public String getUser(Principal principal, Model model) {
        model.addAttribute("logUser", userServiceImp.findByEmail(principal.getName()));
        return "user/user";
    }



}