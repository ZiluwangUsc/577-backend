package com.tripwise.backend.dto.response.travel;

public class ItineraryUpdateResponseDto {
    private String message;
    public ItineraryUpdateResponseDto(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
