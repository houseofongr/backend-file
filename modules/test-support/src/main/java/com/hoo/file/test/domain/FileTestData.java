package com.hoo.file.test.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.file.domain.File;
import com.hoo.file.domain.File.FileID;
import com.hoo.file.domain.vo.*;

import java.util.UUID;

public class FileTestData {

    public static FileBuilder defaultFile() {
        return new FileBuilder()
                .withId(new FileID(UuidCreator.getTimeOrderedEpoch()))
                .withDescriptor(1000L, "realName.png")
                .withLocation("http://localhost:8080", "media", "/images")
                .withContentType("image/png")
                .withAccessControlInfo(UuidCreator.getTimeOrderedEpoch(), AccessLevel.PUBLIC);
    }

    public static class FileBuilder {

        private FileID id;
        private Long size;

        private String endpoint;
        private String bucket;
        private String midpoint;
        private String realName;

        private String contentType;

        private UUID ownerID;
        private AccessLevel accessLevel;


        public FileBuilder withId(FileID fileID) {
            id = fileID;
            return this;
        }

        public FileBuilder withDescriptor(Long size, String name) {
            this.size = size;
            this.realName = name;
            return this;
        }

        public FileBuilder withLocation(String endpoint, String bucket, String midpoint) {
            this.endpoint = endpoint;
            this.bucket = bucket;
            this.midpoint = midpoint;
            return this;
        }

        public FileBuilder withContentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public FileBuilder withAccessControlInfo(UUID ownerID, AccessLevel accessLevel) {
            this.ownerID = ownerID;
            this.accessLevel = accessLevel;
            return this;
        }

        public File build() {
            return File.createFile(id, size, realName, endpoint, bucket, midpoint, contentType, ownerID, accessLevel).newFile();
        }
    }
}
