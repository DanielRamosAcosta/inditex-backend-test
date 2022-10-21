package com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController;

import com.acidtango.inditex.backendtest.store.orders.application.CreateOrder;
import com.acidtango.inditex.backendtest.store.orders.domain.OrderLines;
import com.acidtango.inditex.backendtest.store.orders.domain.primitives.OrderLinePrimitives;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO.CreateOrderRequestDto;
import com.acidtango.inditex.backendtest.store.orders.infrastructure.controllers.PostOrderController.DTO.CreateOrderResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class PostOrderController  {
    final CreateOrder createOrder;

    public PostOrderController(CreateOrder createOrder) {
        this.createOrder = createOrder;
    }

    @Operation(description = "Places an order that will be sent to the customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponseDto create(@RequestBody CreateOrderRequestDto createOrderRequestDto) {

        var lines = createOrderRequestDto.items().stream().map(orderLine -> new OrderLinePrimitives(orderLine.productId(), orderLine.variantId(), orderLine.amount())).toList();

        var orderId = createOrder.execute(OrderLines.from(lines));

        return new CreateOrderResponseDto(orderId.getId());
    }
}
