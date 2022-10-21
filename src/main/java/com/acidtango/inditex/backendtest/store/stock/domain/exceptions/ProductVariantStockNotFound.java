package com.acidtango.inditex.backendtest.store.stock.domain.exceptions;

import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

public class ProductVariantStockNotFound extends RuntimeException {
    private final VariantId variantId;

    public ProductVariantStockNotFound(VariantId variantId) {
        this.variantId = variantId;
    }
}
