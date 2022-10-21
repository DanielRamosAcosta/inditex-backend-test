package com.acidtango.inditex.backendtest.store.products.application;

import com.acidtango.inditex.backendtest.shared.application.UseCase;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductReadModelRepository;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria.ListProductStockOrderCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListProducts extends UseCase {
    final ProductReadModelRepository productReadModelRepository;

    public ListProducts(ProductReadModelRepository productReadModelRepository) {
        this.productReadModelRepository = productReadModelRepository;
    }

    public List<ProductWithStock> execute(ListProductStockOrderCriteria orderCriteria) {
        return productReadModelRepository.find(orderCriteria);
    }
}
