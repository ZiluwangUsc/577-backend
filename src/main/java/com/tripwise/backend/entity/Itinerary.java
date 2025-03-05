package com.tripwise.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "itineraries")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itinerary_id")
    private Integer itineraryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private String time;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    public Itinerary() {
    }

    public Itinerary(Trip trip, LocalDate date, String time, String title, String location, String description) {
        this.trip = trip;
        this.date = date;
        this.time = time;
        this.title = title;
        this.location = location;
        this.description = description;
    }

    public Integer getItineraryId() {
        return itineraryId;
    }

    public void setItineraryId(Integer itineraryId) {
        this.itineraryId = itineraryId;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


