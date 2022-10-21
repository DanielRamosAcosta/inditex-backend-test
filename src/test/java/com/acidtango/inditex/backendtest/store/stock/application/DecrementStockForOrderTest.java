package com.acidtango.inditex.backendtest.store.stock.application;

import com.acidtango.inditex.backendtest.store.orders.domain.events.OrderCreated;
import com.acidtango.inditex.backendtest.store.orders.domain.services.OrderFinder;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.repository.OrderRepositoryMemory;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;
import com.acidtango.inditex.backendtest.store.stock.domain.services.ProductVariantStockFinder;
import com.acidtango.inditex.backendtest.store.stock.infrastructure.repository.ProductVariantStockRepositoryMemory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DecrementStockForOrderTest {

    @Test
    void decrements_the_stock_of_the_given_order() {
        var orderRepository = OrderRepositoryMemory.withAnOrder();
        var productVariantStockRepository = ProductVariantStockRepositoryMemory.withSomeProducts();
        var productVariantStockFinder = new ProductVariantStockFinder(productVariantStockRepository);
        var orderFinder = new OrderFinder(orderRepository);
        var decrementStockForOrder = new DecrementStockForOrder(orderFinder, productVariantStockRepository, productVariantStockFinder);

        decrementStockForOrder.handle(new OrderCreated(new OrderId(OrderRepositoryMemory.example.orderId())));

        var exampleProduct = productVariantStockRepository.productVariantStock.get(ProductVariantStockRepositoryMemory.vNechBasicShirt.id());
        assertThat(exampleProduct.stockAmount()).isEqualTo(0);
    }
}