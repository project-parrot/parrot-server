package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.LikeAddCommand
import com.fx.post.application.`in`.dto.LikeCancelCommand
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface LikeCommandUseCase {

    fun addLike(@Valid likeAddCommand: LikeAddCommand)

    fun cancelLike(@Valid likeCancelCommand: LikeCancelCommand)

}