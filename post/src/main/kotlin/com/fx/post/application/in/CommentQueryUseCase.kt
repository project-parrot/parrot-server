package com.fx.post.application.`in`

import com.fx.post.domain.Comment

interface CommentQueryUseCase {

    fun getComments(postId: Long): List<Comment>

    fun getMyComments(userId: Long): List<Comment>

}