package com.acidtango.inditex.backendtest.shared.domain;

import java.util.List;

public interface EventBus {
    void publish(final List<DomainEvent> events);
}
