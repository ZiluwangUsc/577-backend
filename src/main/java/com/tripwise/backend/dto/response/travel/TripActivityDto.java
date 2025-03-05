package com.tripwise.backend.dto.response.travel;
import java.util.List;

public class TripActivityDto {
    private Integer groupId;      // The Trip ID, e.g. 123
    private String name;         // Trip name
    private String description;  // Trip description
    private String startDate;    // "2025-06-10"
    private String endDate;      // "2025-06-12"
    private List<Integer> participants;

    public TripActivityDto() {
    }

    public TripActivityDto(Integer groupId, String name, String description, String startDate, String endDate, List<Integer> participants ) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.participants = participants;

    }

    // Getters and Setters

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
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

    public List<Integer> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Integer> participants) {
        this.participants = participants;
    }
}
