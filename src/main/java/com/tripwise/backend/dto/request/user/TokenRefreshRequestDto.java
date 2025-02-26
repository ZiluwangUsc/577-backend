package com.tripwise.backend.dto.request.user;

/**
 * {
 * "refreshToken": "your_refresh_token_here"
 * }
 */
public class TokenRefreshRequestDto {
    private String refreshToken; // old token

    public TokenRefreshRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
