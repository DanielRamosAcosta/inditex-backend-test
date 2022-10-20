package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers;

import com.acidtango.inditex.backendtest.store.products.application.ListProducts;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class GetProductsController {
    final ListProducts listProducts;

    public GetProductsController(ListProducts listProducts) {
        this.listProducts = listProducts;
    }

    @GetMapping
    GetProductsResponseDto index() {
        var result = listProducts.execute();

        return toResponseDto(result);
    }

    private static GetProductsResponseDto toResponseDto(List<ProductWithStock> result) {
        var items = result
            .stream()
            .map(productWithStock -> new GetProductsElementDto(
                productWithStock.id().getId(),
                productWithStock.name(),
                productWithStock.salesUnits(),
                new VariantResponseDto(
                    productWithStock.variantStock().large(),
                    productWithStock.variantStock().medium(),
                    productWithStock.variantStock().small()
                )
            )).toList();

        return new GetProductsResponseDto(items);
    }
}
