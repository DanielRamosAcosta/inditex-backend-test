package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController;

import com.acidtango.inditex.backendtest.store.products.application.CreateProduct;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO.PostProductRequestDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO.PostProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostProductController {
    final CreateProduct createProduct;

    public PostProductController(CreateProduct createProduct) {
        this.createProduct = createProduct;
    }

    @Operation(description = "Creates a brand new product with zero stock")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PostProductResponseDto create(@RequestBody PostProductRequestDto postProductRequestDto) {
        var productId = createProduct.execute(postProductRequestDto.name());

        return new PostProductResponseDto(productId.getId());
    }
}
