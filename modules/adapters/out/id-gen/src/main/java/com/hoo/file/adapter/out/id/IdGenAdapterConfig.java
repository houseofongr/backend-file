package com.hoo.file.adapter.out.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGenAdapterConfig {

    @Bean
    public IssueUUIDAdapter issueUUIDAdapter() {
        return new IssueUUIDAdapter();
    }
}
