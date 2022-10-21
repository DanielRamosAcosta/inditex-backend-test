package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController;

import com.acidtango.inditex.backendtest.store.products.application.CreateProduct;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO.PostProductRequestDto;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO.PostProductResponseDto;
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
    PostProductResponseDto create(@RequestBody PostProductRequestDto postProductRequestDto) {
        var productId = createProduct.execute(postProductRequestDto.name());

        return new PostProductResponseDto(productId.getId());
    }
}
