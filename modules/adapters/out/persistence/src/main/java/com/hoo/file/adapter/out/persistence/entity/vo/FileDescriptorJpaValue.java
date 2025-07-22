package com.hoo.file.adapter.out.persistence.entity.vo;

import com.hoo.common.enums.FileStatus;
import com.hoo.file.domain.vo.FileDescriptor;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FileDescriptorJpaValue {

    @Column
    private Long size;

    @Column
    private String realName;

    @Column
    @Enumerated(EnumType.STRING)
    private FileStatus status;

    @Column
    private ZonedDateTime createdTime;

    @Column
    private ZonedDateTime updatedTime;

    public static FileDescriptorJpaValue from(FileDescriptor fileDescriptor) {

        return new FileDescriptorJpaValue(
                fileDescriptor.size(),
                fileDescriptor.realName(),
                fileDescriptor.status(),
                fileDescriptor.createdTime(),
                fileDescriptor.updatedTime()
        );
    }
}
