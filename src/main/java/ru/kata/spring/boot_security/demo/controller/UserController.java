package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getUserPage(Model model, Principal principal) {
        model.addAttribute("user",userService.findByUsername(principal.getName()));
        return "oneUser";
    }

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping(value={"/"})
    public String indexPage() {
        return "login";
    }

}