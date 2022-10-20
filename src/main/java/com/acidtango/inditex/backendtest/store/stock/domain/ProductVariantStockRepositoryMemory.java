package com.acidtango.inditex.backendtest.store.stock.domain;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductVariantStockId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.primitives.ProductVariantStockPrimitives;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class ProductVariantStockRepositoryMemory implements ProductVariantStockRepository {
    public HashMap<Integer, ProductVariantStockPrimitives> productVariantStock = new HashMap<>();

    @Override
    public ProductVariantStockId getNextId() {
        var value = productVariantStock.keySet().stream().mapToInt(Integer::intValue).max();

        return new ProductVariantStockId(value.orElse(0) + 1);
    }

    @Override
    public void save(ProductVariantStock productVariantStock) {
        var primitives = productVariantStock.toPrimitives();
        this.productVariantStock.put(primitives.productVariantStockId(), primitives);
    }

    @Override
    public Optional<ProductVariantStock> findByVariantId(VariantId variantId) {
        return productVariantStock.values().stream()
            .filter(productVariantStockPrimitives -> productVariantStockPrimitives.variantId().equals(variantId.getId()))
            .findFirst()
            .map(ProductVariantStock::fromPrimitives);
    }
}
