package com.fx.user.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class EmailVerificationErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {

    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "인증 코드가 존재하지 않습니다."),
    CODE_MISMATCH(HttpStatus.BAD_REQUEST, "인증 코드가 일치하지 않습니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "유효하지 않은 인증 토큰입니다."),
    UNAUTHORIZED_VERIFICATION_ACCESS(HttpStatus.UNAUTHORIZED, "이메일 인증 권한이 없습니다.")

}