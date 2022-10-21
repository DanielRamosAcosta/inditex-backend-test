package com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers;

import com.acidtango.inditex.backendtest.store.products.domain.ProductSize;

public record PostStockRequestDto(ProductSize productSize, Integer amount) {
}
