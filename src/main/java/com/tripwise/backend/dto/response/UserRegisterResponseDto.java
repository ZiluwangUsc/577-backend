package com.tripwise.backend.dto.response;

import com.tripwise.backend.constants.Constants;

/**
 * success
 * {
 *      "message": "User registered successfully",
 *      "userId": "123456",
 *      "token": "[UUID]"
 * }
 * 
 * error
 * {
 *      "message": "User registered successfully",
 *      "userId": null,
 *      "token": null
 * }
 */
public class UserRegisterResponseDto {
    private String message;
    private Integer userId;
    private String token;

    public UserRegisterResponseDto() {
        this.message = Constants.REGISTER_EXISTED;
    }

    public UserRegisterResponseDto(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
        this.message = Constants.REGISTER_OK;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
