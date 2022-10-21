package com.acidtango.inditex.backendtest.store.products.infrastructure.repository.memory;

import com.acidtango.inditex.backendtest.store.orders.domain.primitives.OrderLinePrimitives;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.repository.OrderRepositoryMemory;
import com.acidtango.inditex.backendtest.store.products.domain.ProductSize;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductPrimitives;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductReadModelRepository;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.VariantStock;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.VariantsStock;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria.ListProductStockOrderCriteria;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStock;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStockRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public List<ProductWithStock> find(ListProductStockOrderCriteria orderCriteria) {
        return productRepositoryMemory.products
            .values()
            .stream()
            .map(product -> new ProductWithStock(
                    new ProductId(product.id()),
                    product.name(),
                    getSalesUnitsOf(product.id()),
                    new VariantsStock(
                        stockForVariantWithSize(product, ProductSize.LARGE),
                        stockForVariantWithSize(product, ProductSize.MEDIUM),
                        stockForVariantWithSize(product, ProductSize.SMALL)
                    )
            ))
            .sorted((p1, p2) -> orderCriteria.getWeight(p2).compareTo(orderCriteria.getWeight(p1)))
            .collect(Collectors.toList());
    }

    private VariantStock stockForVariantWithSize(ProductPrimitives product, ProductSize size) {
        return product
                .variants()
                .stream()
                .filter(primitives -> primitives.size().equals(size))
                .findFirst()
                .flatMap(variant -> productVariantStockRepository.findByVariantId(new VariantId(variant.id())))
                .map(ProductVariantStock::toPrimitives)
                .map(variant -> new VariantStock(variant.variantId(), variant.stockAmount()))
                .orElse(new VariantStock(0, 0));
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
