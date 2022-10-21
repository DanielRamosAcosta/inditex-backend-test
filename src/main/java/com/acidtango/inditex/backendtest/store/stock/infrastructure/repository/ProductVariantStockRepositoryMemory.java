package com.acidtango.inditex.backendtest.store.stock.infrastructure.repository;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductVariantStockId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStock;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStockRepository;
import com.acidtango.inditex.backendtest.store.stock.domain.primitives.ProductVariantStockPrimitives;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class ProductVariantStockRepositoryMemory implements ProductVariantStockRepository {
    public HashMap<Integer, ProductVariantStockPrimitives> productVariantStock = new HashMap<>();

    public static ProductVariantStockPrimitives vNechBasicShirt = new ProductVariantStockPrimitives(1, 1, 1, 0);

    public static ProductVariantStockRepositoryMemory withSomeProducts() {
        var repository = new ProductVariantStockRepositoryMemory();

        repository.productVariantStock.put(vNechBasicShirt.id(), vNechBasicShirt);

        return repository;
    }

    public static ProductVariantStockRepositoryMemory empty() {
        return new ProductVariantStockRepositoryMemory();
    }

    @Override
    public ProductVariantStockId getNextId() {
        var value = productVariantStock.keySet().stream().mapToInt(Integer::intValue).max();

        return new ProductVariantStockId(value.orElse(0) + 1);
    }

    @Override
    public void save(ProductVariantStock productVariantStock) {
        var primitives = productVariantStock.toPrimitives();
        this.productVariantStock.put(primitives.id(), primitives);
    }

    @Override
    public Optional<ProductVariantStock> findByVariantId(VariantId variantId) {
        return productVariantStock.values().stream()
            .filter(productVariantStockPrimitives -> productVariantStockPrimitives.variantId().equals(variantId.getId()))
            .findFirst()
            .map(ProductVariantStock::fromPrimitives);
    }
}
