package com.tripwise.backend.dto.response;

import com.tripwise.backend.constants.Constants;

/**
 * {
 * "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
 * "expiresIn": 3600,
 * "userId": "123456"
 * }
 * 
 * {
 * "error": "Invalid email or password"
 * }
 */
public class UserLoginResponseDto {
    private Integer userId;
    private String token;
    private Integer expiresIn = Constants.TOKEN_EXPIRE_TIME; // default expiration time in seconds

    public UserLoginResponseDto(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
