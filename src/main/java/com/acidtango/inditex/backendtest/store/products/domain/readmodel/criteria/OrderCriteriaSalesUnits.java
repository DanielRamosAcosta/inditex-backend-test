package com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria;

import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;

public class OrderCriteriaSalesUnits implements OrderCriteria {
    @Override
    public OrderWeight getWeight(ProductWithStock productWithStock) {
        return new OrderWeight(productWithStock.salesUnits());
    }
}
