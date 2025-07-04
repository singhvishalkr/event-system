package com.notification.eventsystem.model;

import lombok.*;
import java.time.Instant;
import java.util.Map;

@Data
@AllArgsConstructor
public class EventWrapper {
    private String eventId;
    private EventType eventType;
    private Map<String, Object> payload;
    private String callbackUrl;
    private Instant receivedAt;
}
