package com.hoo.file.adapter.in.web;

import com.github.f4b6a3.uuid.UuidCreator;
import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UploadFileControllerTest extends DocumentationTest {

    @Autowired
    private UploadFileAPI uploadFileAPI;

    @Test
    @DisplayName("파일 단건 업로드")
    void upload() throws Exception {

        //language=JSON
        String metadata = """
                {
                  "domain": "universe",
                  "ownerID": "019812d4-73e3-7d80-9e8e-aa62af94c0b3",
                  "accessLevel": "PUBLIC"
                }
                """;

        MockPart metadataPart = new MockPart("metadata", metadata.getBytes());
        metadataPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = "<<png data 1>>".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", bytes);

        UUID newFileID = UuidCreator.getTimeOrderedEpoch();
        when(uploadFileAPI.uploadFile(argThat(command ->
                command.fileSource().contentType().equals("image/png") &&
                command.fileSource().name().equals("image.png") &&
                command.fileSource().size().equals((long) bytes.length) &&
                command.metadata().domain().equals("universe") &&
                command.metadata().ownerID().toString().equals("019812d4-73e3-7d80-9e8e-aa62af94c0b3") &&
                command.metadata().accessLevel().name().equals("PUBLIC")
        )))
                .thenReturn(new UploadFileResult(
                        newFileID,
                        URI.create("https://api.houseofongr.com/files/fbcd947ebb8c3a89"),
                        ZonedDateTime.now().toEpochSecond()
                ));

        mockMvc.perform(multipart("/files/upload")
                        .file(file)
                        .part(metadataPart))
                .andExpect(status().is(201))
                .andDo(document("upload-file",
                        requestParts(
                                partWithName("metadata").description("업로드할 파일의 정보를 포함하는 Json 문자열입니다."),
                                partWithName("file").description("업로드할 파일입니다.")
                        ),
                        responseFields(
                                fieldWithPath("id").description("저장된 파일의 ID입니다."),
                                fieldWithPath("fileUrl").description("저장된 파일의 url 경로입니다."),
                                fieldWithPath("uploadedTimestamp").description("파일이 생성된 시간입니다.")
                        )));
    }

}