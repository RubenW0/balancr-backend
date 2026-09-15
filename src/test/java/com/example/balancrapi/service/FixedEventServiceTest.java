package com.example.balancrapi.service;

import com.example.balancrapi.dto.FixedEventRequestDTO;
import com.example.balancrapi.dto.FixedEventResponseDTO;
import com.example.balancrapi.mapper.FixedEventMapper;
import com.example.balancrapi.model.FixedEvent;
import com.example.balancrapi.model.User;
import com.example.balancrapi.repository.FixedEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FixedEventServiceTest {

    @Mock
    private FixedEventRepository fixedEventRepository;

    @Mock
    private FixedEventMapper fixedEventMapper;

    private FixedEventService fixedEventService;

    @BeforeEach
    void setUp() {
        fixedEventService = new FixedEventService(fixedEventRepository, fixedEventMapper);
    }

    @Test
    void createFixedEvent_mapsRequestToEntity_savesIt_andReturnsMappedResponse() {
        User owner = new User();
        owner.setId(7L);

        FixedEventRequestDTO request = new FixedEventRequestDTO();
        request.setTitle("Math class");
        request.setType("class");
        request.setStartTime(LocalDateTime.of(2026, 9, 15, 9, 0));
        request.setEndTime(LocalDateTime.of(2026, 9, 15, 10, 0));

        FixedEvent mappedEntity = new FixedEvent();
        FixedEvent savedEntity = new FixedEvent();
        savedEntity.setId(1L);
        FixedEventResponseDTO expectedResponse = new FixedEventResponseDTO(
                1L, "Math class", "class", request.getStartTime(), request.getEndTime(),
                false, 7L, null, null);

        when(fixedEventMapper.toEntity(request, owner)).thenReturn(mappedEntity);
        when(fixedEventRepository.save(mappedEntity)).thenReturn(savedEntity);
        when(fixedEventMapper.toResponseDTO(savedEntity)).thenReturn(expectedResponse);

        FixedEventResponseDTO result = fixedEventService.createFixedEvent(request, owner);

        assertThat(result).isSameAs(expectedResponse);
        verify(fixedEventRepository).save(mappedEntity);
    }

    @Test
    void getFixedEventsForUser_mapsEachEventToResponseDTO() {
        FixedEvent event1 = new FixedEvent();
        FixedEvent event2 = new FixedEvent();
        FixedEventResponseDTO dto1 = new FixedEventResponseDTO(1L, "A", "class", null, null, false, 7L, null, null);
        FixedEventResponseDTO dto2 = new FixedEventResponseDTO(2L, "B", "exam", null, null, false, 7L, null, null);

        when(fixedEventRepository.findByUserId(7L)).thenReturn(List.of(event1, event2));
        when(fixedEventMapper.toResponseDTO(event1)).thenReturn(dto1);
        when(fixedEventMapper.toResponseDTO(event2)).thenReturn(dto2);

        List<FixedEventResponseDTO> result = fixedEventService.getFixedEventsForUser(7L);

        assertThat(result).containsExactly(dto1, dto2);
    }

    @Test
    void getFixedEventsForUser_returnsEmptyList_whenUserHasNoEvents() {
        when(fixedEventRepository.findByUserId(99L)).thenReturn(List.of());

        List<FixedEventResponseDTO> result = fixedEventService.getFixedEventsForUser(99L);

        assertThat(result).isEmpty();
        verifyNoInteractions(fixedEventMapper);
    }
}
