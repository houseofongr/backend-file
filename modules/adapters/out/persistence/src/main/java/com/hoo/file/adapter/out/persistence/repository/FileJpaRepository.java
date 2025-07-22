package com.hoo.file.adapter.out.persistence.repository;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {
    Optional<FileJpaEntity> findByUuid(UUID uuid);
}
