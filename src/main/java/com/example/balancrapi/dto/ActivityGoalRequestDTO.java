package com.example.balancrapi.dto;

import com.example.balancrapi.model.ActivityType;
import com.example.balancrapi.model.Intensity;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Inbound payload for creating an ActivityGoal. Only contains fields the
 * client is allowed to set - notably no id, no user (taken from the
 * authenticated principal) and no createdAt/updatedAt (server-managed).
 * <p>
 * intensity is only required when type is TRAINING; a plain @NotNull on the
 * field can't express that since it doesn't know about type, so that rule is
 * enforced by the class-level {@link #isIntensityValidForType()} check below.
 */
public class ActivityGoalRequestDTO {

    @NotNull(message = "type is required")
    private ActivityType type;

    @NotNull(message = "frequencyPerWeek is required")
    @Min(value = 1, message = "frequencyPerWeek must be at least 1")
    private Integer frequencyPerWeek;

    @NotNull(message = "durationMinutes is required")
    @Min(value = 1, message = "durationMinutes must be at least 1")
    private Integer durationMinutes;

    private Intensity intensity;

    public ActivityGoalRequestDTO() {
    }

    @AssertTrue(message = "intensity is required when type is TRAINING")
    public boolean isIntensityValidForType() {
        if (type == ActivityType.TRAINING) {
            return intensity != null;
        }
        return true;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Integer getFrequencyPerWeek() {
        return frequencyPerWeek;
    }

    public void setFrequencyPerWeek(Integer frequencyPerWeek) {
        this.frequencyPerWeek = frequencyPerWeek;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }
}
