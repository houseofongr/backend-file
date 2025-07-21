package com.hoo.file.domain.vo;

import com.hoo.common.enums.MediaType;

public record MediaInfo(
        String contentType,
        MediaType mediaType
) {

    public static MediaInfo of(String contentType) {

        return new MediaInfo(contentType, MediaType.of(contentType));
    }

}
