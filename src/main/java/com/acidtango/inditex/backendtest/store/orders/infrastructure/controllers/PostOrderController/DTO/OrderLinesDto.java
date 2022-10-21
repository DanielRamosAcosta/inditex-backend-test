package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO;

public record OrderLinesDto(Integer productId, Integer variantId, Integer amount) {
}
