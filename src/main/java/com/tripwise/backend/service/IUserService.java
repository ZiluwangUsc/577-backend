package com.tripwise.backend.service;

import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.UserRegisterDto;
import com.tripwise.backend.entity.User;

public interface IUserService {

    public User create(UserRegisterDto userRegisterDto);

    public void delete(Integer id);

    public void update(Integer id, UserDto userDto);

    public UserDto getUserById(Integer id);

    public UserDto getUserByEmail(String email);
}
