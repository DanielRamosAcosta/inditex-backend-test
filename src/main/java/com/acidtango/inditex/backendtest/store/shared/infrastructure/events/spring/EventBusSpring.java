package com.acidtango.inditex.backendtest.store.shared.infrastructure.events.spring;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventBusSpring implements EventBus {
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventBusSpring(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(List<DomainEvent> events) {
        for (DomainEvent domainEvent : events) {
            applicationEventPublisher.publishEvent(new GenericApplicationEvent<>(this, domainEvent));
        }
    }
}
