package com.fx.global.api;

import org.springframework.http.HttpStatus;

public interface ErrorCodeIfs {

    HttpStatus getHttpStatus();

    String getMessage();

}
