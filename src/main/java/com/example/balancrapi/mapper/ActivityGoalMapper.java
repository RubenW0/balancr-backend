package com.example.balancrapi.mapper;

import com.example.balancrapi.dto.ActivityGoalRequestDTO;
import com.example.balancrapi.dto.ActivityGoalResponseDTO;
import com.example.balancrapi.model.ActivityGoal;
import com.example.balancrapi.model.ActivityType;
import com.example.balancrapi.model.User;
import org.springframework.stereotype.Component;

/**
 * All entity <-> DTO conversion for ActivityGoal lives here, kept out of the
 * service layer so ActivityGoalService only deals with business logic.
 */
@Component
public class ActivityGoalMapper {

    /**
     * Builds a new (unsaved) ActivityGoal from a validated request DTO and the
     * authenticated owner. id/createdAt/updatedAt are left for JPA to fill in.
     * intensity is dropped for non-TRAINING goals regardless of what the
     * request carried, so STUDY goals always persist without one.
     */
    public ActivityGoal toEntity(ActivityGoalRequestDTO dto, User owner) {
        ActivityGoal goal = new ActivityGoal();
        goal.setType(dto.getType());
        goal.setFrequencyPerWeek(dto.getFrequencyPerWeek());
        goal.setDurationMinutes(dto.getDurationMinutes());
        goal.setIntensity(dto.getType() == ActivityType.TRAINING ? dto.getIntensity() : null);
        goal.setUser(owner);
        return goal;
    }

    public ActivityGoalResponseDTO toResponseDTO(ActivityGoal goal) {
        return new ActivityGoalResponseDTO(
                goal.getId(),
                goal.getType(),
                goal.getFrequencyPerWeek(),
                goal.getDurationMinutes(),
                goal.getIntensity(),
                goal.getUser() != null ? goal.getUser().getId() : null,
                goal.getCreatedAt(),
                goal.getUpdatedAt()
        );
    }
}
