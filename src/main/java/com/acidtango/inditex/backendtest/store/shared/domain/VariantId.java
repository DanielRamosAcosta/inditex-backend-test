package com.acidtango.inditex.backendtest.store.shared.domain;

public class VariantId extends DomainId {
    public VariantId(Integer id) {
        super(id);
    }

    public VariantId nextId() {
        return new VariantId(id + 1);
    }
}
