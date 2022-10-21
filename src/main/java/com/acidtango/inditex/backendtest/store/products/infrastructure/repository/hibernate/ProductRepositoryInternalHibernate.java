package com.acidtango.inditex.backendtest.store.products.infrastructure.repository.hibernate;

import com.acidtango.inditex.backendtest.store.products.infrastructure.repository.hibernate.entities.ProductHibernateEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepositoryInternalHibernate extends CrudRepository<ProductHibernateEntity, Integer> {
}
