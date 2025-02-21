package com.tripwise.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripwise.backend.controller.UserController;
import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.entity.User;
import com.tripwise.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public User add(UserDto userDto) {
        User user = mapDtoToEntity(userDto);
        logger.info("Adding User: " + user.toString());
        return userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        logger.info("Fetching All Users: " + users.size() + " users found.");
        return users.stream()
                .map(user -> new UserDto(user.getUserId(), user.getEmail(), user.getPasswordHash(),
                        user.getNickname(), user.getProfilePhoto(),
                        user.getCreatedAt(), user.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("Fetching User By ID: " + id + ", Found: " + user.toString());
            return new UserDto(user.getUserId(), user.getEmail(), user.getPasswordHash(),
                    user.getNickname(), user.getProfilePhoto(),
                    user.getCreatedAt(), user.getUpdatedAt());
        } else {
            logger.warn("User with ID " + id + " not found.");
            // throw new UserNotFoundException("User with ID " + id + " not found");
            return null;
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {
        logger.warn("looking for email: " + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info("Fetching User By Email: " + email + ", Found: " + user.toString());
            return new UserDto(user.getUserId(), user.getEmail(), user.getPasswordHash(),
                               user.getNickname(), user.getProfilePhoto(),
                               user.getCreatedAt(), user.getUpdatedAt());
        } else {
            logger.warn("User with email " + email + " not found.");
            // throw new UserNotFoundException("User with email " + email + " not found");
            return null;
        }
    }
    

    @Override
    public void update(Integer id, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = mapDtoToEntity(userDto);
            user.setUserId(id);
            userRepository.save(user);
            logger.info("Updating User ID: " + id + " with data: " + user.toString());
        } else {
            logger.warn("User with ID " + id + " not found for update.");
            // throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    @Override
    public void delete(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            logger.info("Deleted User with ID: " + id);
        } else {
            logger.warn("User with ID " + id + " not found for deletion.");
            // throw new UserNotFoundException("User with ID " + id + " not found");
        }
    }

    private User mapDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(userDto.getPasswordHash());
        user.setNickname(userDto.getNickname());
        user.setProfilePhoto(userDto.getProfilePhoto());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());
        return user;
    }
}
