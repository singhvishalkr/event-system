package com.notification.eventsystem.Implementation;

import com.notification.eventsystem.Interface.EventService;
import com.notification.eventsystem.dto.EventRequest;
import com.notification.eventsystem.processor.EventProcessorManager;
import com.notification.eventsystem.model.EventWrapper;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventProcessorManager processorManager;

    @Override
    public String handleEvent(EventRequest request) {
        String eventId = UUID.randomUUID().toString();
        EventWrapper wrapper = new EventWrapper(
            eventId,
            request.getEventType(),
            request.getPayload(),
            request.getCallbackUrl(),
            Instant.now()
        );

        processorManager.enqueue(wrapper);
        return eventId;
    }
}