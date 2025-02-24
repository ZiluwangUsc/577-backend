package com.tripwise.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tripwise.backend.controller.UserController;
import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.request.UserRegisterDto;
import com.tripwise.backend.entity.User;
import com.tripwise.backend.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public User create(UserRegisterDto userRegisterDto) {
        logger.info("Adding User: " + userRegisterDto.toString());
        User user = mapRegisterDtoToUser(userRegisterDto);
        logger.info("Adding User: " + user.toString());
        return userRepository.save(user);
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
            User user = mapUserDtoToUser(userDto);
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

    private User mapUserDtoToUser(UserDto userDto) {
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

    private User mapRegisterDtoToUser(UserRegisterDto dto) {
        User user = new User();

        user.setEmail(dto.getEmail());
        String hashedPassword = generateMD5Hash(dto.getPassword());
        user.setPasswordHash(hashedPassword);
        
        return user;
    }

    public static String generateMD5Hash(String input) {
        try {
            // Create an instance of the MD5 MessageDigest
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the hash as bytes
            byte[] hashBytes = md.digest(input.getBytes());

            // Convert the byte array into a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}
