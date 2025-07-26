package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.domain.event.FileCreateEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static com.hoo.file.test.domain.FileTestData.defaultFile;
import static org.assertj.core.api.Assertions.assertThat;

@Sql("classpath:sql/file.sql")
@PersistenceAdapterTest
class JpaCommandAdapterTest {

    @Autowired
    JpaCommandAdapter sut;

    @Autowired
    FileJpaRepository fileJpaRepository;

    @Test
    @DisplayName("파일 생성 이벤트 처리")
    void saveFile() {
        // given
        FileCreateEvent event = new FileCreateEvent(defaultFile().build());
        UUID fileID = event.newFile().getId().uuid();

        // when
        sut.saveFile(event.newFile());

        // then
        assertThat(fileJpaRepository.findByUuid(fileID)).isPresent();
    }

}