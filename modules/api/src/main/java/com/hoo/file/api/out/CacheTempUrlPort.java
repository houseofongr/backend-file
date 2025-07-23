package com.hoo.file.api.out;

import com.hoo.common.enums.AccessLevel;

import java.net.URI;
import java.util.Optional;

public interface CacheTempUrlPort {
    void cacheUrl(String token, URI tempUrl, AccessLevel accessLevel);
    Optional<URI> loadUrl(String token);
    Optional<String> loadToken(URI fileUrl);
}
