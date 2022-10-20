package com.acidtango.inditex.backendtest.store.orders.infrastructure.repository;

import com.acidtango.inditex.backendtest.store.orders.domain.Order;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderRepository;
import com.acidtango.inditex.backendtest.store.orders.domain.primitives.OrderPrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class OrderRepositoryMemory implements OrderRepository {
    public HashMap<Integer, OrderPrimitives> orders = new HashMap<>();

    @Override
    public OrderId getNextId() {
        var value = orders.keySet().stream().mapToInt(Integer::intValue).max();

        return new OrderId(value.orElse(0) + 1);
    }

    @Override
    public void save(Order order) {
        var primitives = order.toPrimitives();
        orders.put(primitives.orderId(), primitives);
    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        return Optional.ofNullable(orders.get(orderId.getId())).map(Order::from);
    }

    public Integer count() {
        return orders.size();
    }
}
