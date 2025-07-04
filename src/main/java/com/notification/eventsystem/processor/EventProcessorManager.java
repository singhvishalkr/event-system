package com.notification.eventsystem.processor;

import com.notification.eventsystem.client.CallbackClient;
import com.notification.eventsystem.dto.CallbackPayload;
import com.notification.eventsystem.model.EventType;
import com.notification.eventsystem.model.EventWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.*;

@Component
public class EventProcessorManager {
    @Autowired
    private CallbackClient callbackClient;
    private final BlockingQueue<EventWrapper> emailQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<EventWrapper> smsQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<EventWrapper> pushQueue = new LinkedBlockingQueue<>();

    private final ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService smsExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService pushExecutor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void startProcessors() {
        emailExecutor.submit(() -> processQueue(emailQueue, 5000, EventType.EMAIL));
        smsExecutor.submit(() -> processQueue(smsQueue, 3000, EventType.SMS));
        pushExecutor.submit(() -> processQueue(pushQueue, 2000, EventType.PUSH));
    }

    private void processQueue(BlockingQueue<EventWrapper> queue, int delayMs, EventType type) {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                EventWrapper event = queue.take(); // FIFO
                Thread.sleep(delayMs); // simulate processing
                boolean success = Math.random() > 0.1;

                CallbackPayload.CallbackPayloadBuilder builder = CallbackPayload.builder()
                    .eventId(event.getEventId())
                    .eventType(type.name())
                    .processedAt(java.time.Instant.now());

                if (success) {
                    builder.status("COMPLETED");
                } else {
                    builder.status("FAILED")
                           .errorMessage("Simulated processing failure");
                }

                callbackClient.notifyClient(event.getCallbackUrl(), builder.build());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void enqueue(EventWrapper event) {
        switch (event.getEventType()) {
            case EMAIL -> emailQueue.offer(event);
            case SMS -> smsQueue.offer(event);
            case PUSH -> pushQueue.offer(event);
        }
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("🔻 Shutdown initiated...");

        emailExecutor.shutdown();
        smsExecutor.shutdown();
        pushExecutor.shutdown();

        try {
            if (!emailExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Email executor did not terminate.");
            }
            if (!smsExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("SMS executor did not terminate.");
            }
            if (!pushExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
                System.err.println("Push executor did not terminate.");
            }
        } catch (InterruptedException e) {
            System.err.println("Graceful shutdown interrupted.");
            Thread.currentThread().interrupt();
        }

        System.out.println("✅ Graceful shutdown complete.");
    }
}
