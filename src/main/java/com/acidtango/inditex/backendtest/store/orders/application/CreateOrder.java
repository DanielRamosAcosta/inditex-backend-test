package com.acidtango.inditex.backendtest.store.orders.application;

import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import com.acidtango.inditex.backendtest.store.orders.domain.Order;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLine;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLines;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderRepository;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;
import com.acidtango.inditex.backendtest.store.stock.domain.services.ProductVariantStockFinder;
import org.springframework.stereotype.Service;

@Service
public class CreateOrder  {
    final OrderRepository orderRepository;
    final ProductVariantStockFinder productVariantStockFinder;
    final EventBus eventBus;

    public CreateOrder(OrderRepository orderRepository, ProductVariantStockFinder productVariantStockFinder, EventBus eventBus) {
        this.orderRepository = orderRepository;
        this.productVariantStockFinder = productVariantStockFinder;
        this.eventBus = eventBus;
    }

    public OrderId execute(OrderLines orderLines) {
        var orderId = orderRepository.getNextId();

        for (var orderLine : orderLines) {
            this.ensureThereIsEnoughStockFor(orderLine);
        }

        var order = Order.create(orderId, orderLines);

        orderRepository.save(order);

        eventBus.publish(order.pullDomainEvents());

        return orderId;
    }

    private void ensureThereIsEnoughStockFor(OrderLine orderLine) {
        var productVariantStock = this.productVariantStockFinder.findByVariantIdOrThrow(orderLine.getVariantId());

        productVariantStock.ensureThereIsEnough(orderLine.getAmount());
    }
}
