package com.fx.global.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class UnauthorizedErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {

    MISSING_USER_ID_HEADER(HttpStatus.UNAUTHORIZED, "Missing header: X-User-Id"),
    MISSING_USER_ROLE_HEADER(HttpStatus.UNAUTHORIZED, "Missing header: X-User-Role"),
    INVALID_USER_ID_FORMAT(HttpStatus.UNAUTHORIZED, "Invalid userId format"),
    INVALID_USER_ROLE(HttpStatus.UNAUTHORIZED, "Invalid userRole value"),
    NO_REQUEST_CONTEXT(HttpStatus.UNAUTHORIZED, "No request context available");

}