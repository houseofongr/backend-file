package com.hoo.file.adapter.out.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGenConfig {

    @Bean
    public UUIDAdapter issueUUIDAdapter() {
        return new UUIDAdapter();
    }
}
