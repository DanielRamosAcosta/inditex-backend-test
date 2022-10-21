package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO;

import java.util.List;

public record GetProductsResponseDto(List<GetProductsElementDto> items) {
}
