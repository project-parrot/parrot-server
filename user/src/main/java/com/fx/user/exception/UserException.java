package com.fx.user.exception;

import com.fx.global.api.ErrorCodeIfs;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final ErrorCodeIfs errorCodeIfs;

    public UserException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getMessage());
        this.errorCodeIfs = errorCodeIfs;
    }

    public UserException(ErrorCodeIfs errorCodeIfs, Throwable cause) {
        super(errorCodeIfs.getMessage(), cause);
        this.errorCodeIfs = errorCodeIfs;
    }

}
