package com.tripwise.backend.controller;

import com.tripwise.backend.constants.Constants;
import com.tripwise.backend.dto.UserDto;
import com.tripwise.backend.dto.request.user.TokenRefreshRequestDto;
import com.tripwise.backend.dto.request.user.UserInfoUpdateRequestDto;
import com.tripwise.backend.dto.request.user.UserLoginRequestDto;
import com.tripwise.backend.dto.request.user.UserLogoutRequestDto;
import com.tripwise.backend.dto.request.user.UserRegisterRequestDto;
import com.tripwise.backend.dto.response.MessageDto;
import com.tripwise.backend.dto.response.user.UserInfoResponseDto;
import com.tripwise.backend.dto.response.user.UserLoginResponseDto;
// import com.tripwise.backend.dto.response.user.UserRegisterResponseDto;
import com.tripwise.backend.service.IUserService;

import jakarta.validation.Valid;

import com.tripwise.backend.entity.User;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class UserController {
    @Autowired
    IUserService userService;

    // Create
    @PostMapping("/register")
    public ResponseEntity<UserLoginResponseDto> create(@Valid @RequestBody UserRegisterRequestDto userRegisterDto) {
        User newUser = userService.create(userRegisterDto);
        if (newUser == null) { // already exists
            return new ResponseEntity<>(new UserLoginResponseDto(Constants.REGISTER_EXISTED), HttpStatus.BAD_REQUEST);
        }
        
        UserLoginResponseDto response = new UserLoginResponseDto(newUser, Constants.REGISTER_OK);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto userLoginDto) {
        User user = userService.login(userLoginDto);
        if (user == null) {
            return new ResponseEntity<>(new UserLoginResponseDto(Constants.USER_NOT_FOUND), HttpStatus.UNAUTHORIZED);
        }
        if (user.getUsername() == null) {
            return new ResponseEntity<>(new UserLoginResponseDto(Constants.INVALID_USER_CREDENTIAL), HttpStatus.UNAUTHORIZED);
        }
        UserLoginResponseDto response = new UserLoginResponseDto(user, Constants.LOGIN_OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserLoginResponseDto> refreshToken(@RequestBody TokenRefreshRequestDto tokenRefreshDto) {
        User user = userService.refreshToken(tokenRefreshDto);
        if (user == null) {
            return new ResponseEntity<>(new UserLoginResponseDto(Constants.USER_NOT_FOUND), HttpStatus.UNAUTHORIZED);
        }
        UserLoginResponseDto response = new UserLoginResponseDto(user, Constants.TOKEN_REFRESHED);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageDto> logout(@RequestBody UserLogoutRequestDto userLogoutDto) {
        userService.logout(userLogoutDto);
        MessageDto response = new MessageDto(Constants.LOG_OUT_OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Read user by email
    @GetMapping("/{email}")
    public UserDto getUserById(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // Update user
    @PutMapping("/update")
    public ResponseEntity<UserLoginResponseDto> updateUser(@RequestBody UserInfoUpdateRequestDto updatedUser) {
        User user = userService.update(updatedUser);
        if (user == null) {
            return new ResponseEntity<>(new UserLoginResponseDto(Constants.USER_NOT_FOUND), HttpStatus.BAD_REQUEST);
        }
        UserLoginResponseDto response = new UserLoginResponseDto(user, Constants.USER_INFO_UPDATED);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.delete(id);
        return "User deleted successfully!";
    }

    @PostMapping("/password-reset-request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String passwordResetToken = userService.requestPasswordReset(email);

        if (passwordResetToken != null) {
            return ResponseEntity.ok(Map.of(
                "message", "Password reset link has been sent",
                "passwordResetToken", passwordResetToken  // 让前端直接拿到 `passwordResetToken`
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                "message", "Email not found"
            ));
        }
    }


    // 提交新密码
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        boolean success = userService.resetPassword(request.get("resetToken"), request.get("newPassword"));
        return success
                ? ResponseEntity.ok(Map.of("message", "Password has been reset successfully"))
                : ResponseEntity.badRequest().body(Map.of("message", "Invalid or expired token"));
    }

    // Get Current User API
    @GetMapping("/user")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(@RequestHeader("token") String token) {
        User user = userService.getUserByToken(token);
        if (user == null) {
            UserInfoResponseDto userInfo = new UserInfoResponseDto();
            return new ResponseEntity<>(userInfo, HttpStatus.UNAUTHORIZED);
        }
        UserInfoResponseDto userInfo = new UserInfoResponseDto(user);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
