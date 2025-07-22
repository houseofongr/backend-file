package com.hoo.file.adapter.in.web;

import com.hoo.common.internal.api.file.GetFileInfoAPI;
import com.hoo.common.internal.api.file.UploadFileAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan(basePackages = "com.hoo.file.adapter.in.web")
public class WebMvcTestMockContext {

    @Bean
    public GetFileInfoAPI getFileInfoAPI() {
        return mock(GetFileInfoAPI.class);
    }

    @Bean
    public UploadFileAPI uploadFileAPI() {
        return mock(UploadFileAPI.class);
    }

}
