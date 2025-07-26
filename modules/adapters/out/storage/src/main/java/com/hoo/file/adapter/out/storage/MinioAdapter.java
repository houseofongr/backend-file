package com.hoo.file.adapter.out.storage;

import com.hoo.file.api.out.GenerateUrlPort;
import com.hoo.file.api.out.StoreFilePort;
import com.hoo.file.application.exception.AdapterErrorCode;
import com.hoo.file.application.exception.FileAdapterException;
import com.hoo.file.domain.File;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class MinioAdapter implements StoreFilePort, GenerateUrlPort {

    private final MinioClient minioClient;
    private final StorageProperties storageProperties;

    @Override
    public void storeFile(File file) {
        try {
            ObjectWriteResponse response = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(file.getLocation().bucket())
                            .object(file.getUrl())
                            .stream(file.getInputStream(), file.getFileDescriptor().size(), storageProperties.partSize())
                            .contentType(file.getMediaInfo().contentType())
                            .build()
            );

            log.info("file upload succeed in bucket : {}", response.bucket());

        } catch (Exception e) {
            log.error("miniO file upload error occur!", e);
            throw new FileAdapterException(AdapterErrorCode.UPLOAD_FAILED);
        }
    }

    @Override
    public URI generatePublicUrl(File file) {
        return generateUrl(file, storageProperties.publicUrlExpireMin());
    }

    @Override
    public URI generatePrivateUrl(File file) {
        return generateUrl(file, storageProperties.privateUrlExpireMin());
    }

    private URI generateUrl(File file, Integer hour) {
        try {
            URI uri = URI.create(minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(file.getLocation().bucket())
                            .object(file.getUrl())
                            .expiry(hour, TimeUnit.MINUTES)
                            .build()
            ));
            log.info("file url created : {}", uri);
            return uri;

        } catch (Exception e) {
            log.error("miniO file url create failed!", e);
            throw new FileAdapterException(AdapterErrorCode.GENERATE_URL_FAILED);
        }
    }
}
