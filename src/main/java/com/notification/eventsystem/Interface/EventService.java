package com.notification.eventsystem.Interface;

import com.notification.eventsystem.dto.EventRequest;

public interface EventService {
    String handleEvent(EventRequest request);
}
