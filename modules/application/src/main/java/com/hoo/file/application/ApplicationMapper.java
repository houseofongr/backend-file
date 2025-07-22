package com.hoo.file.application;

import com.hoo.common.internal.api.file.dto.FileInfo;
import com.hoo.common.internal.api.file.dto.FileMetadata;
import com.hoo.file.domain.File;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ApplicationMapper {

    public FileInfo mapToFileInfo(File file, URI fileUrl) {

        return new FileInfo(
                file.getId().uuid(),
                fileUrl,
                new FileMetadata(
                        file.getDomain(),
                        file.getMediaInfo().contentType(),
                        file.getFileDescriptor().realName(),
                        file.getFileDescriptor().size(),
                        file.getAccessControlInfo().accessLevel()
                )
        );
    }

    public List<FileInfo> mapToFileInfo(List<File> files, Map<UUID, URI> fileUrls) {

        return files.stream().map(file -> mapToFileInfo(file, fileUrls.get(file.getId().uuid()))).toList();
    }
}
