package com.acidtango.inditex.backendtest.store.products.domain.readmodel;

import java.util.List;

public interface ProductReadModelRepository {
    List<ProductWithStock> find();
}
