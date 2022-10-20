package com.acidtango.inditex.backendtest.store.products.domain.services;

import com.acidtango.inditex.backendtest.shared.domain.DomainService;
import com.acidtango.inditex.backendtest.store.products.domain.Product;
import com.acidtango.inditex.backendtest.store.products.domain.ProductRepository;
import com.acidtango.inditex.backendtest.store.products.domain.exceptions.ProductNotFoundException;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import org.springframework.stereotype.Component;

@Component
public class ProductFinder extends DomainService {
    private final ProductRepository productRepository;


    public ProductFinder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findByIdOrThrow(ProductId productId) {
        var product = productRepository.findById(productId);

        return product.orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
