package com.tripwise.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "username", nullable = true, length = 255)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "profile_photo", length = 255)
    private String profilePhoto;

    @Column(name = "token", length = 255)
    private String token;

    @Column(name = "user_created_at", nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "user_updated_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "token_created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime tokenCreatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // Set current timestamp
        this.updatedAt = LocalDateTime.now(); // Set current timestamp
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // Update the timestamp
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getdisplayName() {
        return displayName;
    }

    public void setdisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.setTokenCreatedAt(LocalDateTime.now()); // Set current timestamp
    }

    public LocalDateTime getTokenCreatedAt() {
        return tokenCreatedAt;
    }

    public void setTokenCreatedAt(LocalDateTime tokenCreatedAt) {
        this.tokenCreatedAt = tokenCreatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                " email=" + email +
                ", passwordHash=" + passwordHash +
                ", displayName=" + displayName +
                '}';
    }


    private String resetToken;  // 存储 Token
    private LocalDateTime tokenExpiry; // Token 过期时间

    // Getters & Setters
    public String getResetToken() { return resetToken; }
    public void setResetToken(String resetToken) { this.resetToken = resetToken; }
    
    public LocalDateTime getTokenExpiry() { return tokenExpiry; }
    public void setTokenExpiry(LocalDateTime tokenExpiry) { this.tokenExpiry = tokenExpiry; }
    
}