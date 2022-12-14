package com.acidtango.inditex.backendtest.store.products.domain.readmodel;

import com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria.ListProductStockOrderCriteria;

import java.util.List;

public interface ProductReadModelRepository {
    List<ProductWithStock> find(ListProductStockOrderCriteria orderCriteria);
}
