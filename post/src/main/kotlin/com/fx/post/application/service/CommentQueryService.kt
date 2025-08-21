package com.fx.post.application.service

import com.fx.post.application.`in`.CommentQueryUseCase
import com.fx.post.application.`in`.dto.CommentQueryCommand
import com.fx.post.application.out.persistence.CommentPersistencePort
import com.fx.post.domain.CommentInfo
import com.fx.post.domain.CommentQuery
import org.springframework.stereotype.Service

@Service
class CommentQueryService(
    private val commentPersistencePort: CommentPersistencePort,
) : CommentQueryUseCase{

    override fun getComments(commentQueryCommand: CommentQueryCommand): List<CommentInfo> {
        val commentQuery = CommentQuery.searchCondition(commentQueryCommand, false)
        return commentPersistencePort.getComments(commentQuery)
    }

}