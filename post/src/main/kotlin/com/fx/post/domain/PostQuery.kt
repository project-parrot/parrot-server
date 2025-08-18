package com.fx.post.domain

import com.fx.post.application.`in`.dto.PostQueryCommand
import org.springframework.data.domain.Pageable

data class PostQuery(
    val userId: Long? =null,
    val userIds: List<Long>? =null,
    val postId: Long?= null,

    val pageable: Pageable,
    val isDeleted: Boolean? = false,
) {
    companion object {
        @JvmStatic
        fun searchCondition(postQueryCommand: PostQueryCommand, isDeleted: Boolean): PostQuery =
            PostQuery(
                userId = postQueryCommand.userId,
                userIds = postQueryCommand.userIds,
                postId = postQueryCommand.postId,
                pageable = postQueryCommand.pageable,
                isDeleted = isDeleted
            )
    }
}