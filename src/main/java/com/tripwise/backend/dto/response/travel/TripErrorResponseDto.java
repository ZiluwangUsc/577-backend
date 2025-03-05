package com.tripwise.backend.dto.response.travel;

public class TripErrorResponseDto {
    private int code;
    private String error;

    public TripErrorResponseDto() {
    }

    public TripErrorResponseDto(int code, String error) {
        this.code = code;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
