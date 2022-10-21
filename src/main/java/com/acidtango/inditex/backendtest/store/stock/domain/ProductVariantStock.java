package com.acidtango.inditex.backendtest.store.stock.domain;

import com.acidtango.inditex.backendtest.shared.domain.AggregateRoot;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductVariantStockId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.events.Restocked;
import com.acidtango.inditex.backendtest.store.stock.domain.primitives.ProductVariantStockPrimitives;

public class ProductVariantStock extends AggregateRoot {
    private final ProductVariantStockId id;
    private final ProductId productId;
    private final VariantId variantId;
    private StockAmount stockAmount;

    public static ProductVariantStock zeroFor(ProductVariantStockId productVariantStockId, ProductId productId, VariantId variantId) {
        return new ProductVariantStock(productVariantStockId, productId, variantId, StockAmount.zero());
    }

    public static ProductVariantStock fromPrimitives(ProductVariantStockPrimitives primitives) {
        return new ProductVariantStock(
            new ProductVariantStockId(primitives.id()),
            new ProductId(primitives.productId()),
            new VariantId(primitives.variantId()),
            StockAmount.fromPrimitives(primitives.stockAmount())
        );
    }

    public ProductVariantStock(ProductVariantStockId id, ProductId productId, VariantId variantId, StockAmount stockAmount) {
        this.id = id;
        this.productId = productId;
        this.variantId = variantId;
        this.stockAmount = stockAmount;
    }

    public ProductVariantStockPrimitives toPrimitives() {
        return new ProductVariantStockPrimitives(id.getId(), productId.getId(), variantId.getId(), stockAmount.toPrimitives());
    }

    public void addStock(StockAmount amount) {
        this.stockAmount = this.stockAmount.add(amount);

        this.record(new Restocked(id));
    }
}
