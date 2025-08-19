package com.fx.post.application.`in`

import com.fx.post.domain.Comment
import com.fx.post.domain.CommentInfo

interface CommentQueryUseCase {

    fun getComments(postId: Long): List<CommentInfo>

    fun getMyComments(userId: Long): List<Comment>

}