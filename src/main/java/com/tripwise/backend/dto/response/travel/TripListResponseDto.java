package com.tripwise.backend.dto.response.trip;

import java.util.List;

/**
 * Returned when fetching all trips for a given user.
 *
 * Example JSON:
 * {
 *   "code": 200,
 *   "userId": "789",
 *   "activities": [
 *     { ... },
 *     { ... }
 *   ]
 * }
 */
public class TripListResponseDto {

    private int code;
    private String userId;
    private List<TripActivityDto> activities;

    public TripListResponseDto() {
    }

    public TripListResponseDto(int code, String userId, List<TripActivityDto> activities) {
        this.code = code;
        this.userId = userId;
        this.activities = activities;
    }

    // Getters and Setters

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<TripActivityDto> getActivities() {
        return activities;
    }

    public void setActivities(List<TripActivityDto> activities) {
        this.activities = activities;
    }
}
