package com.hoo.file.adapter.out.storage;

import com.hoo.file.api.out.StoreFilePort;
import com.hoo.file.application.StorageProperties;
import com.hoo.file.application.exception.AdapterErrorCode;
import com.hoo.file.application.exception.FileAdapterException;
import com.hoo.file.domain.File;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MinioStorageAdapter implements StoreFilePort {

    private final MinioClient minioClient;
    private final StorageProperties storageProperties;

    @Override
    public void storeFile(File file) {

        try {
            ObjectWriteResponse response = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(file.getLocation().bucket())
                            .object(file.getLocation().storageKey())
                            .stream(file.getInputStream(), file.getFileDescriptor().size(), storageProperties.partSize())
                            .build()
            );

            log.info("file upload succeed in bucket : {}", response.bucket());

        } catch (Exception e) {
            log.error("miniO file upload error : {}", e.getStackTrace());
            throw new FileAdapterException(AdapterErrorCode.UPLOAD_FAILED);
        }
    }
}
