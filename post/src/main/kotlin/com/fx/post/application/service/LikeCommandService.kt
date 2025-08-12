package com.fx.post.application.service

import com.fx.post.application.`in`.LikeCommandUseCase
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.domain.Like
import com.fx.post.exception.LikeException
import com.fx.post.exception.PostException
import com.fx.post.exception.errorcode.LikeErrorCode
import com.fx.post.exception.errorcode.PostErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LikeCommandService(
    private val likePersistencePort: LikePersistencePort,
    private val postPersistencePort: PostPersistencePort
) : LikeCommandUseCase {

    @Transactional
    override fun addLike(postId: Long, userId: Long) {
        if (!postPersistencePort.existsById(postId))
            throw PostException(PostErrorCode.POST_NOT_EXIST)

        if (likePersistencePort.existsByPostIdAndUserId(postId, userId)) {
            throw LikeException(LikeErrorCode.LIKE_EXIST)
        }

        likePersistencePort.save(Like.addLike(postId, userId))
    }

    @Transactional
    override fun cancelLike(postId: Long, userId: Long) {
        if (!postPersistencePort.existsById(postId))
            throw PostException(PostErrorCode.POST_NOT_EXIST)

        val likeCount = likePersistencePort.deleteByPostIdAndUserId(postId, userId)

        if (likeCount == 0) throw LikeException(LikeErrorCode.LIKE_NOT_EXIST)
    }

}