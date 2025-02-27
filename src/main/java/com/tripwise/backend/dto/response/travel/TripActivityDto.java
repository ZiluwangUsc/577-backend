package com.tripwise.backend.dto.response.trip;

public class TripActivityDto {
    private String groupId;      // The Trip ID, e.g. "123"
    private String name;         // Trip name
    private String description;  // Trip description
    private String startDate;    // "2025-06-10"
    private String endDate;      // "2025-06-12"

    public TripActivityDto() {
    }

    public TripActivityDto(String groupId, String name, String description, String startDate, String endDate) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
}
