package com.acidtango.inditex.backendtest.store.orders.domain;

import com.acidtango.inditex.backendtest.shared.domain.ValueObject;
import com.acidtango.inditex.backendtest.store.orders.domain.primitives.OrderLinePrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

public class OrderLine extends ValueObject {
    final ProductId productId;
    final VariantId variantId;
    final Integer amount;

    public static OrderLine from(OrderLinePrimitives primitives) {
        return new OrderLine(new ProductId(primitives.productId()), new VariantId(primitives.variantId()), primitives.amount());
    }

    public OrderLine(ProductId productId, VariantId variantId, Integer amount) {
        this.productId = productId;
        this.variantId = variantId;
        this.amount = amount;
    }

    public OrderLinePrimitives toPrimitives() {
        return new OrderLinePrimitives(productId.getId(), variantId.getId(), amount);
    }

    public VariantId getVariantId() {
        return variantId;
    }

    public Integer getAmount() {
        return amount;
    }
}
