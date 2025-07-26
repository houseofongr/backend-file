package com.hoo.file.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    WebMapper webMapper(ObjectMapper webObjectMapper) {
        return new WebMapper(webObjectMapper);
    }

    @Bean
    ObjectMapper webObjectMapper() {
        return new ObjectMapper();
    }
}
