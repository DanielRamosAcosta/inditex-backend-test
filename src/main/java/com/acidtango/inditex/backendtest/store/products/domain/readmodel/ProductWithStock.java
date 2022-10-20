package com.acidtango.inditex.backendtest.store.products.domain.readmodel;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;

public record ProductWithStock(ProductId id, String name, Integer salesUnits, VariantStock variantStock) {
}
