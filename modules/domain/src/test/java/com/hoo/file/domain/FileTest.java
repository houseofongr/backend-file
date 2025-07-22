package com.hoo.file.domain;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.AccessLevel;
import com.hoo.common.enums.FileStatus;
import com.hoo.common.enums.MediaType;
import com.hoo.file.domain.event.FileCreateEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FileTest {

    @Test
    @DisplayName("파일 생성")
    void createFile() {
        // given
        UUID fileID = UuidCreator.getTimeOrderedEpoch();
        ZonedDateTime now = ZonedDateTime.now();

        // when
        FileCreateEvent event = File.createFile(
                new File.FileID(fileID),
                1000L, "realName.png",
                "media", "/images",
                "image/png",
                UuidCreator.getTimeOrderedEpoch(), AccessLevel.PUBLIC
        );

        // then
        File newFile = event.newFile();
        assertThat(newFile.getId().uuid()).isEqualTo(fileID);
        assertThat(newFile.getFileDescriptor().realName()).isEqualTo("realName.png");
        assertThat(newFile.getFileDescriptor().status()).isEqualTo(FileStatus.CREATED);
        assertThat(newFile.getFileDescriptor().createdTime()).isAfter(now);
        assertThat(newFile.getFileDescriptor().updatedTime()).isAfter(now);
        assertThat(newFile.getMediaInfo().mediaType()).isEqualTo(MediaType.IMAGES);
        assertThat(newFile.getMediaInfo().contentType()).isEqualTo("image/png");
        assertThat(newFile.getAccessControlInfo().accessLevel()).isEqualTo(AccessLevel.PUBLIC);
        assertThat(newFile.getLocation().bucket()).isEqualTo("media");
        assertThat(newFile.getLocation().storageKey()).contains("/images");
        assertThat(newFile.getLocation().storageKey()).contains(newFile.getId().uuid().toString());
        assertThat(newFile.getLocation().storageKey()).contains(".png");
    }
}