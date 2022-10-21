package com.acidtango.inditex.backendtest.store.stock.application;

import com.acidtango.inditex.backendtest.shared.application.UseCase;
import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStockRepository;
import com.acidtango.inditex.backendtest.store.stock.domain.StockAmount;
import com.acidtango.inditex.backendtest.store.stock.domain.services.ProductVariantStockFinder;
import org.springframework.stereotype.Service;

@Service
public class Restock extends UseCase {
    private final ProductVariantStockRepository productVariantStockRepository;

    private final ProductVariantStockFinder productVariantStockFinder;

    private final EventBus eventBus;

    public Restock(ProductVariantStockRepository productVariantStockRepository, ProductVariantStockFinder productVariantStockFinder, EventBus eventBus) {
        this.productVariantStockRepository = productVariantStockRepository;
        this.productVariantStockFinder = productVariantStockFinder;
        this.eventBus = eventBus;
    }

    public void execute(ProductId productId, VariantId variantId, StockAmount amount) {
        var productVariantStock = productVariantStockFinder.findByVariantIdOrThrow(variantId);

        productVariantStock.addStock(amount);

        productVariantStockRepository.save(productVariantStock);

        this.eventBus.publish(productVariantStock.pullDomainEvents());
    }

}
