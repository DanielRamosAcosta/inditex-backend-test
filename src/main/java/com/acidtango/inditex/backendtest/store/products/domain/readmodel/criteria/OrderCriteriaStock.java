package com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria;

import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;

public class OrderCriteriaStock implements OrderCriteria {
    @Override
    public OrderWeight getWeight(ProductWithStock productWithStock) {
        var stock = productWithStock.variantsStock();
        return new OrderWeight(stock.small().amount() + stock.medium().amount() + stock.large().amount());
    }
}
