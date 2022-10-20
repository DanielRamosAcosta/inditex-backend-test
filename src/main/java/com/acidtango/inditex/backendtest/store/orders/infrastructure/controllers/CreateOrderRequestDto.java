package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers;

public record CreateOrderRequestDto(Integer productId, Integer variantId, Integer amount) {
}
