package com.acidtango.inditex.backendtest.store.orders.domain.services;

import com.acidtango.inditex.backendtest.shared.domain.DomainService;
import com.acidtango.inditex.backendtest.store.orders.domain.Order;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderRepository;
import com.acidtango.inditex.backendtest.store.orders.domain.exceptions.OrderNotFoundException;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;
import org.springframework.stereotype.Component;

@Component
public class OrderFinder extends DomainService {
    private final OrderRepository orderRepository;

    public OrderFinder(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findByIdOrThrow(OrderId orderId) {
        var order = orderRepository.findById(orderId);

        return order.orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
