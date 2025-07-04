package com.notification.eventsystem.controller;

import com.notification.eventsystem.Interface.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventService eventService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public EventService eventService() {
            return Mockito.mock(EventService.class);
        }
    }

    @Test
    void testValidPostRequest() throws Exception {
        when(eventService.handleEvent(any())).thenReturn("e123");

        String body = """
            {
              "eventType": "EMAIL",
              "callbackUrl": "http://callback.com",
              "payload": {
                "recipient": "test@example.com",
                "message": "Hello"
              }
            }
        """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventId").value("e123"));
    }

    @Test
    void testInvalidPostRequest_missingPayload() throws Exception {
        String body = """
            {
              "eventType": "EMAIL",
              "callbackUrl": "http://callback.com"
            }
        """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
    @Test
    void testInvalidEventType() throws Exception {
        String body = """
        {
          "eventType": "INVALID_TYPE",
          "callbackUrl": "http://callback.com",
          "payload": {
            "recipient": "test@example.com",
            "message": "Hello"
          }
        }
    """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMissingEventType() throws Exception {
        String body = """
        {
          "callbackUrl": "http://callback.com",
          "payload": {
            "recipient": "test@example.com",
            "message": "Hello"
          }
        }
    """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}