package com.fx.post.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class PostErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {
    DuplicateDailyPost(HttpStatus.CONFLICT, message = "하루에 하나만 작성할 수 있습니다.")
}