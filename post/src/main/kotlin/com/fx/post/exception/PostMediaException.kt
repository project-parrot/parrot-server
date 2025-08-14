package com.fx.post.exception

import com.fx.global.api.ErrorCodeIfs

class PostMediaException(
    val errorCodeIfs: ErrorCodeIfs,
    cause: Throwable? = null
) : RuntimeException(errorCodeIfs.message, cause)