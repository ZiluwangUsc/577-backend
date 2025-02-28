package com.tripwise.backend.dto.response.user;

import com.tripwise.backend.entity.User;

public class UserInfoResponseDto {
    private Integer userId;
    private String username;
    private String email;
    private String displayName;

    public UserInfoResponseDto() {
    }

    public UserInfoResponseDto(Integer userId, String username, String email, String displayName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.displayName = displayName;
    }

    public UserInfoResponseDto(User user) {
        this.setUserId(user.getUserId());
        this.setEmail(user.getEmail());
        this.setUsername(user.getUsername());
        this.setdisplayName(user.getDisplayName());
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public void setdisplayName(String displayName) {
        this.displayName = displayName;
    }
}
