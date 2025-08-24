package com.fx.post.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class PostErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {
    DUPLICATE_DAILY_POST(HttpStatus.CONFLICT, message = "하루에 하나만 작성할 수 있습니다."),
    POST_NOT_EXIST(HttpStatus.CONFLICT, message = "게시글이 존재하지 않습니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, message = "게시글을 수정할 권한이 없습니다."),
    POST_EDIT_DATE_MISMATCH(HttpStatus.CONFLICT, message = "작성일 기준 당일에만 수정할 수 있습니다."),
    TOO_MANY_FILE(HttpStatus.BAD_REQUEST, "최대 5개의 파일만 업로드할 수 있습니다."),
}