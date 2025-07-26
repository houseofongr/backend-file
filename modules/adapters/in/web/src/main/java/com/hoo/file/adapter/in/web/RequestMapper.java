package com.hoo.file.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.file.application.exception.AdapterErrorCode;
import com.hoo.file.application.exception.FileAdapterException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
public class RequestMapper {

    private final ObjectMapper objectMapper;

    public UploadFileCommand mapToUploadFileCommand(MultipartFile file, String metadata) {
        if (file == null) return null;

        try {
            UploadFileCommand.FileSource fileSource = new UploadFileCommand.FileSource(
                    file.getInputStream(),
                    file.getContentType(),
                    file.getOriginalFilename(),
                    file.getSize()
            );

            UploadFileCommand.Metadata fileMetadata = mapToUploadFileMetadata(metadata);

            return new UploadFileCommand(fileSource, fileMetadata);
        } catch (IOException e) {
            throw new FileAdapterException(AdapterErrorCode.GET_FILE_INPUT_STREAM_FAILED);
        }
    }

    public UploadFileCommand.Metadata mapToUploadFileMetadata(String metadata) {
        try {
            return objectMapper.readValue(metadata, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileAdapterException(AdapterErrorCode.BAD_METADATA_FORMAT);
        }
    }
}
