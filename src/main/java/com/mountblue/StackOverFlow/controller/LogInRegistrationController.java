package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.service.UserService;
import com.mountblue.StackOverFlow.userDto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogInRegistrationController {

    private final UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    public LogInRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String createNewUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") UserDto userDto) {
        userService.saveUserDetails(userDto);
        return "registration";
    }
}
