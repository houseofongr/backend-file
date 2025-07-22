package com.hoo.file.domain.vo;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.enums.MediaType;
import com.hoo.file.domain.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    @DisplayName("Location.of(): 기본 동작 확인")
    void of_shouldCreateLocationWithExpectedValues() {
        UUID fileUuid = UuidCreator.getTimeOrderedEpoch();
        File.FileID fileID = new File.FileID(fileUuid);

        String bucket = "media-files";
        String domain = "user";
        MediaType mediaType = MediaType.IMAGE;
        String realName = "photo.JPG";

        Location location = Location.of(bucket, domain, mediaType, fileID, realName);

        assertThat(location.bucket()).isEqualTo(bucket);
        assertThat(location.storageKey()).isEqualTo("user/image/" + fileUuid + ".jpg");
    }

    @Test
    @DisplayName("Location.of(): 확장자 없는 파일")
    void of_shouldOmitExtensionWhenNotPresent() {
        UUID fileUuid = UuidCreator.getTimeOrderedEpoch();
        File.FileID fileID = new File.FileID(fileUuid);

        Location location = Location.of("media-files", "post", MediaType.VIDEO, fileID, "no_ext");

        assertThat(location.storageKey()).isEqualTo("post/video/" + fileUuid);
    }

    @Test
    @DisplayName("Location.of(): domain이 / 없이 끝나도 정상 작동")
    void of_shouldHandleDomainWithoutTrailingSlash() {
        UUID fileUuid = UuidCreator.getTimeOrderedEpoch();
        File.FileID fileID = new File.FileID(fileUuid);

        Location location = Location.of("media-files", "chat", MediaType.AUDIO, fileID, "sound.mp3");

        assertThat(location.storageKey()).isEqualTo("chat/audio/" + fileUuid + ".mp3");
    }

    @Test
    @DisplayName("Location.of(): 빈 domain → storageKey는 fileID만 포함")
    void of_shouldWorkWithEmptyDomain() {
        UUID fileUuid = UuidCreator.getTimeOrderedEpoch();
        File.FileID fileID = new File.FileID(fileUuid);

        Location location = Location.of("backup-files", "", MediaType.DOCUMENT, fileID, "report.pdf");

        assertThat(location.storageKey()).isEqualTo("default/document/" + fileUuid + ".pdf");
    }

    @Test
    @DisplayName("Location.of(): 확장자 대문자일 때 소문자로 변환되는지 확인")
    void of_shouldConvertUppercaseExtensionToLowercase() {
        UUID fileUuid = UuidCreator.getTimeOrderedEpoch();
        File.FileID fileID = new File.FileID(fileUuid);

        Location location = Location.of("documents", "team", MediaType.DOCUMENT, fileID, "final.DOCX");

        assertThat(location.storageKey()).isEqualTo("team/document/" + fileUuid + ".docx");
    }
}
