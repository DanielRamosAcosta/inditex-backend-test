package com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria;

import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;

public class ListProductStockOrderCriteria implements OrderCriteria {
    private final OrderCriteriaSalesUnits oderCriteriaSalesUnits = new OrderCriteriaSalesUnits();
    private final OrderCriteriaStock orderCriteriaStock = new OrderCriteriaStock();
    private final OrderWeight salesUnitsEpsilon;
    private final OrderWeight stockEpsilon;

    public ListProductStockOrderCriteria(OrderWeight salesUnitsEpsilon, OrderWeight stockEpsilon) {
        this.salesUnitsEpsilon = salesUnitsEpsilon;
        this.stockEpsilon = stockEpsilon;
    }

    @Override
    public OrderWeight getWeight(ProductWithStock productWithStock) {
        return OrderWeight.unit()
                .add(oderCriteriaSalesUnits.getWeight(productWithStock).times(salesUnitsEpsilon))
                .add(orderCriteriaStock.getWeight(productWithStock).times(stockEpsilon));
    }
}
