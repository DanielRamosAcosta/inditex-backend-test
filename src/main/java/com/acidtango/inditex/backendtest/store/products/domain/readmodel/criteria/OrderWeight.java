package com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria;

import com.acidtango.inditex.backendtest.shared.domain.ValueObject;

public class OrderWeight extends ValueObject implements Comparable<OrderWeight> {
    public final Integer weight;

    public static OrderWeight unit() {
        return new OrderWeight(1);
    }

    public OrderWeight(Integer weight) {
        this.weight = weight;
    }

    public OrderWeight times(OrderWeight other) {
        return new OrderWeight(weight * other.weight);
    }

    public OrderWeight add(OrderWeight other) {
        return new OrderWeight(weight + other.weight);
    }

    @Override
    public int compareTo(OrderWeight other) {
        return weight - other.weight;
    }
}
