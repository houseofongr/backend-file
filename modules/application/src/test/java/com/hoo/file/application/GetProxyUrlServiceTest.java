package com.hoo.file.application;

import com.hoo.file.api.out.CacheTempUrlPort;
import com.hoo.file.api.out.GenerateUrlPort;
import com.hoo.file.application.exception.ApplicationErrorCode;
import com.hoo.file.application.exception.FileApplicationException;
import com.hoo.file.domain.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Optional;

import static com.hoo.file.test.domain.FileTestData.defaultFile;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProxyUrlServiceTest {

    GenerateUrlPort generateUrlPort = mock();
    CacheTempUrlPort cacheTempUrlPort = mock();
    StorageProperties storageProperties = mock();
    ServerProperties serverProperties = mock();

    GetProxyUrlService sut = new GetProxyUrlService(generateUrlPort, cacheTempUrlPort, storageProperties, serverProperties);

    @Test
    @DisplayName("퍼블릭 URI 신규 발급")
    void generateTempUrl() {
        // given
        File file = defaultFile().build();

        // when
        when(cacheTempUrlPort.loadToken(any())).thenReturn(Optional.empty());
        when(serverProperties.baseUrl()).thenReturn("http://example.com/files");
        URI publicUrl = sut.getPublicUrl(file);

        // then
        verify(generateUrlPort, times(1)).generatePublicUrl(any());
        verify(cacheTempUrlPort, times(1)).cacheUrl(any(), any(), any());
        assertThat(publicUrl.toString()).contains("http://example.com/files/");
    }

    @Test
    @DisplayName("퍼블릭 URI 캐시 발급")
    void getTempUrl() {
        // given
        File file = defaultFile().build();
        String generatedKey = "fbcd947ebb8c3a89";

        // when
        when(serverProperties.baseUrl()).thenReturn("http://example.com/files");
        when(cacheTempUrlPort.loadToken(any())).thenReturn(Optional.of(generatedKey));
        URI publicUrl = sut.getPublicUrl(file);

        // then
        assertThat(publicUrl.toString()).isEqualTo("http://example.com/files/fbcd947ebb8c3a89");
    }

    @Nested
    @DisplayName("parseTokenUrl 예외 케이스")
    class ParseTokenUrlException {

        @Test
        @DisplayName("토큰이 16진수가 아님")
        void tokenNotHex() {
            assertThatThrownBy(() -> sut.getTempUrl("xyz123"))
                    .isInstanceOf(FileApplicationException.class)
                    .hasMessage(ApplicationErrorCode.BAD_FILE_TOKEN_URL.getMessage());
        }

        @Test
        @DisplayName("토큰이 null")
        void uriIsNull() {
            assertThatThrownBy(() -> sut.getTempUrl(null))
                    .isInstanceOf(FileApplicationException.class)
                    .hasMessage(ApplicationErrorCode.BAD_FILE_TOKEN_URL.getMessage());
        }
    }

}