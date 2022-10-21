package com.acidtango.inditex.backendtest.store.stock.domain.services;

import com.acidtango.inditex.backendtest.shared.domain.DomainService;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStock;
import com.acidtango.inditex.backendtest.store.stock.domain.ProductVariantStockRepository;
import com.acidtango.inditex.backendtest.store.stock.domain.exceptions.ProductVariantStockNotFound;
import org.springframework.stereotype.Component;

@Component
public class ProductVariantStockFinder extends DomainService {

    private final ProductVariantStockRepository productVariantStockRepository;

    public ProductVariantStockFinder(ProductVariantStockRepository productVariantStockRepository) {
        this.productVariantStockRepository = productVariantStockRepository;
    }

    public ProductVariantStock findByVariantIdOrThrow(VariantId variantId) {
        return productVariantStockRepository.findByVariantId(variantId)
                .orElseThrow(() -> new ProductVariantStockNotFound(variantId));
    }
}
