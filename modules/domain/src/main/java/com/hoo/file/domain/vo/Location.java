package com.hoo.file.domain.vo;

import com.hoo.file.domain.File;

import java.net.URI;

public record Location(
        URI endpoint,
        Bucket bucket,
        String storageKey
) {
    public static String createStorageKey(File.FileID fileID, String midpoint, String realName) {

        String prefix = (midpoint == null || midpoint.isBlank()) ? "" : midpoint.strip();
        if (!prefix.isEmpty() && !prefix.endsWith("/")) {
            prefix += "/";
        }

        String ext = extension(realName);

        if (ext.isEmpty()) return prefix + fileID.uuid();
        else return prefix + fileID.uuid() + "." + ext;
    }

    public static String extension(String filename) {

        if (filename == null) return "";
        int index = filename.lastIndexOf('.');

        if (index == -1 || index == filename.length() - 1) return "";
        return filename.substring(index + 1).toLowerCase();
    }
}
