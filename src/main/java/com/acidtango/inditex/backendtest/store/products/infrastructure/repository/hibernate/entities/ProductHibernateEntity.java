package com.acidtango.inditex.backendtest.store.products.infrastructure.repository.hibernate.entities;

import com.acidtango.inditex.backendtest.store.products.domain.Product;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class ProductHibernateEntity {
    @Id
    @GeneratedValue
    Integer id;

    String name;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<ProductVariantHibernateEntity> variants;

    ProductHibernateEntity () {}

    public static ProductHibernateEntity fromDomain(Product product) {
        var primitives = product.toPrimitives();
        ProductHibernateEntity productHibernateEntity = new ProductHibernateEntity();
        productHibernateEntity.id = primitives.id();
        productHibernateEntity.name = primitives.name();

        productHibernateEntity.variants = primitives
                .variants()
                .stream()
                .map(variant -> ProductVariantHibernateEntity.fromDomain(productHibernateEntity, variant))
                .toList();
        return productHibernateEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductHibernateEntity that = (ProductHibernateEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(variants, that.variants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, variants);
    }
}
