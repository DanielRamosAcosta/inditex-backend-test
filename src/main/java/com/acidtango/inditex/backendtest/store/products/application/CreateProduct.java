package com.acidtango.inditex.backendtest.store.products.application;

import com.acidtango.inditex.backendtest.shared.application.UseCase;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import com.acidtango.inditex.backendtest.store.products.domain.Product;
import com.acidtango.inditex.backendtest.store.products.domain.ProductRepository;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
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
        var firstId = productRepository.getNextVariantId();
        var secondId = firstId.nextId();
        var thirdId = secondId.nextId();

        Product product = Product.create(
            productRepository.getNextId(),
            name,
            firstId,
            secondId,
            thirdId
        );

        productRepository.save(product);

        eventBus.publish(product.pullDomainEvents());

        return productRepository.getNextId();
    }
}
