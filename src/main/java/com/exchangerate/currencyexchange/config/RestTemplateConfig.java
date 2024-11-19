package com.exchangerate.currencyexchange.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Defines a bean of type 'org.springframework.web.client.RestTemplate' in the application configuration
 */
@Configuration
public class RestTemplateConfig {

    /**
     * @return restTemplate
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
        return templateBuilder.build();
    }
}
