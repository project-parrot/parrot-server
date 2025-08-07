package com.fx.post.exception

import com.fx.global.api.ErrorCodeIfs

class LikeException(
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)