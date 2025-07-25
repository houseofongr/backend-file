package com.hoo.file.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public record ApplicationProperties(
        String externalDomain,
        String fileServerEndpoint,
        Bucket bucket
) {

    public record Bucket(
            String document,
            String media
    ) {

    }
}
