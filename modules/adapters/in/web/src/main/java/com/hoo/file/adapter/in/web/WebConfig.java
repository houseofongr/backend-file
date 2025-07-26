package com.hoo.file.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class WebConfig {

    @Bean
    RequestMapper requestMapper(ObjectMapper webObjectMapper) {
        return new RequestMapper(webObjectMapper);
    }

    @Bean
    ObjectMapper webObjectMapper() {
        return new ObjectMapper();
    }
}
