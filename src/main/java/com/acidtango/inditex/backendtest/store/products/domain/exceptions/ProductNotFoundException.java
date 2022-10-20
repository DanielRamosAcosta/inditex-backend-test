package com.acidtango.inditex.backendtest.store.products.domain.exceptions;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;

public class ProductNotFoundException extends RuntimeException {
    private final ProductId productId;

    public ProductNotFoundException(ProductId productId) {
        this.productId = productId;
    }
}
