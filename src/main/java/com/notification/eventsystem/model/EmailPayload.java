package com.notification.eventsystem.model;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class EmailPayload {
    @NotBlank
    private String recipient;

    @NotBlank
    private String message;
}
