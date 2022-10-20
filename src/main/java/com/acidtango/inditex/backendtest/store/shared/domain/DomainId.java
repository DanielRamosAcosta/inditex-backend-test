package com.acidtango.inditex.backendtest.store.shared.domain;

import com.acidtango.inditex.backendtest.shared.domain.ValueObject;

public class DomainId extends ValueObject {
    final Integer id;

    public DomainId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
