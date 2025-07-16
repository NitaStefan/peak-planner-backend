package com.example.pk_pl.config;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customJacksonCustomizer() {
        return builder -> builder
                .serializerByType(LocalDateTime.class, new UtcLocalDateTimeSerializer());
    }
}
