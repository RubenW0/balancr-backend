package com.example.balancrapi.service;

import com.example.balancrapi.dto.ActivityGoalRequestDTO;
import com.example.balancrapi.dto.ActivityGoalResponseDTO;
import com.example.balancrapi.mapper.ActivityGoalMapper;
import com.example.balancrapi.model.ActivityGoal;
import com.example.balancrapi.model.ActivityType;
import com.example.balancrapi.model.Intensity;
import com.example.balancrapi.model.User;
import com.example.balancrapi.repository.ActivityGoalRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityGoalServiceTest {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Mock
    private ActivityGoalRepository activityGoalRepository;

    @Mock
    private ActivityGoalMapper activityGoalMapper;

    private ActivityGoalService activityGoalService;

    @BeforeEach
    void setUp() {
        activityGoalService = new ActivityGoalService(activityGoalRepository, activityGoalMapper);
    }

    @Test
    void createActivityGoal_mapsRequestToEntity_savesIt_andReturnsMappedResponse() {
        User owner = new User();
        owner.setId(7L);

        ActivityGoalRequestDTO request = new ActivityGoalRequestDTO();
        request.setType(ActivityType.TRAINING);
        request.setFrequencyPerWeek(3);
        request.setDurationMinutes(60);
        request.setIntensity(Intensity.MEDIUM);

        ActivityGoal mappedEntity = new ActivityGoal();
        ActivityGoal savedEntity = new ActivityGoal();
        savedEntity.setId(1L);
        ActivityGoalResponseDTO expectedResponse = new ActivityGoalResponseDTO(
                1L, ActivityType.TRAINING, 3, 60, Intensity.MEDIUM, 7L, null, null);

        when(activityGoalMapper.toEntity(request, owner)).thenReturn(mappedEntity);
        when(activityGoalRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(activityGoalMapper.toResponseDTO(savedEntity)).thenReturn(expectedResponse);

        ActivityGoalResponseDTO result = activityGoalService.createActivityGoal(request, owner);

        assertThat(result).isSameAs(expectedResponse);
        verify(activityGoalRepository).save(mappedEntity);
    }

    @Test
    void getActivityGoalsForUser_mapsEachGoalToResponseDTO() {
        ActivityGoal trainingGoal = new ActivityGoal();
        ActivityGoal studyGoal = new ActivityGoal();
        ActivityGoalResponseDTO trainingDto = new ActivityGoalResponseDTO(
                1L, ActivityType.TRAINING, 3, 60, Intensity.HEAVY, 7L, null, null);
        ActivityGoalResponseDTO studyDto = new ActivityGoalResponseDTO(
                2L, ActivityType.STUDY, 5, 90, null, 7L, null, null);

        when(activityGoalRepository.findByUserId(7L)).thenReturn(List.of(trainingGoal, studyGoal));
        when(activityGoalMapper.toResponseDTO(trainingGoal)).thenReturn(trainingDto);
        when(activityGoalMapper.toResponseDTO(studyGoal)).thenReturn(studyDto);

        List<ActivityGoalResponseDTO> result = activityGoalService.getActivityGoalsForUser(7L);

        assertThat(result).containsExactly(trainingDto, studyDto);
    }

    @Test
    void getActivityGoalsForUser_returnsEmptyList_whenUserHasNoGoals() {
        when(activityGoalRepository.findByUserId(99L)).thenReturn(List.of());

        List<ActivityGoalResponseDTO> result = activityGoalService.getActivityGoalsForUser(99L);

        assertThat(result).isEmpty();
        verifyNoInteractions(activityGoalMapper);
    }

    @Test
    void requestDTO_isInvalid_whenTrainingGoalHasNoIntensity() {
        ActivityGoalRequestDTO request = new ActivityGoalRequestDTO();
        request.setType(ActivityType.TRAINING);
        request.setFrequencyPerWeek(3);
        request.setDurationMinutes(60);

        Set<ConstraintViolation<ActivityGoalRequestDTO>> violations = VALIDATOR.validate(request);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("intensity is required when type is TRAINING"));
    }

    @Test
    void requestDTO_isValid_whenStudyGoalHasNoIntensity() {
        ActivityGoalRequestDTO request = new ActivityGoalRequestDTO();
        request.setType(ActivityType.STUDY);
        request.setFrequencyPerWeek(5);
        request.setDurationMinutes(90);

        Set<ConstraintViolation<ActivityGoalRequestDTO>> violations = VALIDATOR.validate(request);

        assertThat(violations).isEmpty();
    }
}
