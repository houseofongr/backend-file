package com.hoo.file.adapter.in.web.errorcode;

import com.hoo.file.application.exception.FileAdapterException;
import com.hoo.file.application.exception.FileApplicationException;
import com.hoo.file.application.exception.FileDomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(FileDomainException.class)
    public ResponseEntity<ErrorResponse> handle(FileDomainException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(ErrorResponse.of(exception.getError()));
    }

    @ExceptionHandler(FileApplicationException.class)
    public ResponseEntity<ErrorResponse> handle(FileApplicationException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(ErrorResponse.of(exception.getError()));
    }

    @ExceptionHandler(FileAdapterException.class)
    public ResponseEntity<ErrorResponse> handle(FileAdapterException exception) {
        return ResponseEntity.status(exception.getError().getStatus()).body(ErrorResponse.of(exception.getError()));
    }
}
