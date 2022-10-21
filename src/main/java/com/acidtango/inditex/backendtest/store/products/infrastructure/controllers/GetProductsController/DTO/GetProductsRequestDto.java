package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Optional;

public record GetProductsRequestDto(
        @Schema(description = "The ratio to give more or less weight to the sales of units")
        Optional<Integer> salesUnitsEpsilon,

        @Schema(description = "The ratio to give more or less weight to the total sum of the stock")
        Optional<Integer> stockEpsilon
) {}
