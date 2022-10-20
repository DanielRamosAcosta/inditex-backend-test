package com.acidtango.inditex.backendtest.store.orders.domain.primitives;

import java.util.List;

public record OrderPrimitives(Integer orderId, List<OrderLinePrimitives> orderLines) {
}
