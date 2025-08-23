package com.fx.post.application.service

import com.fx.post.application.`in`.LikeCommandUseCase
import com.fx.post.application.`in`.dto.LikeAddCommand
import com.fx.post.application.`in`.dto.LikeCancelCommand
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
    override fun addLike(likeAddCommand: LikeAddCommand) {
        if (!postPersistencePort.existsById(likeAddCommand.postId))
            throw PostException(PostErrorCode.POST_NOT_EXIST)

        if (likePersistencePort.existsByPostIdAndUserId(likeAddCommand.postId, likeAddCommand.userId)) {
            throw LikeException(LikeErrorCode.LIKE_EXIST)
        }

        likePersistencePort.save(Like.addLike(likeAddCommand))
    }

    @Transactional
    override fun cancelLike(likeCancelCommand: LikeCancelCommand) {
        if (!postPersistencePort.existsById(likeCancelCommand.postId))
            throw PostException(PostErrorCode.POST_NOT_EXIST)

        val likeCount = likePersistencePort.deleteByPostIdAndUserId(
            Like.cancelLike(likeCancelCommand)
        )

        if (likeCount == 0) throw LikeException(LikeErrorCode.LIKE_NOT_EXIST)
    }

}