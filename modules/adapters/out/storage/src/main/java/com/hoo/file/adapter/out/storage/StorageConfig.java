package com.hoo.file.adapter.out.storage;

import com.hoo.file.application.StorageProperties;
import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfig {

    @Bean
    public MinioStorageAdapter minioStorageAdapter(
            MinioClient minioClient,
            StorageProperties storageProperties) {

        return new MinioStorageAdapter(minioClient, storageProperties);
    }

    @Bean
    public MinioClient minioClient(StorageProperties storageProperties) {

        return MinioClient.builder()
                .endpoint(storageProperties.endpoint())
                .credentials(storageProperties.accessKey(), storageProperties.secretKey())
                .build();
    }

}
