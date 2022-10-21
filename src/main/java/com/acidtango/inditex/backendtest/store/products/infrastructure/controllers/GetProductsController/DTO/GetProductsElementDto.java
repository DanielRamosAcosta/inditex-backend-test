package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO;

public record GetProductsElementDto(Integer id, String name, Integer salesUnits, VariantsStockResponseDto stock) {
}
