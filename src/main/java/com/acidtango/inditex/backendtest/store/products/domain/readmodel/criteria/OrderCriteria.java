package com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria;

import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;

public interface OrderCriteria {
    OrderWeight getWeight(ProductWithStock productWithStock);
}

