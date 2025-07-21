package com.hoo.file.domain;

import com.hoo.common.enums.AccessLevel;
import com.hoo.common.enums.FileStatus;
import com.hoo.file.domain.event.FileCreateEvent;
import com.hoo.file.domain.vo.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.net.URI;
import java.time.ZonedDateTime;
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

    public static FileCreateEvent createFile(FileID fileID,
                                             Long size, String realName,
                                             String endpoint, Bucket bucket, String midpoint,
                                             String contentType,
                                             UUID ownerID, AccessLevel accessLevel
    ) {
        return new FileCreateEvent(new File(
                fileID,
                new FileDescriptor(size, realName, FileStatus.CREATED, ZonedDateTime.now(), ZonedDateTime.now()),
                new Location(URI.create(endpoint), bucket, Location.createStorageKey(fileID, midpoint, realName)),
                MediaInfo.of(contentType),
                new AccessControlInfo(ownerID, accessLevel)
        ));
    }

    public record FileID(UUID uuid) {
    }
}
