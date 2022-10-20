package com.acidtango.inditex.backendtest.store.stock.domain;

import com.acidtango.inditex.backendtest.shared.domain.AggregateRoot;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductVariantPrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.DomainId;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductVariantStockId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.primitives.ProductVariantStockPrimitives;

import java.util.List;

public class ProductVariantStock extends AggregateRoot {
    private final ProductVariantStockId productVariantStockId;
    private final ProductId productId;
    private final VariantId variantId;
    private final StockAmount stockAmount;

    public static ProductVariantStock zeroFor(ProductVariantStockId productVariantStockId, ProductId productId, VariantId variantId) {
        return new ProductVariantStock(productVariantStockId, productId, variantId, StockAmount.zero());
    }

    public static ProductVariantStock fromPrimitives(ProductVariantStockPrimitives primitives) {
        return new ProductVariantStock(
            new ProductVariantStockId(primitives.productVariantStockId()),
            new ProductId(primitives.productId()),
            new VariantId(primitives.variantId()),
            StockAmount.fromPrimitives(primitives.stockAmount())
        );
    }

    public ProductVariantStock(ProductVariantStockId productVariantStockId, ProductId productId, VariantId variantId, StockAmount stockAmount) {
        this.productVariantStockId = productVariantStockId;
        this.productId = productId;
        this.variantId = variantId;
        this.stockAmount = stockAmount;
    }

    public ProductVariantStockPrimitives toPrimitives() {
        return new ProductVariantStockPrimitives(productVariantStockId.getId(), productId.getId(), variantId.getId(), stockAmount.toPrimitives());
    }
}
