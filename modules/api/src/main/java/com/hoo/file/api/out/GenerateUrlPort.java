package com.hoo.file.api.out;

import com.hoo.file.domain.File;

import java.net.URI;

public interface GenerateUrlPort {
    URI generatePublicUrl(File file);

    URI generatePrivateUrl(File file);
}
