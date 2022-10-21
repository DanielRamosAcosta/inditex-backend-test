package com.acidtango.inditex.backendtest.store.stock.application;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.shared.infrastructure.events.noop.EventBusNoop;
import com.acidtango.inditex.backendtest.store.shared.infrastructure.events.tracker.EventBusTracker;
import com.acidtango.inditex.backendtest.store.stock.domain.StockAmount;
import com.acidtango.inditex.backendtest.store.stock.domain.exceptions.ProductVariantStockNotFound;
import com.acidtango.inditex.backendtest.store.stock.domain.services.ProductVariantStockFinder;
import com.acidtango.inditex.backendtest.store.stock.infrastructure.repository.ProductVariantStockRepositoryMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RestockTest {

    @Test
    void adding_new_stock() {
        var productVariantStockRepository = ProductVariantStockRepositoryMemory.withSomeProducts();
        var productVariantStockFinder = new ProductVariantStockFinder(productVariantStockRepository);
        var eventBus = new EventBusNoop();
        var restock = new Restock(productVariantStockRepository, productVariantStockFinder, eventBus);
        var aProductId = new ProductId(ProductVariantStockRepositoryMemory.vNechBasicShirt.productId());
        var aVariantId = new VariantId(ProductVariantStockRepositoryMemory.vNechBasicShirt.variantId());
        var amount = StockAmount.fromPrimitives(100);

        restock.execute(aProductId, aVariantId, amount);

        var element = productVariantStockRepository.productVariantStock.get(aProductId.getId());
        assertThat(element.stockAmount()).isEqualTo(100);
    }

    @Test
    void records_a_domain_event() {
        var productVariantStockRepository = ProductVariantStockRepositoryMemory.withSomeProducts();
        var productVariantStockFinder = new ProductVariantStockFinder(productVariantStockRepository);
        var eventBus = new EventBusTracker();
        var restock = new Restock(productVariantStockRepository, productVariantStockFinder, eventBus);
        var aProductId = new ProductId(ProductVariantStockRepositoryMemory.vNechBasicShirt.productId());
        var aVariantId = new VariantId(ProductVariantStockRepositoryMemory.vNechBasicShirt.variantId());
        var anAmount = StockAmount.fromPrimitives(100);

        restock.execute(aProductId, aVariantId, anAmount);

        assertThat(eventBus.lastEventName()).isEqualTo("Restocked");
    }

    @Test
    void throws_if_no_stock_found() {
        var productVariantStockRepository = ProductVariantStockRepositoryMemory.empty();
        var productVariantStockFinder = new ProductVariantStockFinder(productVariantStockRepository);
        var eventBus = new EventBusTracker();
        var restock = new Restock(productVariantStockRepository, productVariantStockFinder, eventBus);
        var aProductId = new ProductId(ProductVariantStockRepositoryMemory.vNechBasicShirt.productId());
        var aVariantId = new VariantId(ProductVariantStockRepositoryMemory.vNechBasicShirt.variantId());
        var anAmount = StockAmount.fromPrimitives(100);

        assertThatThrownBy(() -> restock.execute(aProductId, aVariantId, anAmount))
                .isInstanceOf(ProductVariantStockNotFound.class);
    }
}