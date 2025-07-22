package com.hoo.file.application.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class FileAdapterException extends RuntimeException {
    private final AdapterErrorCode error;
    private final String message;

    public FileAdapterException(AdapterErrorCode error) {
        log.error("Application Error : {}", error.getMessage());
        this.error = error;
        this.message = error.getMessage();
    }

    public FileAdapterException(AdapterErrorCode error, String message) {
        log.error("Application Error : {}", message);
        this.error = error;
        this.message = message;
    }

}
