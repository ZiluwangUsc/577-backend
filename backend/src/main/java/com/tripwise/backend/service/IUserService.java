package com.tripwise.backend.service;

import com.tripwise.backend.pojo.User;
import com.tripwise.backend.pojo.dto.UserDto;

public interface IUserService {

    User add(UserDto user);
}
