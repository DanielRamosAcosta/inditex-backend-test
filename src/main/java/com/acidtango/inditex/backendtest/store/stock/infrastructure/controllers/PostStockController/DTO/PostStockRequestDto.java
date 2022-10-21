package com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers.PostStockController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostStockRequestDto(
        @Schema(description = "The restock increment amount")
        Integer amount
) {}
