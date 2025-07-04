package com.notification.eventsystem.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PushPayload {
    @NotBlank
    private String deviceId;

    @NotBlank
    private String message;
}
