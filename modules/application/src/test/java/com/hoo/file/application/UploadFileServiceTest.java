package com.hoo.file.application;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.IssueIDPort;
import com.hoo.common.enums.AccessLevel;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.file.api.out.GenerateUrlPort;
import com.hoo.file.api.out.HandleFileEventPort;
import com.hoo.file.api.out.StoreFilePort;
import com.hoo.file.domain.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.UUID;

import static org.mockito.Mockito.*;

class UploadFileServiceTest {

    IssueIDPort issueIDPort = mock();
    StorageProperties storageProperties = new StorageProperties("http://localhost:8080", new StorageProperties.Buckets("media", "document", "log", "backup"), "access", "secret", 5 * 1024 * 1024L);
    HandleFileEventPort handleFileEventPort = mock();
    StoreFilePort storeFilePort = mock();
    GenerateUrlPort generateUrlPort= mock();

    UploadFileService sut = new UploadFileService(issueIDPort, storageProperties, handleFileEventPort, storeFilePort, generateUrlPort);

    @Test
    @DisplayName("파일 업로드 서비스")
    void uploadFileService() {
        // given
        InputStream inputStream = InputStream.nullInputStream();
        UploadFileCommand command = new UploadFileCommand(
                new UploadFileCommand.FileSource(inputStream, "image/png", "image.png", 1000L),
                new UploadFileCommand.Metadata("universe", UuidCreator.getTimeOrderedEpoch(), AccessLevel.PUBLIC)
        );
        UUID newFileID = UuidCreator.getTimeOrderedEpoch();

        // when
        when(issueIDPort.issueNewID()).thenReturn(newFileID);
        sut.uploadFile(command);

        // then
        verify(handleFileEventPort, times(1)).handleCreateFile(any());
        verify(storeFilePort, times(1)).storeFile(any());
        verify(generateUrlPort, times(1)).generatePublicUrl((File) any());
    }

}