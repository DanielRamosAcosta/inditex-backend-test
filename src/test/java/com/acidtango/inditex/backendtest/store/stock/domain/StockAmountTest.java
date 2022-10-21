package com.acidtango.inditex.backendtest.store.stock.domain;

import com.acidtango.inditex.backendtest.store.stock.domain.exceptions.InvalidStockAmount;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StockAmountTest {

    @Test
    void that_cannot_be_built_with_negatives() {
        assertThatThrownBy(() -> new StockAmount(-1))
                .isInstanceOf(InvalidStockAmount.class);
    }
}