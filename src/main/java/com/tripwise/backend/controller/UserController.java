package com.tripwise.backend.controller;

import com.tripwise.backend.constants.Constants;
import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.request.TokenRefreshDto;
import com.tripwise.backend.dto.request.UserLoginDto;
import com.tripwise.backend.dto.request.UserLogoutDto;
import com.tripwise.backend.dto.request.UserRegisterDto;
import com.tripwise.backend.dto.response.Message;
import com.tripwise.backend.dto.response.UserLoginResponseDto;
import com.tripwise.backend.dto.response.UserRegisterResponseDto;
import com.tripwise.backend.service.IUserService;
import com.tripwise.backend.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    IUserService userService;

    // Create
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> create(@RequestBody UserRegisterDto userRegisterDto) {
        User newUser = userService.create(userRegisterDto);
        UserRegisterResponseDto response = new UserRegisterResponseDto(newUser.getUserId(), newUser.getToken());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        User user = userService.login(userLoginDto);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserLoginResponseDto response = new UserLoginResponseDto(user.getUserId(), user.getToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserLoginResponseDto> refreshToken(@RequestBody TokenRefreshDto tokenRefreshDto) {
        User user = userService.refreshToken(tokenRefreshDto);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserLoginResponseDto response = new UserLoginResponseDto(user.getUserId(), user.getToken());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Message> logout(@RequestBody UserLogoutDto userLogoutDto) {
        userService.logout(userLogoutDto);
        Message response = new Message(Constants.LOG_OUT_OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Read user by email
    @GetMapping("/{email}")
    public UserDto getUserById(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // Update user
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody UserDto updatedUser) {
        userService.update(id, updatedUser);
        return "User updated successfully!";
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return "User deleted successfully!";
    }
}
