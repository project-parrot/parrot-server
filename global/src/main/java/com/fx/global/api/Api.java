package com.fx.global.api;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@RequiredArgsConstructor
public class Api<T> {

    private final Boolean success;
    private final Integer code;
    private final String message;
    private final T data;

    public static <T> ResponseEntity<Api<T>> OK(T data, String message) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Api.<T>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<Api<T>> OK(T data) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Api.<T>builder()
                .success(true)
                .code(HttpStatus.OK.value())
                .message(null)
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<Api<T>> OK(T data, HttpStatus httpStatus) {
        return ResponseEntity
            .status(httpStatus)
            .body(Api.<T>builder()
                .success(true)
                .code(httpStatus.value())
                .message(null)
                .data(data)
                .build());
    }

    public static <T> ResponseEntity<Api<T>> OK(T data, String message, HttpStatus httpStatus) {
        return ResponseEntity
            .status(httpStatus)
            .body(Api.<T>builder()
                .success(true)
                .code(httpStatus.value())
                .message(message)
                .data(data)
                .build());
    }

    public static <T extends ErrorCodeIfs> ResponseEntity<Api<T>> ERROR(T errorCodeIfs) {
        return ResponseEntity
            .status(errorCodeIfs.getHttpStatus())
            .body(Api.<T>builder()
                .success(false)
                .code(errorCodeIfs.getHttpStatus().value())
                .message(errorCodeIfs.getMessage())
                .data(null)
                .build());
    }

}