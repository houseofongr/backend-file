package com.hoo.file.adapter.out.persistence.repository;

import com.hoo.file.adapter.out.persistence.entity.FileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileJpaRepository extends JpaRepository<FileJpaEntity, Long> {
    Optional<FileJpaEntity> findByUuid(UUID uuid);

    @Query("select f.uuid from FileJpaEntity f where f.id = :id")
    UUID findUuidById(Long id);

    List<FileJpaEntity> findByUuidIn(Collection<UUID> uuids);
}
