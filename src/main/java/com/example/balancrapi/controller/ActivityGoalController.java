package com.example.balancrapi.controller;

import com.example.balancrapi.dto.ActivityGoalRequestDTO;
import com.example.balancrapi.dto.ActivityGoalResponseDTO;
import com.example.balancrapi.model.User;
import com.example.balancrapi.service.ActivityGoalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/activity-goals")
public class ActivityGoalController {

    private final ActivityGoalService activityGoalService;

    public ActivityGoalController(ActivityGoalService activityGoalService) {
        this.activityGoalService = activityGoalService;
    }

    @PostMapping
    public ResponseEntity<ActivityGoalResponseDTO> createActivityGoal(
            @Valid @RequestBody ActivityGoalRequestDTO requestDTO,
            @AuthenticationPrincipal User currentUser) {
        ActivityGoalResponseDTO created = activityGoalService.createActivityGoal(requestDTO, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ActivityGoalResponseDTO>> getActivityGoals(
            @AuthenticationPrincipal User currentUser) {
        List<ActivityGoalResponseDTO> goals = activityGoalService.getActivityGoalsForUser(currentUser.getId());
        return ResponseEntity.ok(goals);
    }
}
