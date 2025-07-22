package com.hoo.file.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/**
 * <pre>
 * <h1>Error Code Index</h1>
 * <ul>
 *     <li>Bad Request(400) : 1 ~ 99</li>
 *     <li>Authentication(401) : 100 ~ 199</li>
 *     <li>Not Found(404) : 300 ~ 399</li>
 *     <li>Conflict(405) : 400 ~ 499</li>
 *     <li>Internal Server Error(500) : 500 ~ 599</li>
 *     <li>Etc : 600 ~</li>
 * </ul>
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum AdapterErrorCode implements ErrorCode {

    GET_FILE_INPUT_STREAM_FAILED("FILE-WEB-1", BAD_REQUEST, "업로드 파일을 읽어오는데 실패했습니다."),
    BAD_METADATA_FORMAT("FILE-WEB-2", BAD_REQUEST, "메타데이터를 파싱할 수 없습니다."),

    FILE_NOT_FOUND("FILE-PERSISTENCE-300", NOT_FOUND, "해당 유저를 찾을 수 없습니다."),
    
    UPLOAD_FAILED("FILE-STORAGE-500", INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
