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

class GetProxyUrlInServiceTest {

    GenerateUrlPort generateUrlPort = mock();
    CacheTempUrlPort cacheTempUrlPort = mock();
    ApplicationProperties applicationProperties = mock();

    GetProxyUrlInService sut = new GetProxyUrlInService(generateUrlPort, cacheTempUrlPort, applicationProperties);

    @Test
    @DisplayName("퍼블릭 URI 신규 발급")
    void generatePublicUrl() {
        // given
        File file = defaultFile().build();
        String fileDomain = "http://file.domain";
        String storageDomain = "http://minio.domain";

        // when
        when(cacheTempUrlPort.loadToken(any())).thenReturn(Optional.empty());
        when(generateUrlPort.generatePublicUrl(any())).thenReturn(URI.create(storageDomain + "/media/test.png"));
        when(applicationProperties.gatewayEndpoint()).thenReturn(fileDomain);
        URI publicUrl = sut.getProxyUrl(file);

        // then
        verify(generateUrlPort, times(1)).generatePublicUrl(any());
        verify(cacheTempUrlPort, times(1)).cacheUrl(any(), any(), any());
        assertThat(publicUrl.toString()).contains(fileDomain);
    }

    @Test
    @DisplayName("퍼블릭 URI 캐시 발급")
    void getPublicUrlByCache() {
        // given
        File file = defaultFile().build();
        String generatedKey = "fbcd947ebb8c3a89";
        String fileDomain = "http://file.domain";

        // when
        when(cacheTempUrlPort.loadToken(any())).thenReturn(Optional.of(generatedKey));
        when(applicationProperties.gatewayEndpoint()).thenReturn(fileDomain);
        URI publicUrl = sut.getProxyUrl(file);

        // then
        assertThat(publicUrl.toString()).isEqualTo(fileDomain + "/files/" + generatedKey);
    }
    
    @Test
    @DisplayName("토큰으로부터 실제 URL 발급")
    void getRealFileUrl() {
        // given
        String token = "abcd1234";
        String storageDomain = "http://minio.domain";

        // when
        when(cacheTempUrlPort.loadUrl(token)).thenReturn(Optional.of(URI.create(storageDomain + "/files/123")));
        when(applicationProperties.storageEndpoint()).thenReturn(storageDomain);
        URI realFileUrl = sut.getRealFileUrl(token);

        // then
        assertThat(realFileUrl.toString()).contains(storageDomain);
    }

    @Nested
    @DisplayName("parseTokenUrl 예외 케이스")
    class ParseTokenUrlException {

        @Test
        @DisplayName("토큰이 16진수가 아님")
        void tokenNotHex() {
            assertThatThrownBy(() -> sut.getRealFileUrl("xyz123"))
                    .isInstanceOf(FileApplicationException.class)
                    .hasMessage(ApplicationErrorCode.BAD_FILE_TOKEN_URL.getMessage());
        }

        @Test
        @DisplayName("토큰이 null")
        void uriIsNull() {
            assertThatThrownBy(() -> sut.getRealFileUrl(null))
                    .isInstanceOf(FileApplicationException.class)
                    .hasMessage(ApplicationErrorCode.BAD_FILE_TOKEN_URL.getMessage());
        }
    }

}