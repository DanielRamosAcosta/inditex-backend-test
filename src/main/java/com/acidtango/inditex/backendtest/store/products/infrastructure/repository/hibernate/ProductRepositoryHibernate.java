package com.acidtango.inditex.backendtest.store.products.infrastructure.repository.hibernate;

import com.acidtango.inditex.backendtest.store.products.domain.Product;
import com.acidtango.inditex.backendtest.store.products.domain.ProductRepository;
import com.acidtango.inditex.backendtest.store.products.infrastructure.repository.hibernate.entities.ProductHibernateEntity;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class ProductRepositoryHibernate implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final ProductRepositoryInternalHibernate productRepositoryInternalHibernate;

    public ProductRepositoryHibernate(ProductRepositoryInternalHibernate productRepositoryInternalHibernate) {
        this.productRepositoryInternalHibernate = productRepositoryInternalHibernate;
    }

    private Integer randomNumber() {
        var lastId = Optional.ofNullable(
            entityManager
                    .createQuery("SELECT MAX(id) FROM ProductHibernateEntity", Integer.class)
                    .getSingleResult()
        );

        return lastId.map(n -> n + 1).orElse(1);
    }

    @Override
    public ProductId getNextId() {
        var lastId = Optional.ofNullable(
                entityManager
                        .createQuery("SELECT MAX(id) FROM ProductHibernateEntity", Integer.class)
                        .getSingleResult()
        );

        return new ProductId(lastId.map(n -> n + 1).orElse(1));
    }

    @Override
    public VariantId getNextVariantId() {
        var lastId = Optional.ofNullable(
                entityManager
                        .createQuery("SELECT MAX(id) FROM ProductVariantHibernateEntity", Integer.class)
                        .getSingleResult()
        );

        return new VariantId(lastId.map(n -> n + 1).orElse(1));
    }

    @Override
    public void save(Product product) {
        ProductHibernateEntity productHibernateEntity = ProductHibernateEntity.fromDomain(product);

        productRepositoryInternalHibernate.save(productHibernateEntity);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return Optional.empty();
    }
}
