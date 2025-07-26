package com.hoo.file.adapter.out.cache;

import com.hoo.common.enums.AccessLevel;
import com.hoo.file.api.out.CacheTempUrlPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;

import static com.hoo.common.enums.CacheKeys.*;

@RequiredArgsConstructor
public class RedisAdapter implements CacheTempUrlPort {

    private final RedisTemplate<String, String> redisTemplate;
    private final StorageProperties storageProperties;

    @Override
    public void cacheUrl(String token, URI tempUrl, AccessLevel accessLevel) {

        if (accessLevel == AccessLevel.PUBLIC) {
            valueOperations().set(FILE_TEMP_URL_PREFIX.getKey() + tempUrl, token, Duration.ofMinutes(storageProperties.publicUrlExpireMin()));
            valueOperations().set(FILE_TOKEN_PREFIX.getKey() + token, tempUrl.toString(), Duration.ofMinutes(storageProperties.publicUrlExpireMin()));
        } else {
            valueOperations().set(FILE_TEMP_URL_PREFIX.getKey() + tempUrl, token, Duration.ofMinutes(storageProperties.privateUrlExpireMin()));
            valueOperations().set(FILE_TOKEN_PREFIX.getKey() + token, tempUrl.toString(), Duration.ofMinutes(storageProperties.privateUrlExpireMin()));
        }
    }

    @Override
    public Optional<URI> loadUrl(String token) {

        String tempUrl = valueOperations().get(FILE_TOKEN_PREFIX.getKey() + token);

        return Optional.ofNullable(tempUrl).map(URI::create);
    }

    @Override
    public Optional<String> loadToken(URI fileUrl) {

        String token = valueOperations().get(FILE_TEMP_URL_PREFIX.getKey() + fileUrl.toString());

        return Optional.ofNullable(token);
    }

    private ValueOperations<String, String> valueOperations() {
        return redisTemplate.opsForValue();
    }
}
