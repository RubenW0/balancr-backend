package com.example.balancrapi.service;

import com.example.balancrapi.dto.ActivityGoalRequestDTO;
import com.example.balancrapi.dto.ActivityGoalResponseDTO;
import com.example.balancrapi.mapper.ActivityGoalMapper;
import com.example.balancrapi.model.ActivityGoal;
import com.example.balancrapi.model.User;
import com.example.balancrapi.repository.ActivityGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic for ActivityGoals. The controller stays thin and delegates
 * here; this layer owns the "what happens" (persist, scope to the owner) while
 * the mapper owns "how entity/DTO translate into each other".
 */
@Service
public class ActivityGoalService {

    private final ActivityGoalRepository activityGoalRepository;
    private final ActivityGoalMapper activityGoalMapper;

    public ActivityGoalService(ActivityGoalRepository activityGoalRepository, ActivityGoalMapper activityGoalMapper) {
        this.activityGoalRepository = activityGoalRepository;
        this.activityGoalMapper = activityGoalMapper;
    }

    /**
     * Creates an ActivityGoal owned by {@code owner} from the validated request DTO.
     */
    public ActivityGoalResponseDTO createActivityGoal(ActivityGoalRequestDTO requestDTO, User owner) {
        ActivityGoal goal = activityGoalMapper.toEntity(requestDTO, owner);
        ActivityGoal saved = activityGoalRepository.save(goal);
        return activityGoalMapper.toResponseDTO(saved);
    }

    /**
     * Returns every ActivityGoal belonging to the given user, training and
     * study goals mixed together.
     */
    public List<ActivityGoalResponseDTO> getActivityGoalsForUser(Long userId) {
        return activityGoalRepository.findByUserId(userId)
                .stream()
                .map(activityGoalMapper::toResponseDTO)
                .toList();
    }
}
