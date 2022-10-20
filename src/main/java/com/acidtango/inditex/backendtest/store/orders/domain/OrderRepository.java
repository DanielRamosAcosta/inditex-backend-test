package com.acidtango.inditex.backendtest.store.orders.domain;

import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;

import java.util.Optional;

public interface OrderRepository {
    OrderId getNextId();
    void save(Order order);
    Optional<Order> findById(OrderId orderId);
}
