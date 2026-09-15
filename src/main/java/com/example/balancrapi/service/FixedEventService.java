package com.example.balancrapi.service;

import com.example.balancrapi.dto.FixedEventRequestDTO;
import com.example.balancrapi.dto.FixedEventResponseDTO;
import com.example.balancrapi.mapper.FixedEventMapper;
import com.example.balancrapi.model.FixedEvent;
import com.example.balancrapi.model.User;
import com.example.balancrapi.repository.FixedEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Business logic for FixedEvents. The controller stays thin and delegates
 * here; this layer owns the "what happens" (persist, scope to the owner) while
 * the mapper owns "how entity/DTO translate into each other".
 */
@Service
public class FixedEventService {

    private final FixedEventRepository fixedEventRepository;
    private final FixedEventMapper fixedEventMapper;

    public FixedEventService(FixedEventRepository fixedEventRepository, FixedEventMapper fixedEventMapper) {
        this.fixedEventRepository = fixedEventRepository;
        this.fixedEventMapper = fixedEventMapper;
    }

    /**
     * Creates a FixedEvent owned by {@code owner} from the validated request DTO.
     */
    public FixedEventResponseDTO createFixedEvent(FixedEventRequestDTO requestDTO, User owner) {
        FixedEvent event = fixedEventMapper.toEntity(requestDTO, owner);
        FixedEvent saved = fixedEventRepository.save(event);
        return fixedEventMapper.toResponseDTO(saved);
    }

    /**
     * Returns every FixedEvent belonging to the given user.
     */
    public List<FixedEventResponseDTO> getFixedEventsForUser(Long userId) {
        return fixedEventRepository.findByUserId(userId)
                .stream()
                .map(fixedEventMapper::toResponseDTO)
                .toList();
    }
}
