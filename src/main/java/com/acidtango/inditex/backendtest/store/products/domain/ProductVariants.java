package com.acidtango.inditex.backendtest.store.products.domain;

import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductVariantPrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

import java.util.List;

public class ProductVariants {
    final List<ProductVariant> variants;

    public static ProductVariants from(List<ProductVariantPrimitives> primitives) {
        return new ProductVariants(primitives.stream().map(ProductVariant::from).toList());
    }

    public ProductVariants(List<ProductVariant> variants) {
        this.variants = variants;
    }

    public static ProductVariants defaults(VariantId largeId, VariantId mediumId, VariantId smallId) {
        return new ProductVariants(List.of(
            ProductVariant.large(largeId),
            ProductVariant.medium(mediumId),
            ProductVariant.small(smallId)
        ));
    }

    public List<ProductVariantPrimitives> toPrimitives() {
        return variants.stream().map(ProductVariant::toPrimitives).toList();
    }

    public List<VariantId> allIds() {
        return variants.stream().map(ProductVariant::getId).toList();
    }
}
