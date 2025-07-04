package com.notification.eventsystem.Implementation;

import com.notification.eventsystem.Interface.EventService;
import com.notification.eventsystem.dto.EventRequest;
import com.notification.eventsystem.model.EventType;
import com.notification.eventsystem.model.EventWrapper;
import com.notification.eventsystem.processor.EventProcessorManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventProcessorManager processorManager;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void testHandleEvent_shouldEnqueueEvent() {
        EventRequest request = new EventRequest();
        request.setEventType(EventType.EMAIL);
        request.setCallbackUrl("http://callback.com");
        request.setPayload(Map.of("recipient", "user@example.com", "message", "Hello"));

        String eventId = eventService.handleEvent(request);

        assertNotNull(eventId);
        verify(processorManager, times(1)).enqueue(any(EventWrapper.class));
    }
}