package com.acidtango.inditex.backendtest.store.orders.domain.events;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;

public record OrderCreated(OrderId orderId) implements DomainEvent {
    @Override
    public String fullQualifiedEventName() {
        return "OrderCreated";
    }
}
