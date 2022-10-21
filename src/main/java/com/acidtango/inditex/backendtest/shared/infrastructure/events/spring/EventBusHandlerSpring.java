package com.acidtango.inditex.backendtest.shared.infrastructure.events.spring;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.store.shared.application.DomainEventRouter;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventBusHandlerSpring {
    private final DomainEventRouter domainEventRouter;

    public EventBusHandlerSpring(DomainEventRouter domainEventRouter) {
        this.domainEventRouter = domainEventRouter;
    }

    @EventListener
    @Async
    public void handleEvent(GenericApplicationEvent<DomainEvent> event) {
        this.domainEventRouter.handle(event.event);
    }
}
