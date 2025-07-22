package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.domain.File;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Sql("classpath:sql/file.sql")
@PersistenceAdapterTest
class LoadFileAdapterTest {

    @Autowired
    LoadFileAdapter sut;

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @DisplayName("파일 로드")
    void loadFile() {
        // given
        UUID uuid = fileJpaRepository.findUuidById(1L);

        // when
        File file = sut.loadFile(uuid);

        // then
        assertThat(file.getId().uuid()).isEqualTo(uuid);
    }

    @Test
    @DisplayName("파일 전체 로드")
    void loadAllFiles() {
        // given
        UUID uuid = fileJpaRepository.findUuidById(1L);

        // when
        List<File> files = sut.loadAllFiles(List.of(uuid));

        // then
        assertThat(files.getFirst().getId().uuid()).isEqualTo(uuid);
    }
}