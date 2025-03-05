package com.tripwise.backend.dto.response.travel;

public class ItineraryCreateResponseDto {
    private String itineraryId;
    private String message;

    public ItineraryCreateResponseDto(String itineraryId, String message) {
        this.itineraryId = itineraryId;
        this.message = message;
    }

    // Getters and Setters
    public String getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(String itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
