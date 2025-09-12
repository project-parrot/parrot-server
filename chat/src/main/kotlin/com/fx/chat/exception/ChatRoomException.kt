package com.fx.chat.exception

import com.fx.global.api.ErrorCodeIfs

class ChatRoomException (
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)