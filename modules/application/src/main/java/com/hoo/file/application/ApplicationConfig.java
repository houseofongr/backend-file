package com.hoo.file.application;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({StorageProperties.class, ServerProperties.class})
public class ApplicationConfig {
}
