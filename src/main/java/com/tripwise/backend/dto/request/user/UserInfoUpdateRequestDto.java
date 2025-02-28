package com.tripwise.backend.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * {
 * "userId": "1",
 * "token": "the-token",
 * "username": "either-new-username-or-old-username",
 * "displayName": "either-new-or-old",
 * "email": "either-new-or-old"
 * "securityQuestion": "How are you?",
  "securityAnswer": "I'm fine"
 * }
 */
public class UserInfoUpdateRequestDto {
    private Integer userId;
    private String token;
    private String username;
    private String displayName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    private String securityQuestion;
    private String securityAnswer;

    public UserInfoUpdateRequestDto(Integer userId, String token, String username, String displayName, String email, String securityQuestion, String securityAnswer) {
        this.userId = userId;
        this.token = token;
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

}
