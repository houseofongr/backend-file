package com.hoo.file.adapter.out.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public record StorageProperties(
        Integer publicUrlExpireMin,
        Integer privateUrlExpireMin
) {
}
