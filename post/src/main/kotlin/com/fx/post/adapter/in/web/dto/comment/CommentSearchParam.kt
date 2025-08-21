package com.fx.post.adapter.`in`.web.dto.comment

import com.fx.post.application.`in`.dto.CommentQueryCommand
import org.springframework.data.domain.Pageable

data class CommentSearchParam(
    val commentId: Long?= null,
    val parentId: Long?= null,
) {

    fun toCommand(userId:Long?= null, postId:Long?= null, pageable: Pageable): CommentQueryCommand =
        CommentQueryCommand(
            userId = userId,
            postId = postId,
            commentId = this.commentId,
            parentId = this.parentId,
            pageable = pageable
        )
}
