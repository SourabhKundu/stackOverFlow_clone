package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.exception.UserNotFoundException;
import com.mountblue.StackOverFlow.model.Role;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.service.RoleService;
import com.mountblue.StackOverFlow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String homepage() {
        System.out.println("Hi");
        return "index";
    }

    @GetMapping("/users")
    public String showAllUsers(Model model){
        List<User> users= userService.listAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user")User user){
        Role role = roleService.getRoleByName("ROLE_USER");
        Set<Role> roles= user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/registration?success";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/users/editUser/{id}")
    public String editUser(@PathVariable(value = "id")Integer userId, Model model,RedirectAttributes redirectAttributes)  {

        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            return "edit_user";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }

    }

    @PostMapping("/users/updateUser")
    public  String updateUser(@ModelAttribute("user") User user){
        Role role = roleService.getRoleByName("ROLE_USER");
        Set<Role> roles= user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id")Integer userId)  {

        userService.deleteUserById(userId);
        return  "redirect:/users";

    }

}
