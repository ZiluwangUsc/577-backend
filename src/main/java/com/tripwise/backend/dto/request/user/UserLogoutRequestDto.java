package com.tripwise.backend.dto.request.user;

/**
 * {
 * "userId": "123456"
 * }
 * 
 */
public class UserLogoutRequestDto {
    private Integer userId;

    public UserLogoutRequestDto(String userId) {
        this.userId = Integer.valueOf(userId);
    }

    // public UserLogoutDto(Integer userId) {
    //     this.userId = userId;
    // }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserId(String userId) {
        this.userId = Integer.valueOf(userId);
    }

    public String toString() {
        return "UserLogoutDto{" +
                "userId=" + this.userId +
                '}';
    }
}
