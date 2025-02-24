package com.tripwise.backend.dto.response;

import com.tripwise.backend.entity.User;

public class UserInfoDto {
    private Integer userId;
    private String username;
    private String email;

    public UserInfoDto(Integer userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public UserInfoDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getNickname();
        this.email = user.getEmail();
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
}
