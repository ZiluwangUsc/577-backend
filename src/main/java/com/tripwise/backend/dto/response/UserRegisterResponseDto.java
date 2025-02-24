package com.tripwise.backend.dto.response;

/**
 * {
 * "message": "User registered successfully",
 * "userId": "123456",
 * "token": "[UUID]"
 * }
 */
public class UserRegisterResponseDto {
    private String message = "User registered successfully";
    private Integer userId;
    private String token;

    public UserRegisterResponseDto() {
    }

    public UserRegisterResponseDto(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
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
