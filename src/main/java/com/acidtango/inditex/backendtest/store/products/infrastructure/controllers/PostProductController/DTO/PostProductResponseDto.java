package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.PostProductController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostProductResponseDto(
        @Schema(description = "The id of the product")
        Integer id
) {}
