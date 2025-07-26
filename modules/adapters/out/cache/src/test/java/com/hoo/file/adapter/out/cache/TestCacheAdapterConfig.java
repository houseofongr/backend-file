package com.hoo.file.adapter.out.cache;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@TestConfiguration
@EnableConfigurationProperties(StorageProperties.class)
public class TestCacheAdapterConfig {

    @Bean
    public RedisAdapter redisCacheAdapter(
            RedisTemplate<String, String> redisTemplate,
            StorageProperties storageProperties
    ) {
        return new RedisAdapter(redisTemplate, storageProperties);
    }

    @Bean
    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }
}
