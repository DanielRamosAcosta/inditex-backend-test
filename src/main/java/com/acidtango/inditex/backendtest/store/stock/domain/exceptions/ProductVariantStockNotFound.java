package com.acidtango.inditex.backendtest.store.stock.domain.exceptions;

import com.acidtango.inditex.backendtest.shared.domain.DomainException;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

public class ProductVariantStockNotFound extends DomainException {
    private final VariantId variantId;

    public ProductVariantStockNotFound(VariantId variantId) {
        this.variantId = variantId;
    }
}
