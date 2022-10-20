package com.acidtango.inditex.backendtest.store.products.domain.events;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;

public record ProductCreated(ProductId productId) implements DomainEvent {
    @Override
    public String fullQualifiedEventName() {
        return "ProductCreated";
    }
}
