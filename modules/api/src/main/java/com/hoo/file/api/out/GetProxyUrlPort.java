package com.hoo.file.api.out;

import com.hoo.file.domain.File;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GetProxyUrlPort {
    URI getPublicUrl(File file);

    URI getPrivateUrl(File file);

    URI getTempUrl(String token);

    Map<UUID, URI> getUrlMap(List<File> files);
}
