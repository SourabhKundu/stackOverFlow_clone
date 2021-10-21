package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.exception.UserNotFoundException;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.service.UserService;
import com.mountblue.StackOverFlow.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AllUserController {

    private UserService userService;

    public AllUserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    final UserServiceImpl userServiceImpl;

    @RequestMapping("/searchUser")
    public String getSearchResult(@RequestParam("search") String keyword, Model model) {
        List<User> listUsers = userService.findUserByName(keyword);
        model.addAttribute("users", listUsers);

        return "allUsers";
    }

    @GetMapping("/allUsers")
    public String showAllUsers(Model model){
        List<User> users= userService.listAll();
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/users/viewUser/{id}")
    public String viewUser(@PathVariable(value = "id")Integer userId, Model model,RedirectAttributes redirectAttributes)  {

        try {
            User currentUser = userServiceImpl.getCurrentUser();
            User user = userService.getUserById(userId);
            if(user.getReputation() >= 50){
                model.addAttribute("goldBadge",1);
            }else{
                model.addAttribute("goldBadge",0);
            }
            if(user.getReputation() >= 20){
                model.addAttribute("silverBadge",1);
            }else{
                model.addAttribute("silverBadge",0);
            }
            if(user.getReputation() >= 10){
                model.addAttribute("bronzeBadge",1);
            }else{
                model.addAttribute("bronzeBadge",0);
            }
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("user", user);
            return "viewUser";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/allUsers";
        }
    }
}