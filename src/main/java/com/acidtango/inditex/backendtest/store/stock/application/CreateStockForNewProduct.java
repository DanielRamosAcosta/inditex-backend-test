package com.acidtango.inditex.backendtest.store.stock.application;

import com.acidtango.inditex.backendtest.shared.application.DomainEventHandler;
import com.acidtango.inditex.backendtest.store.products.domain.events.ProductCreated;
import com.acidtango.inditex.backendtest.store.products.domain.services.ProductFinder;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStock;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStockRepository;
import org.springframework.stereotype.Component;

@Component
public class CreateStockForNewProduct extends DomainEventHandler<ProductCreated> {
    private final ProductVariantStockRepository productVariantStockRepository;
    private final ProductFinder productFinder;

    public CreateStockForNewProduct(ProductVariantStockRepository productVariantStockRepository, ProductFinder productFinder) {
        this.productVariantStockRepository = productVariantStockRepository;
        this.productFinder = productFinder;
    }

    @Override
    public void handle(ProductCreated domainEvent) {
        execute(domainEvent.productId());
    }

    private void execute(ProductId productId) {
        var product = productFinder.findByIdOrThrow(productId);

        for (VariantId variantId : product.allVariantsIds()) {
            var productVariantStockId = productVariantStockRepository.getNextId();
            var productVariantStock = ProductVariantStock.zeroFor(productVariantStockId, productId, variantId);

            productVariantStockRepository.save(productVariantStock);
        }
    }
}
