package com.example.balancrapi.mapper;

import com.example.balancrapi.dto.FixedEventRequestDTO;
import com.example.balancrapi.dto.FixedEventResponseDTO;
import com.example.balancrapi.model.FixedEvent;
import com.example.balancrapi.model.User;
import org.springframework.stereotype.Component;

/**
 * All entity <-> DTO conversion for FixedEvent lives here, kept out of the
 * service layer so FixedEventService only deals with business logic.
 */
@Component
public class FixedEventMapper {

    /**
     * Builds a new (unsaved) FixedEvent from a validated request DTO and the
     * authenticated owner. id/createdAt/updatedAt are left for JPA to fill in.
     */
    public FixedEvent toEntity(FixedEventRequestDTO dto, User owner) {
        FixedEvent event = new FixedEvent();
        event.setTitle(dto.getTitle());
        event.setType(dto.getType());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setMovable(dto.isMovable());
        event.setUser(owner);
        return event;
    }

    public FixedEventResponseDTO toResponseDTO(FixedEvent event) {
        return new FixedEventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getType(),
                event.getStartTime(),
                event.getEndTime(),
                event.isMovable(),
                event.getUser() != null ? event.getUser().getId() : null,
                event.getCreatedAt(),
                event.getUpdatedAt()
        );
    }
}
