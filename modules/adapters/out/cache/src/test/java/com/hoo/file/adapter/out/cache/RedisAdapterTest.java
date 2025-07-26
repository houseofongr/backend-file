package com.hoo.file.adapter.out.cache;

import com.hoo.common.enums.AccessLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import static com.hoo.common.enums.CacheKeys.*;
import static org.assertj.core.api.Assertions.assertThat;

@RedisTest
class RedisAdapterTest {

    @Autowired
    RedisAdapter sut;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    void clear() {
        Set<String> keys = redisTemplate.keys("*");
        if (!keys.isEmpty()) redisTemplate.delete(keys);
    }

    @Test
    @DisplayName("임시 파일 URL PUBLIC 저장 및 만료 테스트")
    void testCachePublicUrl() {
        // given
        String token = "test-token";
        URI tempUrl = URI.create("https://temp.file/public.png");

        // when
        sut.cacheUrl(token, tempUrl, AccessLevel.PUBLIC);

        // then
        assertThat(redisTemplate.opsForValue().get(FILE_TEMP_URL_PREFIX.getKey() + tempUrl))
                .isEqualTo(token);
        assertThat(redisTemplate.opsForValue().get(FILE_TOKEN_PREFIX.getKey() + token))
                .isEqualTo(tempUrl.toString());
        assertThat(redisTemplate.getExpire(FILE_TEMP_URL_PREFIX.getKey() + tempUrl))
                .isEqualTo(3600);
        assertThat(redisTemplate.getExpire(FILE_TOKEN_PREFIX.getKey() + token))
                .isEqualTo(3600);
    }

    @Test
    @DisplayName("임시 파일 URL PRIVATE 저장 및 만료 테스트")
    void testCachePrivateUrl() {
        // given
        String token = "test-token";
        URI tempUrl = URI.create("https://temp.file/private.png");

        // when
        sut.cacheUrl(token, tempUrl, AccessLevel.PRIVATE);

        // then
        assertThat(redisTemplate.opsForValue().get(FILE_TEMP_URL_PREFIX.getKey() + tempUrl))
                .isEqualTo(token);
        assertThat(redisTemplate.opsForValue().get(FILE_TOKEN_PREFIX.getKey() + token))
                .isEqualTo(tempUrl.toString());
        assertThat(redisTemplate.getExpire(FILE_TEMP_URL_PREFIX.getKey() + tempUrl))
                .isEqualTo(600);
        assertThat(redisTemplate.getExpire(FILE_TOKEN_PREFIX.getKey() + token))
                .isEqualTo(600);
    }

    @Test
    @DisplayName("토큰으로 임시 URL을 불러올 수 있다")
    void testLoadUrlByToken() {
        // given
        String token = "test-token";
        URI tempUrl = URI.create("https://temp.file/test.png");
        sut.cacheUrl(token, tempUrl, AccessLevel.PUBLIC);

        // when
        Optional<URI> result = sut.loadUrl(token);

        // then
        assertThat(result).isPresent().get().hasToString(tempUrl.toString());
    }

    @Test
    @DisplayName("없는 토큰으로 임시 URL을 불러오면 Optional.empty() 반환")
    void testLoadUrlByInvalidToken() {
        // when
        Optional<URI> result = sut.loadUrl("not-exist-token");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("임시 URL로 토큰을 불러올 수 있다")
    void testLoadTokenByFileUrl() {
        // given
        String token = "test-token";
        URI tempUrl = URI.create("https://temp.file/test.png");
        sut.cacheUrl(token, tempUrl, AccessLevel.PUBLIC);

        // when
        Optional<String> result = sut.loadToken(tempUrl);

        // then
        assertThat(result).isPresent().get().isEqualTo(token);
    }

    @Test
    @DisplayName("없는 URL로 토큰을 불러오면 Optional.empty() 반환")
    void testLoadTokenByInvalidUrl() {
        // when
        Optional<String> result = sut.loadToken(URI.create("https://temp.file/none.png"));

        // then
        assertThat(result).isEmpty();
    }
}