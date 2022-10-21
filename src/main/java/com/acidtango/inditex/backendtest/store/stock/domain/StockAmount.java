package com.acidtango.inditex.backendtest.store.stock.domain;

import com.acidtango.inditex.backendtest.shared.domain.ValueObject;
import com.acidtango.inditex.backendtest.store.stock.domain.exceptions.InvalidStockAmount;

public class StockAmount extends ValueObject {
    private final Integer amount;

    public static StockAmount zero() {
        return new StockAmount(0);
    }

    private static void ensureAmountIsPositive(Integer amount) {
        if (amount < 0) {
            throw new InvalidStockAmount();
        }
    }

    public StockAmount(Integer amount) {
        ensureAmountIsPositive(amount);
        this.amount = amount;
    }

    public static StockAmount fromPrimitives(Integer primitives) {
        return new StockAmount(primitives);
    }

    public Integer toPrimitives() {
        return amount;
    }

    public StockAmount add(StockAmount other) {
        return new StockAmount(this.amount + other.amount);
    }
}
