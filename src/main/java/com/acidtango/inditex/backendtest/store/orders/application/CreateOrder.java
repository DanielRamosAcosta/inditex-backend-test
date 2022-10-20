package com.acidtango.inditex.backendtest.store.orders.application;

import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import com.acidtango.inditex.backendtest.store.orders.domain.Order;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLines;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderRepository;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;
import org.springframework.stereotype.Service;

@Service
public class CreateOrder  {
    final OrderRepository orderRepository;
    final EventBus eventBus;

    public CreateOrder(OrderRepository orderRepository, EventBus eventBus) {
        this.orderRepository = orderRepository;
        this.eventBus = eventBus;
    }

    public OrderId execute(OrderLines orderLines) {
        var orderId = orderRepository.getNextId();

        var order = Order.create(orderId, orderLines);

        orderRepository.save(order);

        eventBus.publish(order.pullDomainEvents());

        return orderId;
    }
}
