package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record OrderLinesDto(
        @Schema(description = "The id of the product")
        Integer productId,
        @Schema(description = "The id of the variant, which can be large, medium or small")
        Integer variantId,
        @Schema(description = "The amount of this variant that you want")
        Integer amount
) {}
