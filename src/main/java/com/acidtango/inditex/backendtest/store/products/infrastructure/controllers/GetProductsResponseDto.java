package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers;

import java.util.List;

public record GetProductsResponseDto(List<GetProductsElementDto> items) {
}
