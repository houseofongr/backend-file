package com.hoo.file.domain.vo;

import com.hoo.common.enums.MediaType;
import com.hoo.file.domain.File;

import java.net.URI;

public record Location(
        String bucket,
        String storageKey
) {
    public static Location of(String bucket, String domain, MediaType mediaType, File.FileID fileID, String realName) {
        return new Location(bucket, createStorageKey(domain, mediaType, fileID, realName));
    }

    private static String midpoint(String domain, MediaType mediaType) {

        if (domain == null || domain.isBlank()) domain = "default";
        return domain + "/" + mediaType.name().toLowerCase();
    }

    private static String createStorageKey(String domain, MediaType mediaType, File.FileID fileID, String realName) {

        String midpoint = midpoint(domain, mediaType);

        if (!midpoint.isEmpty() && !midpoint.endsWith("/")) {
            midpoint += "/";
        }

        String ext = extension(realName);

        if (ext.isEmpty()) return midpoint + fileID.uuid();
        else return midpoint + fileID.uuid() + "." + ext;
    }

    private static String extension(String filename) {

        if (filename == null) return "";
        int index = filename.lastIndexOf('.');

        if (index == -1 || index == filename.length() - 1) return "";
        return filename.substring(index + 1).toLowerCase();
    }

    public String url() {
        return bucket + "/" + storageKey;
    }
}
