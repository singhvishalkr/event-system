package com.notification.eventsystem.dto;

import com.notification.eventsystem.model.EventType;

import java.util.Map;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventRequest {
    @NotBlank
    private String callbackUrl;

    @NotNull
    private EventType eventType;

    @NotNull
    private Map<String, Object> payload;
}
