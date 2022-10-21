package com.acidtango.inditex.backendtest.store.orders.domain;

import com.acidtango.inditex.backendtest.shared.domain.AggregateRoot;
import com.acidtango.inditex.backendtest.store.orders.domain.events.OrderCreated;
import com.acidtango.inditex.backendtest.store.orders.domain.primitives.OrderPrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;

public final class Order extends AggregateRoot {
    final OrderId orderId;
    final OrderLines orderLines;

    public static Order from(OrderPrimitives primitives) {
        return new Order(new OrderId(primitives.orderId()), OrderLines.from(primitives.orderLines()));
    }

    public static Order create(OrderId id, OrderLines orderLines) {
        Order order = new Order(id, orderLines);

        order.record(new OrderCreated(id));

        return order;
    }

    public Order(OrderId orderId, OrderLines orderLines) {
        this.orderId = orderId;
        this.orderLines = orderLines;
    }

    public OrderId getId() {
        return orderId;
    }

    public OrderPrimitives toPrimitives() {
        return new OrderPrimitives(orderId.getId(), orderLines.toPrimitives());
    }

    public OrderLines lines() {
        return orderLines;
    }
}
