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
 *     <li>Authorization(403) : 200 ~ 299</li>
 *     <li>Not Found(404) : 300 ~ 399</li>
 *     <li>Conflict(405) : 400 ~ 499</li>
 *     <li>Internal Server Error(500) : 500 ~ 599</li>
 *     <li>Etc : 600 ~</li>
 * </ul>
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum ApplicationErrorCode implements ErrorCode {

    NOT_SUPPORTED_MEDIA_TYPE("FILE-APPLICATION-1", BAD_REQUEST, "지원하지 않는 파일 형식입니다."),

    OWNERSHIP_REQUIRED("FILE-APPLICATION-100", UNAUTHORIZED, "소유자 확인이 필요한 파일입니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
