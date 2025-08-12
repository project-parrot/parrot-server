package com.fx.user.exception

import com.fx.global.api.ErrorCodeIfs

class FollowException (
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)