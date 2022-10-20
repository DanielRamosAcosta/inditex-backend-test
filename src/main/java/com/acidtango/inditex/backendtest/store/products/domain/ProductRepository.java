package com.acidtango.inditex.backendtest.store.products.domain;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

import java.util.Optional;

public interface ProductRepository {
    ProductId getNextId();
    VariantId getNextVariantId();
    void save(Product product);
    Optional<Product> findById(ProductId productId);
}
