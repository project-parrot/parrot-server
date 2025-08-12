package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.CommentCreateCommand
import com.fx.post.domain.Comment

interface CommentCommandUseCase {

    fun createComment(postId:Long, commentCreateCommand: CommentCreateCommand): Comment

}