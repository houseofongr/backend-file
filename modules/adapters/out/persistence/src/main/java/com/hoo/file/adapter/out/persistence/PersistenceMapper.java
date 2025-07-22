package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.entity.vo.AccessControlInfoJpaValue;
import com.hoo.file.adapter.out.persistence.entity.vo.FileDescriptorJpaValue;
import com.hoo.file.adapter.out.persistence.entity.vo.LocationJpaValue;
import com.hoo.file.adapter.out.persistence.entity.vo.MediaInfoJpaValue;
import com.hoo.file.domain.File;
import com.hoo.file.domain.vo.AccessControlInfo;
import com.hoo.file.domain.vo.FileDescriptor;
import com.hoo.file.domain.vo.Location;
import com.hoo.file.domain.vo.MediaInfo;

public class PersistenceMapper {

    public File mapToFile(FileJpaEntity fileJpaEntity) {
        return File.load(
                new File.FileID(fileJpaEntity.getUuid()),
                mapToFileDescriptor(fileJpaEntity.getFileDescriptor()),
                mapToLocation(fileJpaEntity.getLocation()),
                mapToMediaInfo(fileJpaEntity.getMediaInfo()),
                mapToAccessControlInfo(fileJpaEntity.getAccessControlInfo())
        );
    }

    private FileDescriptor mapToFileDescriptor(FileDescriptorJpaValue fileDescriptor) {
        return new FileDescriptor(
                fileDescriptor.getSize(),
                fileDescriptor.getRealName(),
                fileDescriptor.getStatus(),
                fileDescriptor.getCreatedTime(),
                fileDescriptor.getUpdatedTime()
        );
    }

    private Location mapToLocation(LocationJpaValue location) {
        return new Location(
                location.getBucket(),
                location.getDomain(),
                location.getStorageKey()
        );
    }

    private MediaInfo mapToMediaInfo(MediaInfoJpaValue mediaInfo) {
        return new MediaInfo(
                mediaInfo.getContentType(),
                mediaInfo.getMediaType()
        );
    }

    private AccessControlInfo mapToAccessControlInfo(AccessControlInfoJpaValue accessControlInfo) {
        return new AccessControlInfo(
                accessControlInfo.getOwnerID(),
                accessControlInfo.getAccessLevel());
    }
}
