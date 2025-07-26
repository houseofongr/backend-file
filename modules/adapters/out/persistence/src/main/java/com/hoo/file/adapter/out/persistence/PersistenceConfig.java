package com.hoo.file.adapter.out.persistence;

import com.hoo.file.adapter.out.persistence.repository.FileJpaRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories
@EntityScan
public class PersistenceConfig {

    @Bean
    public JpaQueryAdapter loadFileAdapter(FileJpaRepository fileJpaRepository, PersistenceMapper persistenceMapper) {
        return new JpaQueryAdapter(fileJpaRepository, persistenceMapper);
    }

    @Bean
    public JpaCommandAdapter handleFileEventAdapter(FileJpaRepository fileJpaRepository) {
        return new JpaCommandAdapter(fileJpaRepository);
    }

    @Bean
    public PersistenceMapper persistenceMapper() {
        return new PersistenceMapper();
    }
}
