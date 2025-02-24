package com.tripwise.backend.service;

import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.request.TokenRefreshDto;
import com.tripwise.backend.dto.request.UserLoginDto;
import com.tripwise.backend.dto.request.UserLogoutDto;
import com.tripwise.backend.dto.request.UserRegisterDto;
import com.tripwise.backend.entity.User;

public interface IUserService {

    public User create(UserRegisterDto userRegisterDto);

    public void delete(Integer id);

    public User login(UserLoginDto userLoginDto);

    public User refreshToken(TokenRefreshDto tokenRefreshDto);

    public User getUserByToken(String token);

    public void update(Integer id, UserDto userDto);

    public UserDto getUserById(Integer id);

    public UserDto getUserByEmail(String email);

    public String generateToken();

    public void logout(UserLogoutDto userLogoutDto);

    /**
     * Precondition: userId != null and user exists
     * @param userId
     * @return
     */
    public boolean deleteToken(User user);

    public boolean requestPasswordReset(String email);
    public boolean resetPassword(String resetToken, String newPassword);
}
