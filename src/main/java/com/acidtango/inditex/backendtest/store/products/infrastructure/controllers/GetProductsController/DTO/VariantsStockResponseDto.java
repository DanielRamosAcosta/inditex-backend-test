package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record VariantsStockResponseDto(
        @Schema(description = "The stock information for the large size")
        VariantStockDto large,
        @Schema(description = "The stock information for the medium size")
        VariantStockDto medium,
        @Schema(description = "The stock information for the small size")
        VariantStockDto small
) {}
