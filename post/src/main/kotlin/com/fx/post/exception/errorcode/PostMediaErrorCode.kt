package com.fx.post.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class PostMediaErrorCode(override val httpStatus: HttpStatus,
                              override val message: String
) : ErrorCodeIfs {
    TOO_MANY_FILE(HttpStatus.BAD_REQUEST, "최대 5개의 파일만 업로드할 수 있습니다."),
}