package com.acidtango.inditex.backendtest.store.shared.infrastructure.events.spring;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import org.springframework.context.ApplicationEvent;

public class GenericApplicationEvent<T extends DomainEvent> extends ApplicationEvent {
    public final T event;

    public GenericApplicationEvent(EventBus eventBus, T event) {
        super(eventBus);
        this.event = event;
    }
}
