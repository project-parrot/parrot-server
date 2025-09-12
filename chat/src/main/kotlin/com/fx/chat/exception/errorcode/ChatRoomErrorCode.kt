package com.fx.chat.exception.errorcode

import com.fx.global.api.ErrorCodeIfs
import org.springframework.http.HttpStatus

enum class ChatRoomErrorCode(
    override val httpStatus: HttpStatus,
    override val message: String
) : ErrorCodeIfs {

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 채팅방입니다."),
    TARGET_USER_IDS_REQUIRED(HttpStatus.BAD_REQUEST, "채팅방이 없으면 targetUserIds가 필요합니다."),
    PARTICIPANT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "채팅방을 만들려면 다른 사용자가 필요합니다."),
    INVALID_PARTICIPANT(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자가 포함되어 있습니다."),

}