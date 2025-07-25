package com.hoo.file.application;

import com.hoo.common.internal.api.file.GetFileInfoAPI;
import com.hoo.common.internal.api.file.dto.FileInfo;
import com.hoo.common.internal.api.file.dto.GetFileInfoCommand;
import com.hoo.common.internal.api.file.dto.GetFileInfoCommand.FileOwnership;
import com.hoo.file.api.out.GetProxyUrlInCase;
import com.hoo.file.api.out.LoadFilePort;
import com.hoo.file.application.exception.ApplicationErrorCode;
import com.hoo.file.application.exception.FileApplicationException;
import com.hoo.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetFileInfoService implements GetFileInfoAPI {

    private final LoadFilePort loadFilePort;
    private final GetProxyUrlInCase getProxyUrlInCase;
    private final ApplicationMapper applicationMapper;

    @Override
    public List<FileInfo> getFileInfo(GetFileInfoCommand command) {

        List<UUID> fileIDs = command.fileOwners().stream().map(FileOwnership::fileID).toList();
        List<File> files = loadFilePort.loadAllFiles(fileIDs);

        for (File file : files) {
            if (file.isPublic()) continue;
            for (FileOwnership ownership : command.fileOwners()) {
                if (ownership.fileID() == file.getId().uuid() &&
                    !file.isMine(ownership.ownerID()))
                    throw new FileApplicationException(ApplicationErrorCode.OWNERSHIP_REQUIRED);
            }
        }

        Map<UUID, URI> fileUrls = getProxyUrlInCase.getUrlMap(files);

        return applicationMapper.mapToFileInfo(files, fileUrls);
    }
}
