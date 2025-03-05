package com.tripwise.backend.dto.request.travel;

/**
 * Example JSON:
 * {
 *   "userId": "789",
 *   "name": "3-day Trip to New York",
 *   "description": "Exploring NYC with friends!",
 *   "startDate": "2025-06-10",
 *   "endDate": "2025-06-12",
 *   "token": "i-am-a-token"
 * }
 */
public class TripCreateRequestDto {
    private String userId;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private String token;

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
