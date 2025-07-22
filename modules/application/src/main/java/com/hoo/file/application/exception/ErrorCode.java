package com.hoo.file.application.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String name();

    String getCode();

    HttpStatus getStatus();

    String getMessage();
}
