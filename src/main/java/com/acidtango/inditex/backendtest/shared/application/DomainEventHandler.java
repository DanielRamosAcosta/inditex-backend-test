package com.acidtango.inditex.backendtest.shared.application;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;

public abstract class DomainEventHandler<T extends DomainEvent> {
    public abstract void handle(T domainEvent);
}
