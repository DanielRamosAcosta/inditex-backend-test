package com.acidtango.inditex.backendtest.store.orders.application;

import com.acidtango.inditex.backendtest.shared.infrastructure.events.noop.EventBusNoop;
import com.acidtango.inditex.backendtest.shared.infrastructure.events.tracker.EventBusTracker;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLine;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLines;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.repository.OrderRepositoryMemory;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CreateOrderTest {
    @Test
    void places_the_order_at_the_repository() {
        var orderRepositoryMemory = new OrderRepositoryMemory();
        var eventBus = new EventBusNoop();
        var createOrder = new CreateOrder(orderRepositoryMemory, eventBus);
        var orderLine = new OrderLine(new ProductId(1), new VariantId(1), 1);
        var orderLines = new OrderLines(List.of(orderLine));

        createOrder.execute(orderLines);

        assertEquals(orderRepositoryMemory.count(), 1);
    }

    @Test
    void publishes_an_order_created_event() {
        var orderRepositoryMemory = new OrderRepositoryMemory();
        var eventBus = new EventBusTracker();
        var createOrder = new CreateOrder(orderRepositoryMemory, eventBus);
        var orderLine = new OrderLine(new ProductId(1), new VariantId(1), 1);
        var orderLines = new OrderLines(List.of(orderLine));

        createOrder.execute(orderLines);

        assertEquals(eventBus.count(), 1);
        assertEquals(eventBus.lastEventName(), "OrderCreated");
    }
}