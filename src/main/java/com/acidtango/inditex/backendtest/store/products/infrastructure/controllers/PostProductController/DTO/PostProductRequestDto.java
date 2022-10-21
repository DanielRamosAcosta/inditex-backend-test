package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostProductRequestDto(
        @Schema(description = "The name of the product")
        String name
) {
}
