package com.fx.user.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class ProfileErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs
{

    PROFILE_NOT_FOUND(HttpStatus.NOT_FOUND, "프로필 정보를 찾을 수 없습니다."),
    PROFILE_PRIVATE(HttpStatus.FORBIDDEN, "비공개 프로필입니다.")

}