package com.hoo.file.adapter.in.web;

import com.hoo.file.api.in.GetFileUrlUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DownloadFileControllerTest extends DocumentationTest {

    @Autowired
    private GetFileUrlUseCase getFileUrlUseCase;

    @Test
    @DisplayName("파일 다운로드 - 프록시")
    void downloadFile() throws Exception {
        String token = "abc1234";
        String tempUrl = "https://mybucket.path.com/abc.jpg";
        when(getFileUrlUseCase.getTempUrl(token)).thenReturn(tempUrl);

        mockMvc.perform(get("/files/{token}", token))
                .andExpect(status().isFound())
                .andDo(document("download-file",
                        pathParameters(
                                parameterWithName("token").description("해당 파일의 토큰입니다.")
                        ),
                        responseHeaders(
                                headerWithName("Location").description("임시 파일 다운로드 주소(302 리다이렉트)")
                        )
                ));
    }

}