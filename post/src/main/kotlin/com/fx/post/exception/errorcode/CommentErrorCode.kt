package com.fx.post.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class CommentErrorCode (
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {
    PARENT_NOT_EXIST(HttpStatus.CONFLICT, message = "부모 댓글이 존재하지 않습니다.")

}