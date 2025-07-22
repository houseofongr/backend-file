package com.hoo.file.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public record StorageProperties(
        String endpoint,
        Buckets bucket,
        String accessKey,
        String secretKey,
        Long partSize,
        Integer publicUrlExpireMin,
        Integer privateUrlExpireMin
) {
    public record Buckets(
            String media,
            String document,
            String log,
            String backup
    ) {

    }
}
