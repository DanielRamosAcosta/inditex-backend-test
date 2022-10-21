package com.acidtango.inditex.backendtest.store.stock.application;

import com.acidtango.inditex.backendtest.shared.application.DomainEventHandler;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLine;
import com.acidtango.inditex.backendtest.store.orders.domain.events.OrderCreated;
import com.acidtango.inditex.backendtest.store.orders.domain.services.OrderFinder;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStockRepository;
import com.acidtango.inditex.backendtest.store.stock.domain.services.ProductVariantStockFinder;
import org.springframework.stereotype.Component;

@Component
public class DecrementStockForOrder extends DomainEventHandler<OrderCreated> {
    private final OrderFinder orderFinder;
    private final ProductVariantStockRepository productVariantStockRepository;
    private final ProductVariantStockFinder productVariantStockFinder;

    public DecrementStockForOrder(OrderFinder orderFinder, ProductVariantStockRepository productVariantStockRepository, ProductVariantStockFinder productVariantStockFinder) {
        this.orderFinder = orderFinder;
        this.productVariantStockRepository = productVariantStockRepository;
        this.productVariantStockFinder = productVariantStockFinder;
    }

    @Override
    public void handle(OrderCreated domainEvent) {
        execute(domainEvent.orderId());
    }

    private void execute(OrderId orderId) {
        var order = orderFinder.findByIdOrThrow(orderId);

        for (OrderLine orderLine : order.lines()) {
            var stock = productVariantStockFinder.findByVariantIdOrThrow(orderLine.getVariantId());

            stock.decrementIn(orderLine.getAmount());

            productVariantStockRepository.save(stock);
        }
    }
}
