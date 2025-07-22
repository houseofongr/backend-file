package com.hoo.file.application.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class FileDomainException extends RuntimeException {

    private final DomainErrorCode error;
    private final String message;

    public FileDomainException(DomainErrorCode error) {
        log.error("Application Error : {}", error.getMessage());
        this.error = error;
        this.message = error.getMessage();
    }

    public FileDomainException(DomainErrorCode error, String message) {
        log.error("Application Error : {}", message);
        this.error = error;
        this.message = message;
    }

}
