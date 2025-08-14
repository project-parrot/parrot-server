package com.fx.media.exception

import com.fx.global.api.ErrorCodeIfs

class MediaException(
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)