package com.fx.post.application.`in`

import com.fx.post.application.`in`.dto.CommentQueryCommand
import com.fx.post.application.out.persistence.dto.CommentInfo

interface CommentQueryUseCase {

    fun getComments(commentQueryCommand: CommentQueryCommand): List<CommentInfo>

    fun getMyComments(commentQueryCommand: CommentQueryCommand): List<CommentInfo>

}