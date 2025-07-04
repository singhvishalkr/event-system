package com.notification.eventsystem.controller;

import com.notification.eventsystem.Interface.EventService;
import com.notification.eventsystem.dto.EventRequest;
import com.notification.eventsystem.dto.EventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> receiveEvent(@Valid @RequestBody EventRequest request) {
        String eventId = eventService.handleEvent(request);
        return ResponseEntity.ok(new EventResponse(eventId, "Event accepted for processing."));
    }
}
