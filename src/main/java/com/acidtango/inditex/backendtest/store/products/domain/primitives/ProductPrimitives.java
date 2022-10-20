package com.acidtango.inditex.backendtest.store.products.domain.primitives;

import java.util.List;

public record ProductPrimitives(Integer id, String name, List<ProductVariantPrimitives> variants) {
}
