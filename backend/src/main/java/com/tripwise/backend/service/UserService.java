package com.tripwise.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripwise.backend.controller.UserController;
import com.tripwise.backend.pojo.User;
import com.tripwise.backend.pojo.dto.UserDto;
import com.tripwise.backend.repository.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public User add(UserDto userDto) {
        User user = new User();
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(userDto.getPasswordHash());
        logger.info(user.toString());
        return userRepository.save(user);
    }

}
