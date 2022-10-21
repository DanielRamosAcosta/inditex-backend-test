package com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers.PostStockController;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.application.Restock;
import com.acidtango.inditex.backendtest.store.stock.domain.StockAmount;
import com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers.PostStockController.DTO.PostStockRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostStockController {
    final Restock restock;

    public PostStockController(Restock restock) {
        this.restock = restock;
    }

    @PostMapping("/{productId}/variants/{variantId}/stock")
    @ResponseStatus(HttpStatus.CREATED)
    void create(
            @PathVariable Integer productId,
            @PathVariable Integer variantId,
            @RequestBody PostStockRequestDto postStockRequestDto
    ) {
        restock.execute(
            new ProductId(productId),
            new VariantId(variantId),
            new StockAmount(postStockRequestDto.amount())
        );
    }
}
