package com.tripwise.backend.service;

import java.util.List;

import com.tripwise.backend.pojo.User;
import com.tripwise.backend.pojo.dto.UserDto;

public interface IUserService {

    User add(UserDto user);

    public void delete(Integer id);

    public void update(Integer id, UserDto userDto);

    public UserDto getUserById(Integer id);

    public UserDto getUserByEmail(String email);

    public List<UserDto> getAllUsers();

}
