package com.acidtango.inditex.backendtest.store.stock.domain;

import com.acidtango.inditex.backendtest.shared.domain.ValueObject;

public class StockAmount extends ValueObject {
    private final Integer amount;

    public static StockAmount zero() {
        return new StockAmount(0);
    }

    public StockAmount(Integer amount) {
        this.amount = amount;
    }

    public static StockAmount fromPrimitives(Integer primitives) {
        return new StockAmount(primitives);
    }

    public Integer toPrimitives() {
        return amount;
    }
}
