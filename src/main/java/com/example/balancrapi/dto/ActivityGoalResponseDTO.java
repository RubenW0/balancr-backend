package com.example.balancrapi.dto;

import com.example.balancrapi.model.ActivityType;
import com.example.balancrapi.model.Intensity;

import java.time.LocalDateTime;

/**
 * Outbound payload for an ActivityGoal. Exposes only what the frontend is
 * allowed to see: the owning user is flattened to its id (userId) rather than
 * leaking the full User entity/relation. Carries the type field so the
 * frontend can distinguish TRAINING from STUDY goals in a shared list.
 */
public class ActivityGoalResponseDTO {

    private Long id;
    private ActivityType type;
    private int frequencyPerWeek;
    private int durationMinutes;
    private Intensity intensity;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ActivityGoalResponseDTO() {
    }

    public ActivityGoalResponseDTO(Long id, ActivityType type, int frequencyPerWeek, int durationMinutes,
                                    Intensity intensity, Long userId,
                                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.type = type;
        this.frequencyPerWeek = frequencyPerWeek;
        this.durationMinutes = durationMinutes;
        this.intensity = intensity;
        this.userId = userId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public int getFrequencyPerWeek() {
        return frequencyPerWeek;
    }

    public void setFrequencyPerWeek(int frequencyPerWeek) {
        this.frequencyPerWeek = frequencyPerWeek;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
