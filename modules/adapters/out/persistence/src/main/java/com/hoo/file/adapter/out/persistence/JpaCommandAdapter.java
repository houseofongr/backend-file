package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.api.out.SaveFilePort;
import com.hoo.file.domain.File;
import com.hoo.file.domain.event.FileCreateEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaCommandAdapter implements SaveFilePort {

    private final FileJpaRepository fileJpaRepository;

    @Override
    public void saveFile(File file) {
        fileJpaRepository.save(FileJpaEntity.createNewEntity(file));
    }
}
