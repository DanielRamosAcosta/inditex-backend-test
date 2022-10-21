package com.acidtango.inditex.backendtest;

import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import com.acidtango.inditex.backendtest.shared.infrastructure.events.spring.EventBusSpring;
import com.acidtango.inditex.backendtest.store.products.domain.ProductRepository;
import com.acidtango.inditex.backendtest.store.products.infrastructure.repository.memory.ProductRepositoryMemory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfiguration {
    private final ApplicationEventPublisher applicationEventPublisher;

    public AppConfiguration(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean
    @Primary
    public EventBus createEventBus() {
        return new EventBusSpring(applicationEventPublisher);
    }

    @Bean
    @Primary
    public ProductRepository createProductRepository() {
        return new ProductRepositoryMemory();
    }
}
