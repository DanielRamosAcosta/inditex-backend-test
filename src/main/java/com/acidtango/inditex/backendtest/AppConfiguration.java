package com.acidtango.inditex.backendtest;

import com.acidtango.inditex.backendtest.shared.domain.EventBus;
import com.acidtango.inditex.backendtest.store.shared.infrastructure.events.spring.EventBusSpring;
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
}
