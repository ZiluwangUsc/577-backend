package com.tripwise.backend.service;

import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.request.user.TokenRefreshDto;
import com.tripwise.backend.dto.request.user.UserLoginDto;
import com.tripwise.backend.dto.request.user.UserLogoutDto;
import com.tripwise.backend.dto.request.user.UserRegisterDto;
import com.tripwise.backend.entity.User;

public interface IUserService {

    public User create(UserRegisterDto userRegisterDto);

    public void delete(Integer id);

    public User login(UserLoginDto userLoginDto);

    public User refreshToken(TokenRefreshDto tokenRefreshDto);

    public User getUserByToken(String token);

    public boolean verifyUserByToken(String token, Integer userId);

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

    public String requestPasswordReset(String email);
    public boolean resetPassword(String resetToken, String newPassword);
}
