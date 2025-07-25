package com.hoo.file.adapter.in.web;

import com.hoo.file.api.in.GetFileUrlUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetFileUrlController {

    private final GetFileUrlUseCase getFileUrlUseCase;

    @GetMapping("/files/{token}")
    public ResponseEntity<Void> download(@PathVariable String token) {
        return ResponseEntity.status(302)
                .header("Location", getFileUrlUseCase.getTempUrl(token))
                .build();
    }

}
