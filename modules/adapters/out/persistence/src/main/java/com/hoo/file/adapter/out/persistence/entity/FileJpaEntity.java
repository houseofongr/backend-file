package com.hoo.file.adapter.out.persistence.entity;

import com.hoo.file.adapter.out.persistence.entity.vo.AccessControlInfoJpaValue;
import com.hoo.file.adapter.out.persistence.entity.vo.FileDescriptorJpaValue;
import com.hoo.file.adapter.out.persistence.entity.vo.LocationJpaValue;
import com.hoo.file.adapter.out.persistence.entity.vo.MediaInfoJpaValue;
import com.hoo.file.domain.File;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "FILE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    @Embedded
    FileDescriptorJpaValue fileDescriptor;

    @Embedded
    LocationJpaValue location;

    @Embedded
    MediaInfoJpaValue mediaInfo;

    @Embedded
    AccessControlInfoJpaValue accessControlInfo;


    public static FileJpaEntity createNewEntity(File file) {

        return new FileJpaEntity(
                null,
                file.getId().uuid(),
                FileDescriptorJpaValue.from(file.getFileDescriptor()),
                LocationJpaValue.from(file.getLocation()),
                MediaInfoJpaValue.from(file.getMediaInfo()),
                AccessControlInfoJpaValue.from(file.getAccessControlInfo())
        );
    }
}
