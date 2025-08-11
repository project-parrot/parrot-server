package com.fx.global.exception

import com.fx.global.api.ErrorCodeIfs

class UnauthorizedException(
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)