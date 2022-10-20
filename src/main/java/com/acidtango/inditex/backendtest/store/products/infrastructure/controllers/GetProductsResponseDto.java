package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers;

public record GetProductsResponseDto(Integer id, String name, Integer salesUnits, VariantResponseDto stock) {
}
