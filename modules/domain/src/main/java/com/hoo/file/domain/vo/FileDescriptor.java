package com.hoo.file.domain.vo;

import com.hoo.common.enums.FileStatus;

import java.time.ZonedDateTime;

public record FileDescriptor(
        Long size,
        String realName,
        FileStatus status,
        ZonedDateTime createdTime,
        ZonedDateTime updatedTime
) {

    public static FileDescriptor of(Long size, String name, FileStatus status) {
        return new FileDescriptor(size, name, status, ZonedDateTime.now(), ZonedDateTime.now());
    }
}
