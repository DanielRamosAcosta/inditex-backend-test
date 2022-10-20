package com.acidtango.inditex.backendtest.store.shared.infrastructure.events.tracker;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;

import java.util.ArrayList;
import java.util.List;

public class EventBusTracker implements EventBus {
    public List<DomainEvent> events = new ArrayList<>();

    @Override
    public void publish(List<DomainEvent> events) {
        this.events.addAll(events);
    }

    public Integer count() {
        return events.size();
    }

    public String lastEventName() {
        if (events.isEmpty()) {
            return "no events recorded";
        }

        return events.get(events.size() - 1).fullQualifiedEventName();
    }
}
