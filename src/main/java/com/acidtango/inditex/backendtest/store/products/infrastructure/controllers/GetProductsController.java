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
    List<GetProductsResponseDto> index() {
        var result = listProducts.execute();

        return toResponseDto(result);
    }

    private static List<GetProductsResponseDto> toResponseDto(List<ProductWithStock> result) {
        return result
            .stream()
            .map(productWithStock -> new GetProductsResponseDto(
                productWithStock.id().getId(),
                productWithStock.name(),
                productWithStock.salesUnits(),
                new VariantResponseDto(
                    productWithStock.variantStock().large(),
                    productWithStock.variantStock().medium(),
                    productWithStock.variantStock().small()
                )
            ))
            .collect(Collectors.toList());
    }
}
