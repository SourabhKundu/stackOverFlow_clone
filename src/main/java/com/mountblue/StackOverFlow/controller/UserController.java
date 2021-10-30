package com.mountblue.StackOverFlow.controller;

import com.mountblue.StackOverFlow.exception.UserNotFoundException;
import com.mountblue.StackOverFlow.model.Role;
import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.service.RoleService;
import com.mountblue.StackOverFlow.service.UserService;
import com.mountblue.StackOverFlow.service.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private RoleService roleService;

    private boolean isOtpVerified;

    @Autowired
    private EmailSenderService emailSenderService;

    private final int otp = (int) (1000 * Math.random());

    private String email = "";

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

    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@ModelAttribute("user") User user,
                               RedirectAttributes redirectAttributes) {
        if (userService.findUserByEmail(user.getEmail()) != null) {

            return new ResponseEntity<>("Already Registered",HttpStatus.OK);
        }
        Role role = roleService.getRoleByName("ROLE_USER");
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
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

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> sendOtpViaMail(@RequestBody String toEmail, Model model) {
        try {
            email = toEmail;

            emailSenderService.sendMail(toEmail, "OTP to change Password", "Your OTP to change password is; -" + otp);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestParam("otp") String usersOtp,
                                       RedirectAttributes redirectAttributes) {

        if (Integer.parseInt(usersOtp) == otp) {
            isOtpVerified = true;
            return new ResponseEntity<>(HttpStatus.OK);
        }

        redirectAttributes.addFlashAttribute("error", "!!! Incorrect OTP !!!");

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody String password) {

        if (!isOtpVerified)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        User user = userService.findUserByEmail(email);

        user.setPassword(password);
        userService.saveUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}