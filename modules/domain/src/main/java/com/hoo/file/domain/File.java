package com.hoo.file.domain;

import com.hoo.common.enums.AccessLevel;
import com.hoo.common.enums.FileStatus;
import com.hoo.file.domain.event.FileCreateEvent;
import com.hoo.file.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "id")
public class File {

    private final FileID id;
    private final FileDescriptor fileDescriptor;
    private final Location location;
    private final MediaInfo mediaInfo;
    private final AccessControlInfo accessControlInfo;
    private InputStream inputStream;

    public static FileCreateEvent createFile(FileID fileID,
                                             Long size, String realName,
                                             String endpoint, String bucket, String domain,
                                             String contentType,
                                             UUID ownerID, AccessLevel accessLevel
    ) {

        MediaInfo media = MediaInfo.of(contentType);
        return new FileCreateEvent(new File(
                fileID,
                FileDescriptor.of(size, realName, FileStatus.CREATED),
                Location.of(bucket, domain, media.mediaType(), fileID, realName),
                media,
                new AccessControlInfo(ownerID, accessLevel),
                null
        ));
    }

    public void addInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getLocationUrl() {
        return location.url();
    }

    public record FileID(UUID uuid) {
    }
}
