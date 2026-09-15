package com.example.balancrapi.controller;

import com.example.balancrapi.dto.FixedEventRequestDTO;
import com.example.balancrapi.dto.FixedEventResponseDTO;
import com.example.balancrapi.model.User;
import com.example.balancrapi.service.FixedEventService;
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

/**
 * REST endpoints for FixedEvents. Contains no business logic itself - it only
 * validates the incoming request shape (@Valid) and delegates to
 * FixedEventService, translating the result into an HTTP response.
 * <p>
 * The authenticated user is taken from @AuthenticationPrincipal; who is
 * allowed to call these endpoints (role checks, authentication itself) is
 * configured separately in the Spring Security layer.
 */
@RestController
@RequestMapping("/api/fixed-events")
public class FixedEventController {

    private final FixedEventService fixedEventService;

    public FixedEventController(FixedEventService fixedEventService) {
        this.fixedEventService = fixedEventService;
    }

    @PostMapping
    public ResponseEntity<FixedEventResponseDTO> createFixedEvent(
            @Valid @RequestBody FixedEventRequestDTO requestDTO,
            @AuthenticationPrincipal User currentUser) {
        FixedEventResponseDTO created = fixedEventService.createFixedEvent(requestDTO, currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<FixedEventResponseDTO>> getFixedEvents(
            @AuthenticationPrincipal User currentUser) {
        List<FixedEventResponseDTO> events = fixedEventService.getFixedEventsForUser(currentUser.getId());
        return ResponseEntity.ok(events);
    }
}
