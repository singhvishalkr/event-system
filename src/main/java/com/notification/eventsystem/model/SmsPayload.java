package com.notification.eventsystem.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SmsPayload {
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String message;
}
