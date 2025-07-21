package com.hoo.file.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bucket {

    PUBLIC_MEDIA_FILES("media-files"),
    PRIVATE_MEDIA_FILES("private-media-files"),
    SYSTEM_LOGS("system-logs"),
    SYSTEM_BACKUP("system-backups");

    private final String key;
}
