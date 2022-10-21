package com.acidtango.inditex.backendtest.store.stock.domain.events;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductVariantStockId;

public record Restocked(ProductVariantStockId productVariantStockId) implements DomainEvent {
    @Override
    public String fullQualifiedEventName() {
        return "Restocked";
    }
}
