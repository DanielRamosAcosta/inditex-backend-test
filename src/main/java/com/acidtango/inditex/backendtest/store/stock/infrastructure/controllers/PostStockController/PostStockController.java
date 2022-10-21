package com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers.PostStockController;

import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import com.acidtango.inditex.backendtest.store.stock.application.Restock;
import com.acidtango.inditex.backendtest.store.stock.domain.StockAmount;
import com.acidtango.inditex.backendtest.store.stock.infrastructure.controllers.PostStockController.DTO.PostStockRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostStockController {
    final Restock restock;

    public PostStockController(Restock restock) {
        this.restock = restock;
    }

    @Operation(description = "Restocks the given variant within a product")
    @PostMapping("/{productId}/variants/{variantId}/stock")
    @ResponseStatus(HttpStatus.CREATED)
    void create(
            @Schema(description = "The product id")
            @PathVariable Integer productId,
            @Schema(description = "The variant withing the product that you want to restock, the large size for example")
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
