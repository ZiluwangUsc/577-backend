package com.tripwise.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.UserRegisterDto;
import com.tripwise.backend.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    IUserService userService;

    // Create
    @PostMapping("/register")
    public String create(@RequestBody UserRegisterDto userRegisterDto) {
        userService.create(userRegisterDto);
        return "Success!";
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
