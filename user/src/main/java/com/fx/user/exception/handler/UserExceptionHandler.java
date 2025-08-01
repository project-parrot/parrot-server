package com.fx.user.exception.handler;

import com.fx.global.api.Api;
import com.fx.global.api.ErrorCodeIfs;
import com.fx.user.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Api<ErrorCodeIfs>> handleUserException(UserException e) {
        log.error("",e);
        return Api.ERROR(e.getErrorCodeIfs());
    }


}
