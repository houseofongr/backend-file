package com.hoo.file.application;

import com.hoo.common.enums.AccessLevel;
import com.hoo.file.api.out.CacheTempUrlPort;
import com.hoo.file.api.out.GenerateUrlPort;
import com.hoo.file.api.out.GetProxyUrlPort;
import com.hoo.file.application.exception.ApplicationErrorCode;
import com.hoo.file.application.exception.FileApplicationException;
import com.hoo.file.domain.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetProxyUrlService implements GetProxyUrlPort {

    private final GenerateUrlPort generateUrlPort;
    private final CacheTempUrlPort cacheTempUrlPort;
    private final StorageProperties storageProperties;
    private final ServerProperties serverProperties;

    @Override
    public URI getPublicUrl(File file) {

        Optional<String> key = cacheTempUrlPort.loadToken(getFileUrl(file));

        if (key.isPresent()) return getTokenUrl(key.get());

        URI generated = generateUrlPort.generatePublicUrl(file);
        String token = generateRandomToken();
        cacheTempUrlPort.cacheUrl(token, generated, file.getAccessControlInfo().accessLevel());

        return getTokenUrl(token);
    }

    @Override
    public URI getPrivateUrl(File file) {

        Optional<String> key = cacheTempUrlPort.loadToken(getFileUrl(file));

        if (key.isPresent()) return getTokenUrl(key.get());

        URI generated = generateUrlPort.generatePrivateUrl(file);
        String token = generateRandomToken();
        cacheTempUrlPort.cacheUrl(token, generated, file.getAccessControlInfo().accessLevel());

        return getTokenUrl(token);
    }

    @Override
    public URI getTempUrl(String token) {

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
            URI url = (file.getAccessControlInfo().accessLevel() == AccessLevel.PUBLIC) ?
                    getPublicUrl(file) : getPrivateUrl(file);
            urlMap.put(file.getId().uuid(), url);
        }

        return urlMap;
    }

    private URI getFileUrl(File file) {
        return URI.create(storageProperties.endpoint() + "/" + file.getBucketIncludedUrl());
    }

    private URI getTokenUrl(String token) {
        return URI.create(serverProperties.baseUrl() + "/" + token);
    }

    private void validateToken(String token) {

        if (!Pattern.compile("^-?[0-9a-f]+$").matcher(token).matches())
            throw new FileApplicationException(ApplicationErrorCode.BAD_FILE_TOKEN_URL);
    }

    private String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        return Long.toHexString(random.nextLong());
    }
}
