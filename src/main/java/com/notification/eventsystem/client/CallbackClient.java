package com.notification.eventsystem.client;


import com.notification.eventsystem.dto.CallbackPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class CallbackClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void notifyClient(String callbackUrl, CallbackPayload payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CallbackPayload> request = new HttpEntity<>(payload, headers);
            restTemplate.postForEntity(callbackUrl, request, Void.class);

            log.info("✅ Callback sent to {}", callbackUrl);
        } catch (Exception ex) {
            log.error("❌ Callback failed to {}", callbackUrl, ex);
        }
    }
}