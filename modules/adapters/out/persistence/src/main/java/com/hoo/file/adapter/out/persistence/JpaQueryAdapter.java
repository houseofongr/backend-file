package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import com.hoo.file.api.out.LoadFilePort;
import com.hoo.file.application.exception.AdapterErrorCode;
import com.hoo.file.application.exception.FileAdapterException;
import com.hoo.file.domain.File;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class JpaQueryAdapter implements LoadFilePort {

    private final FileJpaRepository fileJpaRepository;
    private final PersistenceMapper persistenceMapper;

    @Override
    public File loadFile(UUID fileID) {
        FileJpaEntity fileJpaEntity = fileJpaRepository.findByUuid(fileID)
                .orElseThrow(() -> new FileAdapterException(AdapterErrorCode.FILE_NOT_FOUND));
        return persistenceMapper.mapToFile(fileJpaEntity);
    }

    @Override
    public List<File> loadAllFiles(Collection<UUID> fileIDs) {
        List<FileJpaEntity> fileJpaEntities = fileJpaRepository.findByUuidIn(fileIDs);
        return fileJpaEntities.stream().map(persistenceMapper::mapToFile).toList();
    }
}
