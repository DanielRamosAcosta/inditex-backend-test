package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO;

import java.util.List;

public record CreateOrderRequestDto(List<OrderLinesDto> items) {
}
