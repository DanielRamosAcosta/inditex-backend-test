package com.acidtango.inditex.backendtest.store.products.domain;

import com.acidtango.inditex.backendtest.shared.domain.Entity;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductVariantPrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

public final class ProductVariant extends Entity {
    final VariantId id;
    final ProductSize size;

    public static ProductVariant large(VariantId id) {
        return new ProductVariant(id, ProductSize.LARGE);
    }

    public static ProductVariant medium(VariantId id) {
        return new ProductVariant(id, ProductSize.MEDIUM);
    }

    public static ProductVariant small(VariantId id) {
        return new ProductVariant(id, ProductSize.SMALL);
    }

    public ProductVariant(VariantId id, ProductSize size) {
        this.id = id;
        this.size = size;
    }

    public static ProductVariant from(ProductVariantPrimitives primitives) {
        return new ProductVariant(new VariantId(primitives.id()), primitives.size());
    }

    public ProductVariantPrimitives toPrimitives() {
        return new ProductVariantPrimitives(id.getId(), size);
    }

    public VariantId getId() {
        return id;
    }
}
