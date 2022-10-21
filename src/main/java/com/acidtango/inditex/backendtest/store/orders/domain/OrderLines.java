package com.acidtango.inditex.backendtest.store.orders.domain;

import com.acidtango.inditex.backendtest.shared.domain.ValueObject;
import com.acidtango.inditex.backendtest.store.orders.domain.primitives.OrderLinePrimitives;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderLines extends ValueObject implements Iterable<OrderLine> {
    final List<OrderLine> lines;

    public static OrderLines from(List<OrderLinePrimitives> primitives) {
        return new OrderLines(primitives.stream().map(OrderLine::from).collect(Collectors.toList()));
    }

    public OrderLines(List<OrderLine> lines) {
        this.lines = lines;
    }

    public List<OrderLinePrimitives> toPrimitives() {
        return lines.stream().map(OrderLine::toPrimitives).collect(Collectors.toList());
    }

    @Override
    public Iterator<OrderLine> iterator() {
        return lines.iterator();
    }
}
