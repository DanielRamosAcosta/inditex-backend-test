package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers;

public record GetProductsElementDto(Integer id, String name, Integer salesUnits, VariantsStockResponseDto stock) {
}
