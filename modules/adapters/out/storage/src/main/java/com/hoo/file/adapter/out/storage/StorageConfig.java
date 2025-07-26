package com.hoo.file.adapter.out.storage;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfig {

    @Bean
    public MinioAdapter minioStorageAdapter(
            MinioClient minioClient,
            StorageProperties storageProperties) {

        return new MinioAdapter(minioClient, storageProperties);
    }

    @Bean
    public MinioClient minioClient(StorageProperties storageProperties) {

        return MinioClient.builder()
                .endpoint(storageProperties.endpoint())
                .credentials(storageProperties.accessKey(), storageProperties.secretKey())
                .build();
    }

}
