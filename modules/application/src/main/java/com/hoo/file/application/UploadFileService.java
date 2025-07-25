package com.hoo.file.application;

import com.hoo.common.IssueIDPort;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.api.file.dto.UploadFileResult;
import com.hoo.file.api.out.GetProxyUrlInCase;
import com.hoo.file.api.out.SaveFilePort;
import com.hoo.file.api.out.StoreFilePort;
import com.hoo.file.application.exception.ApplicationErrorCode;
import com.hoo.file.application.exception.FileApplicationException;
import com.hoo.file.domain.File;
import com.hoo.file.domain.event.FileCreateEvent;
import com.hoo.file.domain.vo.MediaInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadFileService implements UploadFileAPI {

    private final IssueIDPort issueIDPort;
    private final SaveFilePort saveFilePort;
    private final StoreFilePort storeFilePort;
    private final GetProxyUrlInCase getProxyUrlInCase;
    private final ApplicationProperties applicationProperties;

    @Override
    public UploadFileResult uploadFile(UploadFileCommand request) {

        UUID fileID = issueIDPort.issueNewID();

        String bucket = getBucketByContentType(request.fileSource().contentType());
        FileCreateEvent event = File.createFile(
                new File.FileID(fileID),
                request.fileSource().size(),
                request.fileSource().name(),
                bucket,
                request.metadata().domain(),
                request.fileSource().contentType(),
                request.metadata().ownerID(),
                request.metadata().accessLevel()
        );

        File newFile = event.newFile();
        newFile.addInputStream(request.fileSource().inputStream());

        saveFilePort.saveFile(newFile);
        storeFilePort.storeFile(newFile);

        URI url = getProxyUrlInCase.getProxyUrl(newFile);

        return new UploadFileResult(newFile.getId().uuid(), url, newFile.getFileDescriptor().createdTime().toEpochSecond());
    }

    private String getBucketByContentType(String contentType) {

        switch (MediaInfo.of(contentType).mediaType()) {
            case DOCUMENTS -> {
                return applicationProperties.bucket().document();
            }
            case IMAGES, AUDIOS, VIDEOS -> {
                return applicationProperties.bucket().media();
            }
            default -> throw new FileApplicationException(ApplicationErrorCode.NOT_SUPPORTED_MEDIA_TYPE);
        }
    }
}
