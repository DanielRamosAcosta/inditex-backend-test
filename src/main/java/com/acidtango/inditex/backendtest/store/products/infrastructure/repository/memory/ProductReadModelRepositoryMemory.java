package com.acidtango.inditex.backendtest.store.products.infrastructure.repository.memory;

import com.acidtango.inditex.backendtest.store.orders.domain.primitives.OrderLinePrimitives;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.repository.OrderRepositoryMemory;
import com.acidtango.inditex.backendtest.store.products.domain.ProductSize;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductPrimitives;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductReadModelRepository;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.VariantStock;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductVariantStockId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStock;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStockRepository;
import com.acidtango.inditex.backendtest.store.stock.domain.primitives.ProductVariantStockPrimitives;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductReadModelRepositoryMemory implements ProductReadModelRepository {
    private final ProductRepositoryMemory productRepositoryMemory;
    private final OrderRepositoryMemory orderRepositoryMemory;
    private final ProductVariantStockRepository productVariantStockRepository;

    public ProductReadModelRepositoryMemory(ProductRepositoryMemory productRepositoryMemory, OrderRepositoryMemory orderRepositoryMemory, ProductVariantStockRepository productVariantStockRepository) {
        this.productRepositoryMemory = productRepositoryMemory;
        this.orderRepositoryMemory = orderRepositoryMemory;
        this.productVariantStockRepository = productVariantStockRepository;
    }

    @Override
    public List<ProductWithStock> find() {
        return productRepositoryMemory.products
            .values()
            .stream()
            .map(product -> new ProductWithStock(
                    new ProductId(product.id()),
                    product.name(),
                    getSalesUnitsOf(product.id()),
                    new VariantStock(
                        stockForVariantWithSize(product, ProductSize.LARGE),
                        stockForVariantWithSize(product, ProductSize.MEDIUM),
                        stockForVariantWithSize(product, ProductSize.SMALL)
                    )
            ))
            .collect(Collectors.toList());
    }

    private Integer stockForVariantWithSize(ProductPrimitives product, ProductSize size) {
        return product
                .variants()
                .stream()
                .filter(primitives -> primitives.size().equals(size))
                .findFirst()
                .flatMap(variant -> productVariantStockRepository.findByVariantId(new VariantId(variant.id())))
                .map(ProductVariantStock::toPrimitives)
                .map(ProductVariantStockPrimitives::stockAmount)
                .orElse(0);
    }

    private int getSalesUnitsOf(Integer id) {
        return this.orderRepositoryMemory.orders
                .values()
                .stream()
                .flatMap(orderPrimitives -> orderPrimitives.orderLines().stream())
                .filter(primitives -> primitives.productId().equals(id))
                .mapToInt(OrderLinePrimitives::amount)
                .sum();
    }
}
