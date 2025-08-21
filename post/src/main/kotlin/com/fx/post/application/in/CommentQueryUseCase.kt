package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.CommentQueryCommand
import com.fx.post.domain.CommentInfo

interface CommentQueryUseCase {

    fun getComments(commentQueryCommand: CommentQueryCommand): List<CommentInfo>

}