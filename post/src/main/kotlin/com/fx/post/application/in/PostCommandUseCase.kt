package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.PostCreateCommand
import com.fx.post.application.`in`.dto.PostUpdateCommand
import com.fx.post.domain.Post
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface PostCommandUseCase {

    fun createPost(@Valid postCreateCommand: PostCreateCommand): Post

    fun updatePost(@Valid postUpdateCommand: PostUpdateCommand): Post

}