package com.test.project.controller;

import com.test.project.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getMainPage(Model model) {
        model.addAttribute("users", userService.findAllReaders());
        model.addAttribute("managers", userService.findAllManagers());

        return "main";
    }

    @PostMapping("/user")
    public String getUserPage(@RequestParam Long id) {
        return String.format("redirect:/users/%d", id);
    }

    @PostMapping("/manager")
    public String getManagerPage(@RequestParam Long id) {
        return String.format("redirect:/managers/%d", id);
    }
}
