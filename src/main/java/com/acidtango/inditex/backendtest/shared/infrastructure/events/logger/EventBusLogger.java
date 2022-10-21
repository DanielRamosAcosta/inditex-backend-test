package com.acidtango.inditex.backendtest.shared.infrastructure.events.logger;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventBusLogger implements EventBus {
    @Override
    public void publish(List<DomainEvent> events) {
        for (DomainEvent domainEvent : events) {
            System.out.println("Event has been emitted: " + domainEvent.fullQualifiedEventName());
        }
    }
}
