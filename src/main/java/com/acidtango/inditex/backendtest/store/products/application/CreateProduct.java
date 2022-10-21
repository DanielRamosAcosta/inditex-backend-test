package com.acidtango.inditex.backendtest.store.products.application;

import com.acidtango.inditex.backendtest.shared.application.UseCase;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import com.acidtango.inditex.backendtest.store.products.domain.Product;
import com.acidtango.inditex.backendtest.store.products.domain.ProductRepository;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import org.springframework.stereotype.Service;

@Service
public class CreateProduct extends UseCase {
    final EventBus eventBus;
    final ProductRepository productRepository;

    public CreateProduct(EventBus eventBus, ProductRepository productRepository) {
        this.eventBus = eventBus;
        this.productRepository = productRepository;
    }

    public ProductId execute(String name) {
        ProductId productId = productRepository.getNextId();
        var firstId = productRepository.getNextVariantId();
        var secondId = firstId.nextId();
        var thirdId = secondId.nextId();

        Product product = Product.create(
            productId,
            name,
            firstId,
            secondId,
            thirdId
        );

        productRepository.save(product);

        eventBus.publish(product.pullDomainEvents());

        return productId;
    }
}
