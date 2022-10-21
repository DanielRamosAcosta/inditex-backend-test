package com.acidtango.inditex.backendtest.store.stock.domain.primitives;

public record ProductVariantStockPrimitives(
        Integer id,
        Integer productId,
        Integer variantId,
        Integer stockAmount
) {}
