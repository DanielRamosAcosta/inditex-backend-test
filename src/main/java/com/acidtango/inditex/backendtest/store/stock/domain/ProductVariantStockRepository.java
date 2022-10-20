package com.acidtango.inditex.backendtest.store.stock.domain;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductVariantStockId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

import java.util.Optional;

public interface ProductVariantStockRepository {
    ProductVariantStockId getNextId();
    void save(ProductVariantStock productStock);

    Optional<ProductVariantStock> findByVariantId(VariantId variantId);
}
