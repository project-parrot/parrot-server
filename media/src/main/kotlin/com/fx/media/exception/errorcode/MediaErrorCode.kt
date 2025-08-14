package com.fx.media.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class MediaErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs
{

    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "업로드할 파일이 필요합니다."),
    TOO_MANY_FILE(HttpStatus.BAD_REQUEST, "최대 5개의 파일만 업로드할 수 있습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패했습니다."),
    DIRECTORY_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "디렉터리 생성에 실패하였습니다."),
    MEDIA_NOT_FOUND(HttpStatus.NOT_FOUND, "삭제되었거나 존재하지 않는 미디어입니다.")

}