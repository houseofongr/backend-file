package com.hoo.file.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(
        String storageEndpoint,
        String gatewayEndpoint,
        Bucket bucket
) {

    public record Bucket(
            String document,
            String media
    ) {

    }
}
