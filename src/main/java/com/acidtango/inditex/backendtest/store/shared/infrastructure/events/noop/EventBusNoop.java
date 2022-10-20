package com.acidtango.inditex.backendtest.store.shared.infrastructure.events.noop;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;

import java.util.List;

public class EventBusNoop implements EventBus {
    @Override
    public void publish(List<DomainEvent> events) {}
}
