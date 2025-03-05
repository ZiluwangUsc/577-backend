package com.tripwise.backend.dto.response.travel;

import java.time.LocalDate;

public class ItineraryResponseDto {
    private Integer itineraryId;
    private String title;
    private String description;
    private LocalDate date;
    private String time;
    private String location;

    public ItineraryResponseDto(){

    }


    public ItineraryResponseDto(Integer itineraryId, String title, String description, LocalDate date, String time, String location) {
        this.itineraryId = itineraryId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
