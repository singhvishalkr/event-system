package com.notification.eventsystem.client;

import com.notification.eventsystem.dto.CallbackPayload;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class CallbackClientTest {

    private final CallbackClient callbackClient = new CallbackClient();

    @Test
    void testNotifyClient_shouldNotThrow() {
        CallbackPayload payload = CallbackPayload.builder()
                .eventId("e123")
                .eventType("EMAIL")
                .status("COMPLETED")
                .processedAt(Instant.now())
                .build();

        assertDoesNotThrow(() ->
                callbackClient.notifyClient("http://localhost:9999/callback-test", payload)
        );
    }
}