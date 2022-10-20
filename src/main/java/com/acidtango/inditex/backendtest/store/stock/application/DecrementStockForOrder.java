package com.acidtango.inditex.backendtest.store.stock.application;

import com.acidtango.inditex.backendtest.shared.application.DomainEventHandler;
import com.acidtango.inditex.backendtest.store.orders.domain.events.OrderCreated;
import com.acidtango.inditex.backendtest.store.orders.domain.services.OrderFinder;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;
import org.springframework.stereotype.Component;

@Component
public class DecrementStockForOrder extends DomainEventHandler<OrderCreated> {
    private final OrderFinder orderFinder;

    public DecrementStockForOrder(OrderFinder orderFinder) {
        this.orderFinder = orderFinder;
    }

    @Override
    public void handle(OrderCreated domainEvent) {
        execute(domainEvent.orderId());
    }

    private void execute(OrderId orderId) {
        var order = orderFinder.findByIdOrThrow(orderId);


        System.out.println("Estoy!");
    }
}
