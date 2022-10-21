package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record VariantStockDto(
        @Schema(description = "The id of this variant, you can use it for placing an order")
        Integer id,

        @Schema(description = "The stock amount of this variant")
        Integer amount
) {}
