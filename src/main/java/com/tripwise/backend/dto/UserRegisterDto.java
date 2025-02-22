package com.tripwise.backend.dto;

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
public class UserRegisterDto {
    private String username;
    private String email;
    private String password;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String toString() {
        return "UserRegisterDto{" +
                "username='" + this.username + '\'' +
                ", email='" + this.email + '\'' +
                ", rawPassword='" + this.password + '\'' +
                '}';
    }

}
