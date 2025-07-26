package com.hoo.file.application;

import com.hoo.common.enums.AccessLevel;
import com.hoo.common.enums.Domain;
import com.hoo.file.api.out.CacheTempUrlPort;
import com.hoo.file.api.out.GenerateUrlPort;
import com.hoo.file.api.out.GetProxyUrlInCase;
import com.hoo.file.application.exception.ApplicationErrorCode;
import com.hoo.file.application.exception.FileApplicationException;
import com.hoo.file.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProxyUrlInService implements GetProxyUrlInCase {

    private final GenerateUrlPort generateUrlPort;
    private final CacheTempUrlPort cacheTempUrlPort;
    private final ApplicationProperties applicationProperties;


    @Override
    public URI getProxyUrl(File file) {
        Optional<String> key = cacheTempUrlPort.loadToken(getRealFileUrl(file));

        if (key.isPresent()) return getTokenUrl(key.get());

        URI realUrl = (file.getAccessControlInfo().accessLevel() == AccessLevel.PUBLIC) ?
                generateUrlPort.generatePublicUrl(file) : generateUrlPort.generatePrivateUrl(file);
        String token = file.generateRandomToken();

        cacheTempUrlPort.cacheUrl(token, realUrl, file.getAccessControlInfo().accessLevel());

        return getTokenUrl(token);
    }

    @Override
    public URI getRealFileUrl(String token) {
        validateToken(token);

        URI loadedUrl = cacheTempUrlPort.loadUrl(token)
                .orElseThrow(() -> new FileApplicationException(ApplicationErrorCode.FILE_TOKEN_EXPIRED));

        log.info("temp url loaded : {}", loadedUrl);

        return loadedUrl;
    }

    @Override
    public Map<UUID, URI> getUrlMap(List<File> files) {
        Map<UUID, URI> urlMap = new HashMap<>();

        for (File file : files) {
            urlMap.put(file.getId().uuid(), getProxyUrl(file));
        }

        return urlMap;
    }

    private URI getRealFileUrl(File file) {
        return URI.create(applicationProperties.storageEndpoint() + "/" + file.getBucketIncludedUrl());
    }

    private URI getTokenUrl(String token) {
        return URI.create(applicationProperties.gatewayEndpoint() + "/" + Domain.FILE.getApiPath() + "/" + token);
    }

    private void validateToken(String token) {
        if (token == null || token.isBlank() || !Pattern.compile("^-?[0-9a-f]+$").matcher(token).matches())
            throw new FileApplicationException(ApplicationErrorCode.BAD_FILE_TOKEN_URL);
    }
}
