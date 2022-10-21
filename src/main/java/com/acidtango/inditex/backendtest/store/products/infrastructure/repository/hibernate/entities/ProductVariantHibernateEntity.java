package com.acidtango.inditex.backendtest.store.products.infrastructure.repository.hibernate.entities;

import com.acidtango.inditex.backendtest.store.products.domain.ProductSize;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductVariantPrimitives;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "variants")
public class ProductVariantHibernateEntity {
    @Id
    @GeneratedValue
    Integer id;

    @ManyToOne
    ProductHibernateEntity product;

    @Enumerated(EnumType.STRING)
    ProductSize size;

    ProductVariantHibernateEntity () {}

    public static ProductVariantHibernateEntity fromDomain(ProductHibernateEntity parent, ProductVariantPrimitives variant) {
        ProductVariantHibernateEntity productVariantHibernateEntity = new ProductVariantHibernateEntity();
        var offset = 1000;
        productVariantHibernateEntity.id = variant.id() + offset;
        productVariantHibernateEntity.size = variant.size();
        productVariantHibernateEntity.product = parent;
        return productVariantHibernateEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVariantHibernateEntity that = (ProductVariantHibernateEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product);
    }
}
