package com.hoo.file.adapter.in.web;

import com.hoo.common.internal.api.file.GetFileInfoAPI;
import com.hoo.common.internal.api.file.dto.FileInfo;
import com.hoo.common.internal.api.file.dto.GetFileInfoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetFileInfoController {

    private final GetFileInfoAPI getFileInfoAPI;

    @PostMapping("/files/info")
    ResponseEntity<List<FileInfo>> getPublicFileInfo(@RequestBody GetFileInfoCommand command) {
        return ResponseEntity.ok(getFileInfoAPI.getFileInfo(command));
    }
}
