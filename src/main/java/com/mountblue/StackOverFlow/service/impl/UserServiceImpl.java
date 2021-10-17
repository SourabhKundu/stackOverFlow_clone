package com.mountblue.StackOverFlow.service.impl;

import com.mountblue.StackOverFlow.model.User;
import com.mountblue.StackOverFlow.repository.UserRepository;
import com.mountblue.StackOverFlow.service.UserService;
import com.mountblue.StackOverFlow.userDto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUserDetails(UserDto regDto) {
        User user = new User(regDto.getName(),regDto.getEmail(),
                regDto.getPassword());
        userRepository.save(user);
    }
}
