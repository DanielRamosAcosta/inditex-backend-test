package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers;

public record OrderLinesDto(Integer productId, Integer variantId, Integer amount) {
}
