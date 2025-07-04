package com.notification.eventsystem.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CallbackPayload {
    private String eventId;
    private String status;           // COMPLETED or FAILED
    private String eventType;
    private String errorMessage;     // nullable
    private Instant processedAt;
}