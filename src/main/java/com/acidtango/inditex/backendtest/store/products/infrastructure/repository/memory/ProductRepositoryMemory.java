package com.acidtango.inditex.backendtest.store.products.infrastructure.repository.memory;

import com.acidtango.inditex.backendtest.store.orders.domain.Order;
import com.acidtango.inditex.backendtest.store.products.domain.Product;
import com.acidtango.inditex.backendtest.store.products.domain.ProductRepository;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductPrimitives;
import com.acidtango.inditex.backendtest.store.products.domain.primitives.ProductVariantPrimitives;
import com.acidtango.inditex.backendtest.store.shared.domain.ProductId;
import com.acidtango.inditex.backendtest.store.shared.domain.VariantId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Optional;

@Repository
public class ProductRepositoryMemory implements ProductRepository {
    public HashMap<Integer, ProductPrimitives> products = new HashMap<>();

    @Override
    public ProductId getNextId() {
        var value = products.keySet().stream().mapToInt(Integer::intValue).max();

        return new ProductId(value.orElse(0) + 1);
    }

    @Override
    public VariantId getNextVariantId() {
        var value = products
                .values()
                .stream()
                .flatMap(product -> product.variants().stream())
                .mapToInt(ProductVariantPrimitives::id)
                .max();

        return new VariantId(value.orElse(0) + 1);
    }

    @Override
    public void save(Product product) {
        var primitives = product.toPrimitives();

        products.put(primitives.id(), primitives);
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        return Optional.ofNullable(products.get(productId.getId())).map(Product::from);
    }
}
