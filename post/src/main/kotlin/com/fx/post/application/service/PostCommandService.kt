package com.fx.post.application.service

import com.fx.global.dto.UserRole
import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.dto.PostCreateCommand
import com.fx.post.application.`in`.dto.PostUpdateCommand
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.application.out.persistence.PostMediaPersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.UserWebPort
import com.fx.post.domain.Like
import com.fx.post.domain.Post
import com.fx.post.domain.PostMedia
import com.fx.post.exception.LikeException
import com.fx.post.exception.PostException
import com.fx.post.exception.PostMediaException
import com.fx.post.exception.errorcode.LikeErrorCode
import com.fx.post.exception.errorcode.PostErrorCode
import com.fx.post.exception.errorcode.PostMediaErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class PostCommandService(
    private val postPersistencePort: PostPersistencePort,
    private val postMediaPersistencePort: PostMediaPersistencePort,
) : PostCommandUseCase {

    @Transactional
    override fun createPost(postCreateCommand: PostCreateCommand): Post {
        val userId = postCreateCommand.userId
        val today = LocalDateTime.now().toLocalDate()
        val start = today.atStartOfDay()
        val end = today.atTime(LocalTime.MAX)

        if (postPersistencePort.existsByUserIdAndCreatedAtBetween(userId, start, end)) {
            throw PostException(PostErrorCode.DUPLICATE_DAILY_POST)
        }

        val mediaIds = postCreateCommand.mediaIds.orEmpty()

        if (mediaIds.size > 5) throw PostMediaException(PostMediaErrorCode.TOO_MANY_FILE)

        val savedPost = postPersistencePort.save(Post.createPost(postCreateCommand))

        savePostMedia(savedPost.id!!, mediaIds)

        return savedPost
    }

    @Transactional
    override fun updatePost(postUpdateCommand: PostUpdateCommand): Post {
        val post = postPersistencePort.findByIdAndIsDeletedNot(postUpdateCommand.postId)?: throw PostException(PostErrorCode.POST_NOT_EXIST)

        val now = LocalDateTime.now()

        validateUserPermission(post.userId, postUpdateCommand.userId, postUpdateCommand.role)
        validateEditDate(now, post.createdAt)

        val mediaIds = postUpdateCommand.mediaIds.orEmpty().toSet()

        if (mediaIds.size > 5) throw PostMediaException(PostMediaErrorCode.TOO_MANY_FILE)

        updatePostMedia(postUpdateCommand.postId, mediaIds)

        return postPersistencePort.save(Post.updatePost(post.userId, now, postUpdateCommand)) // 운영자가 수정했을 때에도 게시글의 작성자는 변경되면 안되므로
    }

    private fun validateUserPermission(postUserId: Long, userId: Long, role: UserRole) {
        if (postUserId != userId && role != UserRole.ADMIN)
            throw PostException(PostErrorCode.POST_FORBIDDEN)
    }

    private fun validateEditDate(now: LocalDateTime, createdAt: LocalDateTime?) {
        if (now.toLocalDate() != createdAt?.toLocalDate()) {
            throw PostException(PostErrorCode.POST_EDIT_DATE_MISMATCH)
        }
    }

    private fun savePostMedia(postId: Long, mediaIds: List<Long>) {
        mediaIds.forEach { mediaId ->
            postMediaPersistencePort.save(PostMedia(postId = postId, mediaId = mediaId))
        }
    }

    private fun updatePostMedia(postId: Long, newMediaIds: Set<Long>) {
        val existingMediaIds = postMediaPersistencePort.findByPostId(postId).map {it}.toSet()

        val toAdd = newMediaIds - existingMediaIds
        val toRemove = existingMediaIds - newMediaIds

        toAdd.forEach { mediaId -> postMediaPersistencePort.save(PostMedia(postId = postId, mediaId = mediaId)) }
        toRemove.forEach { mediaId -> postMediaPersistencePort.delete(postId, mediaId) }
    }

}