package com.hoo.file.adapter.out.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public record StorageProperties(
        String endpoint,
        String accessKey,
        String secretKey,
        Long partSize,
        Integer publicUrlExpireMin,
        Integer privateUrlExpireMin
) {
}
