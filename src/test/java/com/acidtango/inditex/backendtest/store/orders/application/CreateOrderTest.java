package com.acidtango.inditex.backendtest.store.orders.application;

import com.acidtango.inditex.backendtest.shared.infrastructure.events.noop.EventBusNoop;
import com.acidtango.inditex.backendtest.shared.infrastructure.events.tracker.EventBusTracker;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLine;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLines;
import com.acidtango.inditex.backendtest.store.orders.domain.exceptions.NotEnoughStock;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.repository.OrderRepositoryMemory;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.services.ProductVariantStockFinder;
import com.acidtango.inditex.backendtest.store.stock.infrastructure.repository.ProductVariantStockRepositoryMemory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


class CreateOrderTest {
    private final ProductId aProductId = new ProductId(ProductVariantStockRepositoryMemory.vNechBasicShirt.productId());
    private final VariantId aVariantId = new VariantId(ProductVariantStockRepositoryMemory.vNechBasicShirt.variantId());

    @Test
    void places_the_order_at_the_repository() {
        var orderRepositoryMemory = new OrderRepositoryMemory();
        var productVariantStockRepository = ProductVariantStockRepositoryMemory.withSomeProducts();
        var productVariantStockFinder = new ProductVariantStockFinder(productVariantStockRepository);
        var eventBus = new EventBusNoop();
        var createOrder = new CreateOrder(orderRepositoryMemory, productVariantStockFinder, eventBus);
        var orderLine = new OrderLine(aProductId, aVariantId, 1);
        var orderLines = new OrderLines(List.of(orderLine));

        createOrder.execute(orderLines);

        assertEquals(orderRepositoryMemory.count(), 1);
    }

    @Test
    void publishes_an_order_created_event() {
        var orderRepositoryMemory = new OrderRepositoryMemory();
        var productVariantStockRepository = ProductVariantStockRepositoryMemory.withSomeProducts();
        var productVariantStockFinder = new ProductVariantStockFinder(productVariantStockRepository);
        var eventBus = new EventBusTracker();
        var createOrder = new CreateOrder(orderRepositoryMemory, productVariantStockFinder, eventBus);
        var orderLine = new OrderLine(aProductId, aVariantId, 1);
        var orderLines = new OrderLines(List.of(orderLine));

        createOrder.execute(orderLines);

        assertEquals(eventBus.count(), 1);
        assertEquals(eventBus.lastEventName(), "OrderCreated");
    }

    @Test
    void throws_if_there_is_not_enough_stock() {
        var orderRepositoryMemory = new OrderRepositoryMemory();
        var productVariantStockRepository = ProductVariantStockRepositoryMemory.withSomeProducts();
        var productVariantStockFinder = new ProductVariantStockFinder(productVariantStockRepository);
        var eventBus = new EventBusTracker();
        var createOrder = new CreateOrder(orderRepositoryMemory, productVariantStockFinder, eventBus);
        var orderLine = new OrderLine(aProductId, aVariantId, 2);
        var orderLines = new OrderLines(List.of(orderLine));

        assertThatThrownBy(() -> createOrder.execute(orderLines))
                .isInstanceOf(NotEnoughStock.class);
    }
}