package com.tripwise.backend.dto.request;

/**
 * {
 * "refreshToken": "your_refresh_token_here"
 * }
 */
public class TokenRefreshDto {
    private String refreshToken; // old token

    public TokenRefreshDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
