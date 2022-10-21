package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateOrderResponseDto(
        @Schema(description = "The order id")
        Integer id
) {}
