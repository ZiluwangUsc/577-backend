package com.tripwise.backend.service;

import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.request.user.ResetPasswordRequestDto;
import com.tripwise.backend.dto.request.user.TokenRefreshRequestDto;
import com.tripwise.backend.dto.request.user.UserInfoUpdateRequestDto;
import com.tripwise.backend.dto.request.user.UserLoginRequestDto;
import com.tripwise.backend.dto.request.user.UserLogoutRequestDto;
import com.tripwise.backend.dto.request.user.UserRegisterRequestDto;
import com.tripwise.backend.entity.User;

public interface IUserService {

    public User create(UserRegisterRequestDto userRegisterDto);

    public void delete(Integer id);

    public User login(UserLoginRequestDto userLoginDto);

    public User refreshToken(TokenRefreshRequestDto tokenRefreshDto);

    public User getUserByToken(String token);

    public User verifyUserByToken(String token, Integer userId);

    public User update(UserInfoUpdateRequestDto userInfoDto);

    public UserDto getUserById(Integer id);

    public UserDto getUserByEmail(String email);

    public String generateToken();

    public void logout(UserLogoutRequestDto userLogoutDto);

    

    /**
     * Precondition: userId != null and user exists
     * @param userId
     * @return
     */
    public boolean deleteToken(User user);

    public User requestPasswordReset(ResetPasswordRequestDto request);
    public boolean resetPassword(String passwordResetToken, String newPassword);
}
