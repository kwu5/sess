package com.example.sess.dto;

import java.time.LocalDateTime;

public class TaskUpdateRequest {
    // Including only fields that can be updated. Excluding ownerId and taskId as
    // these should not change post creation.
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long clientId; // Assuming it can be updated. Adjust according to your business logic.
    private String location;
    private String type;
    private String description;

    // Constructors
    public TaskUpdateRequest() {
    }

    public TaskUpdateRequest(LocalDateTime startTime, LocalDateTime endTime, Long clientId, String location,
            String type, String description) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.clientId = clientId;
        this.location = location;
        this.type = type;
        this.description = description;
    }

    // Getters and Setters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Override equals and hashCode if necessary based on your requirements
}
