package com.acidtango.inditex.backendtest.shared.domain;

import com.acidtango.inditex.backendtest.store.shared.domain.DomainId;

import java.util.LinkedList;
import java.util.List;

public abstract class AggregateRoot {
    private List<DomainEvent> recordedDomainEvents = new LinkedList<>();

    final public List<DomainEvent> pullDomainEvents() {
        final var recordedDomainEvents = this.recordedDomainEvents;
        this.recordedDomainEvents = new LinkedList<>();

        return recordedDomainEvents;
    }

    final protected void record(DomainEvent event) {
        recordedDomainEvents.add(event);
    }
}
