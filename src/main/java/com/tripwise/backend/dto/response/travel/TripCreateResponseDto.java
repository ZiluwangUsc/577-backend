package com.tripwise.backend.dto.response.trip;

/**
 * Returned when a trip is successfully created.
 *
 * Example JSON:
 * {
 *   "code": 201,
 *   "activityId": "trip_4"
 * }
 */
public class TripCreateResponseDto {

    private int code;
    private Integer activityId;

    public TripCreateResponseDto() {
    }

    public TripCreateResponseDto(int code, Integer activityId) {
        this.code = code;
        this.activityId = activityId;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
}
