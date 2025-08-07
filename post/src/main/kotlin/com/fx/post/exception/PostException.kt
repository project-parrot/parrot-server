package com.fx.post.exception

import com.fx.global.api.ErrorCodeIfs

class PostException(
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)