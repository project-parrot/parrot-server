package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.PostCreateCommand
import com.fx.post.application.`in`.dto.PostUpdateCommand
import com.fx.post.domain.Post

interface PostCommandUseCase {

    fun createPost(postCreateCommand: PostCreateCommand): Post

    fun updatePost(postUpdateCommand: PostUpdateCommand): Post

}