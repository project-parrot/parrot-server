package com.fx.user.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class FollowErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {

    ALREADY_FOLLOWING(HttpStatus.CONFLICT, "이미 팔로우 중입니다."),
    FOLLOW_REQUEST_ALREADY_SENT(HttpStatus.CONFLICT, "이미 팔로우 요청을 보냈습니다."),
    SELF_FOLLOW_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "자기 자신을 팔로우할 수 없습니다."),
    NOT_FOLLOWING(HttpStatus.BAD_REQUEST, "팔로우 중이 아닙니다."),
    NOT_FOLLOWER(HttpStatus.BAD_REQUEST, "해당 사용자는 나를 팔로우하지 않습니다."),
    INVALID_MODE(HttpStatus.BAD_REQUEST, "지원하지 않는 mode 값입니다.")

}