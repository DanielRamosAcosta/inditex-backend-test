package com.acidtango.inditex.backendtest.store.shared.application;

import com.acidtango.inditex.backendtest.shared.domain.DomainEvent;
import com.acidtango.inditex.backendtest.store.orders.domain.events.OrderCreated;
import com.acidtango.inditex.backendtest.store.products.domain.events.ProductCreated;
import com.acidtango.inditex.backendtest.store.stock.application.CreateStockForNewProduct;
import com.acidtango.inditex.backendtest.store.stock.application.DecrementStockForOrder;
import org.springframework.stereotype.Component;

@Component
public class DomainEventRouter {
    private final CreateStockForNewProduct createStockForNewProduct;
    private final DecrementStockForOrder decrementStockForOrder;

    public DomainEventRouter(CreateStockForNewProduct createStockForNewProduct, DecrementStockForOrder decrementStockForOrder) {
        this.createStockForNewProduct = createStockForNewProduct;
        this.decrementStockForOrder = decrementStockForOrder;
    }

    public void handle(DomainEvent domainEvent) {
        switch (domainEvent) {
            case OrderCreated orderCreated -> decrementStockForOrder.handle(orderCreated);
            case ProductCreated productCreated -> createStockForNewProduct.handle(productCreated);
            default -> System.out.println("Unhandled event: " + domainEvent.fullQualifiedEventName());
        }
    }
}
