package com.mountblue.StackOverFlow.service;

import com.mountblue.StackOverFlow.exception.UserNotFoundException;
import com.mountblue.StackOverFlow.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService  {
    List<User>listAll();

    void saveUser(User user);

    User getUserById(Integer userId) throws UserNotFoundException;

    void deleteUserById(Integer userId);

    User getUserByEmail(String currentUserEmail);
    public User getUserFromContext();

    List<User> findUserByName(String keyword);
}
