package com.fx.post.application.service

import com.fx.global.dto.UserRole
import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.dto.CommentCreateCommand
import com.fx.post.application.`in`.dto.PostCreateCommand
import com.fx.post.application.`in`.dto.PostUpdateCommand
import com.fx.post.application.out.persistence.CommentPersistencePort
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.application.out.persistence.PostMediaPersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.UserWebPort
import com.fx.post.domain.Comment
import com.fx.post.domain.Like
import com.fx.post.domain.Post
import com.fx.post.exception.CommentException
import com.fx.post.exception.LikeException
import com.fx.post.exception.PostException
import com.fx.post.exception.errorcode.CommentErrorCode
import com.fx.post.exception.errorcode.LikeErrorCode
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
    private val likePersistencePort: LikePersistencePort,
    private val userWebPort: UserWebPort
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

        val savedPost = postPersistencePort.save(Post.createPost(postCreateCommand))
        return savedPost
    }

    @Transactional
    override fun updatePost(postId:Long, postUpdateCommand: PostUpdateCommand): Post {
        val post = postPersistencePort.findByIdAndIsDeletedNot(postId)?: throw PostException(PostErrorCode.POST_NOT_EXIST)

        if (postUpdateCommand.userId != post.userId && postUpdateCommand.role != UserRole.ADMIN) {
            throw PostException(PostErrorCode.POST_FORBIDDEN)
        }

        val today: LocalDateTime = LocalDateTime.now()
        val createdAt: LocalDateTime? = post.createdAt

        if (today.toLocalDate() != createdAt?.toLocalDate()) throw PostException(PostErrorCode.POST_EDIT_DATE_MISMATCH)

        val savedPost = postPersistencePort.save(Post.updatePost(postId, post.userId, today, postUpdateCommand))
        return savedPost
    }

    @Transactional
    override fun createComment(postId:Long, commentCreateCommand: CommentCreateCommand): Comment {
        if (!postPersistencePort.existsById(postId))
            throw PostException(PostErrorCode.POST_NOT_EXIST)

        val parentId = commentCreateCommand.parentId

        return if (parentId != null) {
            if (!commentPersistencePort.existsById(parentId)) {
                throw CommentException(CommentErrorCode.PARENT_NOT_EXIST)
            }
            commentPersistencePort.save(Comment.createComment(postId, commentCreateCommand))
        } else {
            commentPersistencePort.save(Comment.createComment(postId, commentCreateCommand))
        }
    }

    override fun getComments(postId: Long): List<Comment> {
        val comments = commentPersistencePort.findByPostIdOrderByCreatedAtAsc(postId)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)

        return comments
    }

    override fun getMyComments(userId: Long): List<Comment> {
        val comments = commentPersistencePort.findByUserIdOrderByCreatedAtDesc(userId)

        return comments
    }

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

    override fun getLikeUsers(postId: Long): List<Long> {
        val users = likePersistencePort.findByPostId(postId)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)
        // 이후 userId나 닉네임 순으로 정렬(미구현)

        return users
    }

    override fun getMyLikedPosts(userId: Long, before: LocalDateTime): List<PostSummaryDto> {
        val posts = postPersistencePort.findLikedPostsByUserIdAndCreatedAtBeforeAndIsDeleted(userId, before)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)

        return posts
    }
}