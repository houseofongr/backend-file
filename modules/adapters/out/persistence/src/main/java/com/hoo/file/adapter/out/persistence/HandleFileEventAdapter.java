package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.api.out.HandleFileEventPort;
import com.hoo.file.domain.event.FileCreateEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HandleFileEventAdapter implements HandleFileEventPort {

    private final FileJpaRepository fileJpaRepository;

    @Override
    public void handleCreateFile(FileCreateEvent event) {

        fileJpaRepository.save(FileJpaEntity.createNewEntity(event.newFile()));
    }
}
