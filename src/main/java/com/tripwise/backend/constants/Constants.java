package com.tripwise.backend.constants;

public final class Constants {
    public static final Integer TOKEN_EXPIRE_TIME = 259200; // unit: seconds, equivalent to 30 days
    public static final String LOG_OUT_OK = "User logged out successfully";
    public static final String REGISTER_OK = "User registered successfully";
    public static final String REGISTER_EXISTED = "User already exists";
    public static final String INVALID_USER_CREDENTIAL = "Invalid email or password";
    public static final String LOGIN_OK = "User logged in";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String TOKEN_REFRESHED = "Refresh token successfully";
    public static final String USER_INFO_UPDATED = "User information updated";
    public static final String VERIFICATION_FAILED = "User token invalid";
    public static final String SEND_SECURITY_QUESTION = "Password reset question sent";
    public static final String WRONG_SECURITY_ANS = "Verification failed. The answer is incorrect.";
    public static final String CORRECT_SECURITY_ANS = "Verification passed. The answer is correct.";
    public static final String PASSWORD_RESET_OK = "password reset successfully";
}