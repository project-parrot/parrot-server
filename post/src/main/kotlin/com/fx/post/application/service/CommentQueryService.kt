package com.fx.post.application.service

import com.fx.post.adapter.out.web.impl.dto.ProfileCommand
import com.fx.post.application.`in`.CommentQueryUseCase
import com.fx.post.application.`in`.dto.CommentQueryCommand
import com.fx.post.application.out.persistence.CommentPersistencePort
import com.fx.post.application.out.web.UserWebPort
import com.fx.post.application.out.persistence.dto.CommentInfo
import com.fx.post.domain.CommentQuery
import org.springframework.stereotype.Service

@Service
class CommentQueryService(
    private val commentPersistencePort: CommentPersistencePort,
    private val userWebPort: UserWebPort
) : CommentQueryUseCase{

    override fun getComments(commentQueryCommand: CommentQueryCommand): List<CommentInfo> =
        mappedByProfile(
            commentPersistencePort.getComments(
                CommentQuery.searchCondition(commentQueryCommand, false)
            )
        )

    override fun getMyComments(commentQueryCommand: CommentQueryCommand): List<CommentInfo> =
        commentPersistencePort.getComments(
            CommentQuery.searchCondition(commentQueryCommand, false)
        )

    private fun mappedByProfile(comments: List<CommentInfo>): List<CommentInfo> {
        if (comments.isEmpty()) return emptyList()

        val userMap: Map<Long, ProfileCommand> = userWebPort.getUsersInfo(comments.map { it.userId }.distinct())
            .orEmpty()
            .associateBy { it.userId }

        return comments.map { comment ->
            val userInfo = userMap[comment.userId]
            comment.copy(
                nickname = userInfo?.nickname,
                profileImageUrl = userInfo?.profileImageUrl
            )
        }
    }

}