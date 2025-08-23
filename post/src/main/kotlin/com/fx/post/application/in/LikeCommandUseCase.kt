package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.LikeAddCommand
import com.fx.post.application.`in`.dto.LikeCancelCommand

interface LikeCommandUseCase {

    fun addLike(likeAddCommand: LikeAddCommand)

    fun cancelLike(likeCancelCommand: LikeCancelCommand)

}