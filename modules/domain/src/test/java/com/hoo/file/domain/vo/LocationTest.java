package com.hoo.file.domain.vo;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.file.domain.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

    @Test
    @DisplayName("확장자 변환")
    void extensionConvert() {
        assertThat(Location.extension("file.png")).isEqualTo("png");
        assertThat(Location.extension("file.name.with.dots.jpg")).isEqualTo("jpg");
        assertThat(Location.extension("file_without_extension")).isEmpty();
        assertThat(Location.extension("file.")).isEmpty();
        assertThat(Location.extension(null)).isEmpty();
        assertThat(Location.extension("FILE.JPEG")).isEqualTo("jpeg");  // 소문자 변환 확인
    }

    @Test
    @DisplayName("경로키 변환")
    void createStorageKey() {
        UUID fileId = UuidCreator.getTimeOrderedEpoch();

        // 기본 경로 + 확장자
        String path1 = Location.createStorageKey(new File.FileID(fileId), "avatars/user123", "photo.JPG");
        assertThat(path1).isEqualTo(String.format("avatars/user123/%s.jpg", fileId));

        // 경로 끝에 슬래시 없는 경우 자동 추가
        String path2 = Location.createStorageKey(new File.FileID(fileId), "avatars/user123/", "image.png");
        assertThat(path2).isEqualTo(String.format("avatars/user123/%s.png", fileId));

        // midpoint가 빈 문자열이면 fileId + 확장자만
        String path3 = Location.createStorageKey(new File.FileID(fileId), "", "document.pdf");
        assertThat(path3).isEqualTo(String.format("%s.pdf", fileId));

        // 확장자가 없으면 확장자 생략
        String path4 = Location.createStorageKey(new File.FileID(fileId), "prefix", "noextension");
        assertThat(path4).isEqualTo(String.format("prefix/%s", fileId));

        // midpoint가 null인 경우
        String path5 = Location.createStorageKey(new File.FileID(fileId), null, "file.txt");
        assertThat(path5).isEqualTo(fileId + ".txt");
    }

}