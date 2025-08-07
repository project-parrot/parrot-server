package com.fx.post.application.service

import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.dto.PostCreateCommand
import com.fx.post.application.out.CommentPersistencePort
import com.fx.post.application.out.LikePersistencePort
import com.fx.post.application.out.PostMediaPersistencePort
import com.fx.post.application.out.PostPersistencePort
import com.fx.post.domain.Post
import com.fx.post.exception.PostException
import com.fx.post.exception.errorcode.PostErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class PostCommandService(
    private val postPersistencePort: PostPersistencePort,
    private val postMediaPersistencePort: PostMediaPersistencePort,
    private val commentPersistencePort: CommentPersistencePort,
    private val likePersistencePort: LikePersistencePort
) : PostCommandUseCase {

    @Transactional
    override fun createPost(postCreateCommand: PostCreateCommand): Post {
        val userId = postCreateCommand.userId
        val today = LocalDateTime.now().toLocalDate()
        val start = today.atStartOfDay()
        val end = today.atTime(LocalTime.MAX)

        if (postPersistencePort.existsByUserIdAndCreatedAtBetween(userId, start, end)) {
            throw PostException(PostErrorCode.DuplicateDailyPost)
        }

        val savedPost = postPersistencePort.save(Post.createPost(postCreateCommand))
        return savedPost
    }

}