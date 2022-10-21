package com.acidtango.inditex.backendtest.store.orders.domain.exceptions;

import com.acidtango.inditex.backendtest.shared.domain.DomainException;
import com.acidtango.inditex.backendtest.store.shared.domain.OrderId;

public class OrderNotFoundException extends DomainException {
    public final OrderId orderId;

    public OrderNotFoundException(OrderId orderId) {
        this.orderId = orderId;
    }
}
