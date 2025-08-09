package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.CommentCreateCommand
import com.fx.post.application.`in`.dto.PostCreateCommand
import com.fx.post.domain.Comment
import com.fx.post.domain.Post

interface PostCommandUseCase {

    fun createPost(postCreateCommand: PostCreateCommand): Post

    fun createComment(postId:Long, commentCreateCommand: CommentCreateCommand): Comment

    fun addLike(postId:Long, userId: Long)
}