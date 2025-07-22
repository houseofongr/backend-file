package com.hoo.file.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
public enum DomainErrorCode implements ErrorCode {

    OVERLAPPED("UNIVERSE-DOMAIN-1", BAD_REQUEST, "내부의 다른 요소와 충돌했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
