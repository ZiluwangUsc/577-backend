package com.tripwise.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripwise.backend.pojo.dto.UserDto;
import com.tripwise.backend.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/testuser")
public class UserController {
    @Autowired
    IUserService userService;

    // create
    @PostMapping
    public String add(@RequestBody UserDto user) {
        userService.add(user);

        return "Success!";
    }

    // read

    // update

    // delete
}
