package com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController;

import com.acidtango.inditex.backendtest.store.products.application.ListProducts;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.ProductWithStock;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria.ListProductStockOrderCriteria;
import com.acidtango.inditex.backendtest.store.products.domain.readmodel.criteria.OrderWeight;
import com.acidtango.inditex.backendtest.store.products.infrastructure.controllers.GetProductsController.DTO.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class GetProductsController {
    final ListProducts listProducts;

    public GetProductsController(ListProducts listProducts) {
        this.listProducts = listProducts;
    }

    @Operation(summary = "Lists the products along with the stock for each variant")
    @GetMapping
    GetProductsResponseDto index(@ParameterObject GetProductsRequestDto getProductsRequestDto) {
        var orderCriteria = new ListProductStockOrderCriteria(
            getProductsRequestDto.salesUnitsEpsilon().map(OrderWeight::new).orElse(OrderWeight.unit()),
            getProductsRequestDto.stockEpsilon().map(OrderWeight::new).orElse(OrderWeight.unit())
        );

        var result = listProducts.execute(orderCriteria);

        return toResponseDto(result);
    }

    private static GetProductsResponseDto toResponseDto(List<ProductWithStock> result) {
        var items = result
            .stream()
            .map(productWithStock -> new GetProductsElementDto(
                productWithStock.id().getId(),
                productWithStock.name(),
                productWithStock.salesUnits(),
                new VariantsStockResponseDto(
                    new VariantStockDto(productWithStock.variantsStock().large().id(), productWithStock.variantsStock().large().amount()),
                    new VariantStockDto(productWithStock.variantsStock().medium().id(), productWithStock.variantsStock().medium().amount()),
                    new VariantStockDto(productWithStock.variantsStock().small().id(), productWithStock.variantsStock().small().amount())
                )
            )).toList();

        return new GetProductsResponseDto(items);
    }
}
