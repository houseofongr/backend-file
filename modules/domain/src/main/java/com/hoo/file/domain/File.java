package com.hoo.file.domain;

import com.hoo.common.enums.AccessLevel;
import com.hoo.common.enums.FileStatus;
import com.hoo.file.domain.event.FileCreateEvent;
import com.hoo.file.domain.vo.AccessControlInfo;
import com.hoo.file.domain.vo.FileDescriptor;
import com.hoo.file.domain.vo.Location;
import com.hoo.file.domain.vo.MediaInfo;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.InputStream;
import java.security.SecureRandom;
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
                                             String bucket, String domain,
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

    public static File load(FileID fileID, FileDescriptor fileDescriptor, Location location, MediaInfo mediaInfo, AccessControlInfo accessControlInfo) {
        return new File(fileID, fileDescriptor, location, mediaInfo, accessControlInfo, null);
    }

    public void addInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getBucketIncludedUrl() {
        return location.url();
    }

    public String getUrl() {
        return location.storageKey();
    }

    public String getDomain() {
        return location.domain();
    }

    public boolean isPublic() {
        return accessControlInfo.accessLevel() == AccessLevel.PUBLIC;
    }

    public boolean isMine(UUID ownerID) {
        return accessControlInfo.ownerID().equals(ownerID);
    }

    public String generateRandomToken() {
        SecureRandom random = new SecureRandom();
        return Long.toHexString(random.nextLong());
    }

    public record FileID(UUID uuid) {
    }
}
