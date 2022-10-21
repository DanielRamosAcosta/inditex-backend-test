package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateOrderRequestDto(
        @Schema(description = "Each one of the product variants that you want")
        List<OrderLinesDto> items
) {}
