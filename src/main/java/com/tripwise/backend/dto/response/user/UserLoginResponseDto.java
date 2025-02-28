package com.tripwise.backend.dto.response.user;

import java.time.LocalDateTime;

import com.tripwise.backend.constants.Constants;
import com.tripwise.backend.entity.User;

/**
 * {
 * "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
 * "expiresIn": 3600,
 * "userId": "123456"
 * }
 * 
 * error:
 * {
 * "token": null,
 * "userId": null,
 * "expiresIn": null,
 * "error": "Invalid email or password"
 * }
 */
public class UserLoginResponseDto {
    private Integer userId;
    private String token;
    private Integer expiresIn;
    private LocalDateTime tokenCreatedAt;
    private String message;
    private String username;
    private String email;
    private String displayName;

    /**
     * Default constructor for failure case
     */
    public UserLoginResponseDto() {
        this.expiresIn = null;
        this.message = Constants.INVALID_USER_CREDENTIAL;
    }

    /**
     * Constructor for success case
     */
    public UserLoginResponseDto(User user, String message) {
        this.userId = user.getUserId();
        this.token = user.getToken();
        this.expiresIn = Constants.TOKEN_EXPIRE_TIME;
        this.message = message;
        this.displayName = user.getDisplayName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.tokenCreatedAt = user.getTokenCreatedAt();
    }

    /**
     * Constructor for failure case
     */
    public UserLoginResponseDto(String message) {
        this.message = message;
        this.expiresIn = null;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDateTime getTokenCreatedAt() {
        return tokenCreatedAt;
    }

    public void setTokenCreatedAt(LocalDateTime tokenCreatedAt) {
        this.tokenCreatedAt = tokenCreatedAt;
    }
}
