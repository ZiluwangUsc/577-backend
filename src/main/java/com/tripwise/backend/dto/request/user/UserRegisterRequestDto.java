package com.tripwise.backend.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * A DTO sent from client to request to create a user
 * 
 * Request body:
 * {
 * "username": "john_doe",
 * "email": "john@example.com",
 * "password": "securepassword123"
 * }
 */
public class UserRegisterRequestDto {
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String password;
    private String displayName;
    private String securityQuestion;
    private String securityAnswer;

    public UserRegisterRequestDto() {
    }

    public UserRegisterRequestDto(String username, String email, String password, String displayName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
    }

    public UserRegisterRequestDto(String username, String email, String password, String displayName, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String toString() {
        return "UserRegisterDto{" +
                "username='" + this.username + '\'' +
                ", email='" + this.email + '\'' +
                ", rawPassword='" + this.password + '\'' +
                '}';
    }

}
