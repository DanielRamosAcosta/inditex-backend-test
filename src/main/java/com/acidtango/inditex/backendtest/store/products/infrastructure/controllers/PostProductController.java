package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers;

import com.acidtango.inditex.backendtest.store.products.application.CreateProduct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostProductController {
    final CreateProduct createProduct;

    public PostProductController(CreateProduct createProduct) {
        this.createProduct = createProduct;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateProductResponseDto create(@RequestBody CreateProductRequestDto createProductRequestDto) {
        var productId = createProduct.execute(createProductRequestDto.name());

        return new CreateProductResponseDto(productId.getId());
    }
}
