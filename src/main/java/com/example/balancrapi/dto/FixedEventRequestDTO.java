package com.example.balancrapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Inbound payload for creating a FixedEvent. Only contains fields the client is
 * allowed to set - notably no id, no user (taken from the authenticated
 * principal) and no createdAt/updatedAt (server-managed). Validation lives here
 * rather than on the entity, since request-shape rules and persistence
 * constraints are different concerns.
 */
public class FixedEventRequestDTO {

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "type is required")
    private String type;

    @NotNull(message = "startTime is required")
    private LocalDateTime startTime;

    @NotNull(message = "endTime is required")
    private LocalDateTime endTime;

    @JsonProperty("isMovable")
    private boolean isMovable = false;

    public FixedEventRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    @JsonProperty("isMovable")
    public boolean isMovable() {
        return isMovable;
    }

    public void setMovable(boolean movable) {
        isMovable = movable;
    }
}
