package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers;

import java.util.Optional;

public record GetProductsRequestDto(Optional<Integer> salesUnitsEpsilon, Optional<Integer> stockEpsilon) {
}
