package com.acidtango.inditex.backendtest.store.products.domain;

import com.acidtango.inditex.backendtest.shared.domain.AggregateRoot;
import com.acidtango.inditex.backendtest.store.products.domain.events.ProductCreated;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductPrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

import java.util.List;

public class Product extends AggregateRoot {
    final ProductId id;
    final String name;
    final ProductVariants variants;

    public static Product from(ProductPrimitives primitives) {
        return new Product(new ProductId(primitives.id()), primitives.name(), ProductVariants.from(primitives.variants()));
    }

    public Product(ProductId id, String name, ProductVariants variants) {
        this.id = id;
        this.name = name;
        this.variants = variants;
    }

    public static Product create(ProductId id, String name, VariantId largeId, VariantId mediumId, VariantId smallId) {
        var product = new Product(id, name, ProductVariants.defaults(largeId, mediumId, smallId));

        product.record(new ProductCreated(id));

        return product;
    }

    public ProductPrimitives toPrimitives() {
        return new ProductPrimitives(id.getId(), name, variants.toPrimitives());
    }

    public List<VariantId> allVariantsIds() {
        return variants.allIds();
    }
}
