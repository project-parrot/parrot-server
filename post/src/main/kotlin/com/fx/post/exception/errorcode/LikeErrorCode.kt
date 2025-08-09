package com.fx.post.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class LikeErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {
    LIKE_EXIST(HttpStatus.CONFLICT, message = "이미 좋아요한 게시글입니다."),
    LIKE_NOT_EXIST(HttpStatus.CONFLICT, message = "좋아요하지 않은 게시글입니다.")
}