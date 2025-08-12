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
    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "팔로우/팔로워 요청을 찾을 수 없습니다."),
    UNAUTHORIZED_UNFOLLOW(HttpStatus.UNAUTHORIZED, "팔로우/팔로워 삭제 권한이 없습니다.")

}