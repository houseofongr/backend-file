package com.hoo.file.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "server")
public record ServerProperties(
        String baseUrl
) {
}
