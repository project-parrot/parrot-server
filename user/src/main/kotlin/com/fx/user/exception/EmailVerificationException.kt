package com.fx.user.exception

import com.fx.global.api.ErrorCodeIfs

class EmailVerificationException(
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)