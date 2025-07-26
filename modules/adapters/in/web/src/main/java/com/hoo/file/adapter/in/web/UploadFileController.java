package com.hoo.file.adapter.in.web;

import com.hoo.common.internal.api.file.UploadFileAPI;
import com.hoo.common.internal.api.file.dto.UploadFileCommand;
import com.hoo.common.internal.api.file.dto.UploadFileResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadFileController {

    private final UploadFileAPI uploadFileAPI;
    private final WebMapper webMapper;

    @PostMapping("/files/upload")
    ResponseEntity<UploadFileResult> upload(
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(name = "metadata") String metadata) {
        UploadFileCommand command = webMapper.mapToUploadFileCommand(file, metadata);
        return new ResponseEntity<>(uploadFileAPI.uploadFile(command), HttpStatus.CREATED);
    }
}
