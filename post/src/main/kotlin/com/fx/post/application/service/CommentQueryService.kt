package com.fx.post.application.service

import com.fx.post.application.`in`.CommentQueryUseCase
import com.fx.post.application.out.persistence.CommentPersistencePort
import com.fx.post.domain.Comment
import com.fx.post.domain.CommentInfo
import org.springframework.stereotype.Service

@Service
class CommentQueryService(
    private val commentPersistencePort: CommentPersistencePort
) : CommentQueryUseCase{

    override fun getComments(postId: Long): List<CommentInfo> {
        val comments = commentPersistencePort.findByPostIdOrderByCreatedAtAsc(postId)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)

        val commentInfos = comments.map { CommentInfo.from(null, null, it) }

        return commentInfos
    }

    override fun getMyComments(userId: Long): List<Comment> {
        val comments = commentPersistencePort.findByUserIdOrderByCreatedAtDesc(userId)

        return comments
    }

}