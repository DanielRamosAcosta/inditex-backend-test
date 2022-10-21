package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetProductsElementDto(
        @Schema(description = "The id of the product", example = "1")
        Integer id,

        @Schema(description = "The name of a product", example = "V-NECH BASIC SHIRT")
        String name,

        @Schema(description = "The number of items of this type already sold", example = "100")
        Integer salesUnits,

        @Schema(description = "The stock for each type of variant")
        VariantsStockResponseDto stock
) {}
