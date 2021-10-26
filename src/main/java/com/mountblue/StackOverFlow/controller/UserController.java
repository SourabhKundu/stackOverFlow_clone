package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.exception.UserNotFoundException;
import com.mountblue.StackOverFlow.model.Role;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.service.RoleService;
import com.mountblue.StackOverFlow.service.UserService;
import com.mountblue.StackOverFlow.service.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private EmailSenderService emailSenderService;

    private final int otp = (int) (1000 * Math.random());

    private String email= "";

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") User user) {
        Role role = roleService.getRoleByName("ROLE_USER");
        Set<Role> roles = user.getRoles();
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
    public String editUser(@PathVariable(value = "id") Integer userId, Model model, RedirectAttributes redirectAttributes) {

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
    public String updateUser(@ModelAttribute("user") User user) {
        Role role = roleService.getRoleByName("ROLE_USER");
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") Integer userId) {

        userService.deleteUserById(userId);

        return "redirect:/users";
    }

    @GetMapping("/forgotPassword")
    public String forgotPassword() {
        System.out.println("here");
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String sendOtpViaMail(@RequestParam("email") String toEmail, Model model) {
        email=toEmail;
        System.out.println("to email "+toEmail);
        emailSenderService.sendMail(toEmail, "OTP to change Password", "Your OTP to change password is; -" + otp);

        return "otp";
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("otp") String usersOtp,
                            RedirectAttributes redirectAttributes){

        System.out.println("verify otp");
        if(Integer.parseInt(usersOtp)==otp){
            System.out.println(" inside if "+otp+" userOtp "+usersOtp);
            return "resetPassword";
        }
        redirectAttributes.addFlashAttribute("error", "!!! Incorrect OTP !!!");

        return "otp";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("password") String password){
        System.out.println("password "+password);
        User user=userService.findUserByEmail(email);
        user.setPassword(password);
        userService.saveUser(user);
        System.out.println("changed ");
        return "login";
    }
}