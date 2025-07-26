package com.hoo.file.api.out;

import com.hoo.file.domain.File;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GetProxyUrlInCase {
    URI getProxyUrl(File file);

    URI getRealFileUrl(String token);

    Map<UUID, URI> getUrlMap(List<File> files);
}
