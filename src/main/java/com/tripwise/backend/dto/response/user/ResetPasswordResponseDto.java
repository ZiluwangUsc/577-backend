package com.tripwise.backend.dto.response.user;

import com.tripwise.backend.entity.User;

public class ResetPasswordResponseDto {
    private String message;
    private String securityQuestion;
    private String passwordResetToken;

    public ResetPasswordResponseDto(User user, String message) {
        this.message = message;
        this.securityQuestion = user.getSecurityQuestion();
        this.passwordResetToken = user.getPasswordResetToken();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }
}
